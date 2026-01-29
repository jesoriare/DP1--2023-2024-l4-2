import { useEffect, useState } from "react";
import { update } from "../peticiones";
import tokenService from '../services/token.service';
export default function PartidaBatallaCartas(props){
    const {partida,sendPartida}=props;
    const [cartaJugador,setCartaJugador]= useState();
    const [cartaOponente,setCartaOponente]= useState();
    const [numDado,setNumDado]= useState(partida.dado.numero);
    const [puedeUsarDado,setPuedeUsarDado]= useState(false);
    const [jugadorActual,setJugadorActual] = useState([]);
    const [oponenteActual,setOponenteActual] = useState([]);

    useEffect(() => {
      if(partida.jugadorTurno===tokenService.getUser().username)setPuedeUsarDado(true);
      else setPuedeUsarDado(false);
      if(partida.dado.tirando)moverDado(partida,false);
      for(const jugador of partida.jugadores){
        if(jugador.user.username===tokenService.getUser().username){
          if(jugador.cartaUsada!=null)setCartaJugador(jugador.cartaUsada);
        }else{
          if(jugador.cartaUsada!=null)setCartaOponente(jugador.cartaUsada);
        } 
      }
    }, [partida]);

    useEffect(() => {
      for (const jugador of partida.jugadores) {
        if (jugador.user.username === tokenService.getUser().username) {
          setJugadorActual(jugador);
        } else {
          setOponenteActual(jugador);
        }
      }
    }, [partida]);
    
      const renderCarta = function(carta, verCarta){
        if(carta==null)return;
        if(verCarta){
            return(
                <div style={{ 
                  ...cartaStyle, backgroundImage:`url(${carta.imagen})`}}>
              </div>
              );
        }else{
          return(
            <div style={{ 
              ...cartaStyle, backgroundImage:`url(/ImagenesCartas/DorsoCartas.jpg)`}}>
          </div>
          );
        }
      };
      const tirarDado= async function(){
        if(!partida.dado.tirando&&puedeUsarDado){
          const partidaActualizada= await actualizarPartidaBackend(`/api/v1/partidas/tirardado/${partida.id}`, {},true);
          moverDado(partidaActualizada,true);
        }
      }
      const moverDado= function(partidaActualizada,actualizarPartida){
        let cambios=0;
        let caraDado = partidaActualizada.dado.numero;
        const taskId=setInterval(() => {
          setNumDado((prevNumDado) => {
            while (prevNumDado === caraDado || caraDado === 0) {
              caraDado = Math.floor(Math.random() * 6) + 1;
            }
            if (cambios === 20) {
              clearInterval(taskId);
              if (actualizarPartida) {
                pararDadoYUsarCarta(partidaActualizada);
              }return partidaActualizada.dado.numero;
            }
            cambios++;
            return caraDado;  
          });
        }, 200);
      }
      const pararDadoYUsarCarta= async function(partidaActualizada){
        await actualizarPartidaBackend(`/api/v1/partidas/parardado/${partidaActualizada.id}`, {},false);
        await actualizarPartidaBackend(`/api/v1/partidas/usarcarta/${cartaJugador.id}/${tokenService.getUser().username}`,{},true)
      }
      const actualizarPartidaBackend=async function(peticion,partida,actualizar){
        const partidaActualizada= await update(peticion,partida);
        if(actualizar)sendPartida(partidaActualizada);
        return partidaActualizada;
      }
      const cartaCointainerStyle = {
        display: 'flex',
        margin:0,
        padding:0,
        justifyContent:'center',
        alignItems:'center',
        backgroundImage: `url("/tablon.jpg")` ,
        width: "900px",
        height:"550px",
        borderRadius: '10px',
      };
      const cartaStyle = {
        borderRadius: '10px',
        boxShadow: '0 20px 45px rgba(0, 0, 0, 0.5)',
        width: '300px', 
        height: '450px',
        backgroundPosition: 'center',
        backgroundSize: 'cover',
      };
      const cartasUsadasContainerStyle = {
        display: 'flex',
        flexDirection: 'column',
        backgroundImage: `url("/home_background.jpg")` ,
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        justifyContent:'center',
        alignItems:'center',
        width:"75%",
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
      
      const renderDado= function(){
        if(partida.dado.tirando||!puedeUsarDado){
          return(<div style={{...dadoStyle,backgroundImage:`url("/Dado${numDado}.jpg")`}}></div>);
        }else{
          return(<div style={{...dadoStyle,backgroundImage:`url("/Dado${numDado}.jpg")`,cursor:"pointer"}} 
          onClick={()=>tirarDado()}></div>);
        }
      }
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
      const datosContainerStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent:'center',
        width:"25%",
        padding:'30px',
        height:"100%",
        backgroundImage: `url("/tablon.jpg")` ,
      };
      const pageContentStyle = {
        display: 'flex',
        width:"100vw",
        height:"100vh",
        margin:0,
        padding:0,
        
      };
      const dadoStyle = {
        width: "170px",
        height:"170px",
        backgroundColor: "white",
        marginLeft: "40px",
        marginRight: "40px",
        borderRadius: '35px',
        border: '5px solid #262626',
        backgroundPosition: 'center',
        backgroundSize: 'cover',
      };
      const estiloTextoTurno = {
        color: '#502718',
        fontSize: '40px',
        fontWeight: 'bold',
      };
    return(
       <div style={pageContentStyle}>
         <div style={cartasUsadasContainerStyle}>
          {partida.jugadorTurno===tokenService.getUser().username ? <p style={estiloTextoTurno}>Es tu turno, tira el dado </p> : <p style={estiloTextoTurno}>Turno del oponente</p>}
            <div style={cartaCointainerStyle}>
            <div>
            {renderCarta(cartaJugador,true)}
            </div>
            {renderDado()}
            <div>
            {renderCarta(cartaOponente,true)}
            </div>
            </div>
        </div>
        <div style={datosContainerStyle}>
        {renderCartaDatos(oponenteActual)}
        <div style={{height:"100px"}}></div>
        {renderCartaDatos(jugadorActual)}
      </div>
       </div>
    );
}