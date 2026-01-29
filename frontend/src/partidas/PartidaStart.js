import React, { useState, useEffect } from 'react';
import VisualizacionCarta from './VisualizacionCarta';
import VisualizacionChat from './VisualizacionChat'
import tokenService from '../services/token.service';
import PartidaBatallaCartas from './PartidaBatallaCartas';
import { update } from '../peticiones';

export default function PartidaStart(props) {
 const {partida,sendPartidaToSocket} = props;
 const [renderCartasJugador,setRenderCartasJugador]=useState(true);
  const [mazo,setMazo] = useState(partida.mazo)
  const [jugadorCartas, setJugadorCartas] = useState([]);
  const [oponenteCartas, setOponenteCartas] = useState([]);
  const [cartaSeleccionada,setCartaSeleccionada]=useState(null);
  const [cartaUsadaJugador,setCartaUsadaJugador]=useState(null);
  const [cartaUsadaOponente,setCartaUsadaOponente]=useState(null);
  const [cartaMiradaFija,setCartaMiradaFija]=useState(null);
  const [mensaje,setMensaje]=useState('');
  const [jugadorActual,setJugadorActual] = useState([]);
  const [oponenteActual,setOponenteActual] = useState([]);
  const [chatVisible, setChatVisible] = useState(false);

  const toggleChat = () => {
    setChatVisible(!chatVisible);
  };
  useEffect(() => {
    for(const jugador of partida.jugadores){
      if(jugador.user.username===tokenService.getUser().username){
        setCartaMiradaFija(jugador.miradaFija);
        break;
      }
    }
    sendPartidaToSocket(partida);
  }, []);
  useEffect(() => {
    if(partida.turnoActivo){
        setRenderCartasJugador(true);
        setCartaUsadaJugador(null);
        setCartaUsadaOponente(null);
    }
    else{
      for(const jugador of partida.jugadores){
        if(jugador.user.username===tokenService.getUser().username){
          if(renderCartasJugador){
            setJugadorCartas(jugador.cartas);
            setCartaUsadaJugador(jugador.cartaUsada);
            setRenderCartasJugador(false);
          }
          setJugadorActual(jugador);
        }else{
          setOponenteActual(jugador);
          setOponenteCartas(jugador.cartas);
          setCartaUsadaOponente(jugador.cartaUsada);
        } 
      }
    }
  }, [partida]);

  const quitarCartaJugadorFrontend= function(carta,mandarMazo){
    if(mandarMazo){
      setMazo([...mazo, carta]);
    }else{
      setCartaUsadaJugador(carta);
    }
    setJugadorCartas((cartas) => cartas.filter((c) => c !== carta));
  }
  const quitarCartaJugadorBackend= async function(carta,mandarMazo){
      const partidaActualizada=await update(`/api/v1/partidas/quitarcarta/${tokenService.getUser().username}/${mandarMazo}`,carta);
      if(partidaActualizada!=null)sendPartidaToSocket(partidaActualizada);
  }
  const darCartaJugadorFrontend= function(carta){
    setJugadorCartas([...jugadorCartas,carta]);
    if(cartaUsadaJugador===carta)setCartaUsadaJugador(null);
    else setMazo((cartas) => cartas.filter((c) => c !== carta));
  }
  const darCartaJugadorBackend= async function(carta){
    const partidaActualizada=await update(`/api/v1/partidas/darcarta/${tokenService.getUser().username}`,carta);
    if(partidaActualizada!=null)sendPartidaToSocket(partidaActualizada);
  }
  const handleDescartar = async function(carta) {
    setCartaSeleccionada(null);
    if(jugadorCartas.length===7){
      darCartaJugadorFrontend(cartaMiradaFija);
      quitarCartaJugadorFrontend(carta,true)
      await darCartaJugadorBackend(cartaMiradaFija);
      quitarCartaJugadorBackend(carta,true);
    }else{
      quitarCartaJugadorFrontend(carta,true)
      quitarCartaJugadorBackend(carta,true);
    }
  };
  const usarCarta = async function(carta) {
    setCartaSeleccionada(null);
    setCartaUsadaJugador(carta);
    quitarCartaJugadorFrontend(carta,false);
    quitarCartaJugadorBackend(carta,false);
  };
  const renderCarta = function(carta, verCarta){
    if(carta==null)return;
    if(verCarta){
      if(jugadorCartas.includes(carta)){
        return(
          <div onClick={() =>{setCartaSeleccionada(carta);} } style={{ 
            ...cartaStyle, backgroundImage:`url(${carta.imagen})`, cursor: 'pointer',}}>
        </div>
        );
      }else{
        return(
          <div style={{ 
            ...cartaStyle, backgroundImage:`url(${carta.imagen})`}}>
        </div>
        );
      }
    }else{
      return(
        <div style={{ 
          ...cartaStyle, backgroundImage:`url(/ImagenesCartas/DorsoCartas.jpg)`}}>
      </div>
      );
    }
  };
  const handleClickFuera = (event) => {
    if (event.target === event.currentTarget) {
      setCartaSeleccionada(null);
      setChatVisible(false);
     
    }
   
  };
  const renderCartaDatos = function (jugador) {
    
    const salud = jugador.salud
    const precision = jugador.precision
    const balas = jugador.balas
    const renderFlechasSalud = () => {
      const flecha = [];
      const posiciones = [15,45,70];
      for (let i = 0; i <= 2; i++) {
        if(salud === i ){
          const leftPosition = posiciones[i]
          flecha.push(<div style={{ ...flechaStyle, left: `${leftPosition}%`, top: '85%', color: 'green' }}>↑</div>);
          flecha.push(<div style={{ position: 'absolute', left: `${leftPosition}%`, top: '105%', color: 'green' }}>SALUD</div>);
        }
      }
      return flecha;
    };
    const renderFlechasPrecision = () => {
      const flecha = [];
      const posiciones = [45,37,29,20,12,5,-3];
      for (let i = 0; i <= 6; i++) {
          if(precision === i ){
            const topPosition = posiciones[i]
            flecha.push(<div style={{ ...flechaStyle, left: '-15%', top: `${topPosition}%`, color: 'blue' }}>→</div>);
            flecha.push(<div style={{ position: 'absolute', left: `-50%`, top: `${topPosition}%`, color: 'blue' }}>PRECISION</div>);
          }
      }
      return flecha;
    };
    const renderFlechasBalas = () => {
      const flecha = [];
      const posiciones = [45,37,29,20,12,5,-3];
      for (let i = 0; i <= 6; i++) {
        if(balas === i ){
          const topPosition = posiciones[i]
          flecha.push(<div style={{ ...flechaStyle, left:'85%', top:`${topPosition}%` , color: 'red' }}>←</div>);
          flecha.push(<div style={{ position: 'absolute', left: `90%`, top: `${topPosition}%`, color: 'red' }}>BALAS</div>);
        }
      }
      return flecha;
    };

  return (
      <div style={{ ...cartaDatosStyle, backgroundImage: `url("/ImagenesCartas/CartaDatos.jpg")`, position: 'relative' }}>
          {renderFlechasSalud()}
          {renderFlechasPrecision()}
          {renderFlechasBalas()}
      </div>
  );
};
  const flechaStyle = {
    position: 'absolute',
    fontSize: '50px',
    fontWeight: 'bold',
  };
  
   
  const renderCartasSeleccionadas = function(verCarta){
    return(
     <div>
       {cartaUsadaJugador!=null&&(<div key={1} style={{ 
        ...cartasSeleccionadaStyle, backgroundImage:verCarta?`url(${cartaUsadaJugador.imagen})`:
        `url(/ImagenesCartas/DorsoCartas.jpg)`}}>
      </div>)}
     {cartaUsadaOponente!=null&&(<div key={1} style={{ 
      ...cartasSeleccionadaStyle, backgroundImage:verCarta?`url(${cartaUsadaOponente.imagen})`:
      `url(/ImagenesCartas/DorsoCartas.jpg)`}}>
    </div>)}
     </div>
    );
  }
  const cartaStyle = {
    backgroundColor: '#fff',
    padding: '40px',
    margin: '10px',
    textAlign: 'center',
    borderRadius: '10px',
    boxShadow: '0 4px 10px rgba(0, 0, 0, 0.5)',
    width: '100px', 
    height: '150px',
    backgroundPosition: 'center',
    backgroundSize: 'cover'
  };
  const cartaDatosStyle = {
    backgroundColor: '#fff',
    padding: '40px',
    margin: '10px',
    textAlign: 'center',
    borderRadius: '10px',
    boxShadow: '0 10px 20px rgba(0, 0, 0, 0.5)',
    width: '180px', 
    height: '250px',
    backgroundPosition: 'center',
    backgroundSize: 'cover',
  };
  const cartasSeleccionadaStyle = {
    backgroundColor: '#fff',
    padding: '40px',
    margin: '10px',
    textAlign: 'center',
    borderRadius: '10px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    width: '100px', 
    height: '150px',
    backgroundPosition: 'center',
    backgroundSize: 'cover'
  };
  const cartasSeleccionadasContainerStyle = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height:'200px',
    margin:"35px"
  };
  const contenedorJugadoresStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundImage: `url("/home_background.jpg")` ,
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'center',
    height:"100%",
    width:"75%"
  };

  const barajaStyle = {
    display: 'flex',
    alignItems: 'flex-start', 
    width:'1100px',
    justifyContent: 'center',
  };
  const datosContainerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent:'center',
    width:"25%",
    padding:'30px',
    backgroundImage: `url("/tablon.jpg")` ,
    height:"100%",
  };
  const cartaContainerStyle = {
    display: 'flex',
    alignItems: 'center', 
    justifyContent:'center'
  };
  const pageContentStyle = {
    display: 'flex',
    width:"100vw",
    height:"100vh",
    margin:0,
    padding:0
  };
  const manejarCambioInput = (event) => {
    setMensaje(event.target.value);
  };
  const manejarPresionarEnter = async(event) => {
    if (event.key === 'Enter') {
      const partidaActualizada=await update(`/api/v1/partidas/escribirchat/${mensaje}/${tokenService.getUser().username}`,{});
      if(partidaActualizada!=null){
        sendPartidaToSocket(partidaActualizada);
      
      }
      setMensaje('');
    }
  };
  const renderMensaje = function(mensaje){
    return(
      <div>
        <p>{mensaje.escritor}: {mensaje.textoMensaje}</p>
      </div>
    );
  }
 if(partida.turnoActivo){
    return(
      <PartidaBatallaCartas
      partida={partida}
      sendPartida={sendPartidaToSocket}/>
    );
 }else{
  return (
    <div style={pageContentStyle}>
      <div style={contenedorJugadoresStyle} onClick={handleClickFuera}>
      {chatVisible&& <VisualizacionChat
          mensaje={mensaje}
          partida={partida}
          manejarCambioInput={manejarCambioInput}
          manejarPresionarEnter={manejarPresionarEnter}
          renderMensaje={renderMensaje}
          jugadorActual = {jugadorActual}
          toggleChat={toggleChat}
        />
      }
      {cartaSeleccionada&&!chatVisible&&<VisualizacionCarta
      carta={cartaSeleccionada}
      descartarCarta={handleDescartar}
      cartasJugador={jugadorCartas}
      usarCarta={usarCarta}
      cartaUsada={cartaUsadaJugador}/>}

      <div style={cartaContainerStyle}>
       <div style={barajaStyle}>
       {oponenteCartas.map((carta) => renderCarta(carta, false))}
       </div>
      </div>
      <div style={cartasSeleccionadasContainerStyle}>
      {renderCartasSeleccionadas(false)}
      </div>
      <div style={cartaContainerStyle}>
        <div style={barajaStyle}>
        {jugadorCartas.map((carta) => renderCarta(carta,true))}
        </div>
      </div>
      <button
        style={{
          backgroundColor: '#8B4513', 
          color: 'white', 
          padding: '3px 150px', 
          borderRadius: '20px', 
          border: 'none',
          cursor: 'pointer', 
          fontSize: '25px', 
          marginTop: '45px',
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', 
        }}
        onClick={toggleChat}
      >
        Chat
      </button>
    </div>
    <div style={datosContainerStyle}>
    {renderCartaDatos(oponenteActual)}
    <div style={{height:"100px"}}></div>
    {renderCartaDatos(jugadorActual)}
  </div>
  </div>
  );
 }
}