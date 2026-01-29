
import React, { useEffect,useState } from 'react';
import tokenService from '../services/token.service';
import useFetchState from '../util/useFetchState';
export default function EstadisticasComponent(props){
    const jwt = tokenService.getLocalAccessToken();
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [estadisticas,setEstadisticas] = useFetchState(
    [],
    `/api/v1/estadisticas/${tokenService.getUser().username}`,
    jwt,
    setMessage,
    setVisible
  );
  
const estiloContenedor = {
    height: '90vh', 
    width: '100vw',
    textAlign: 'center',
    overflow: 'hidden',
    backgroundImage: `url("/home_background.jpg")`,
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'center',
    margin: 0,
    padding: 0,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
  };
  
  const estiloUsuario = {
    color: 'brown',
  };
  
  const estiloImagen = {
    width: '15%',
    marginTop: '20px',
  };

     if(estadisticas.partidasJugadas!=null){
        return (
            <div style={estiloContenedor}>
                <h1>Estadísticas</h1>
                <p style={estiloUsuario}>Usuario: {tokenService.getUser().username}</p>
                <p>Partidas jugadas: {estadisticas.partidasJugadas}  </p>
                <p>Partidas ganadas: {estadisticas.partidasGanadas}  </p>
                <p>Tiempo medio de partida: {estadisticas.tiempoMedioDePartida}  </p>
                <p>Numero de turnos medio por partida: {estadisticas.numerodeTurnosMedioPorPartida} </p>
                <p>Carta más usada: </p>
                <img src={estadisticas.cartaMasUsada} alt="Carta más usada" style={estiloImagen} />
            </div>
        );
    }else{
        return(
            <div style={estiloContenedor}>
                <h1>Estadísticas</h1>
                <p>No has jugado todavía</p>
            </div>
        );
       }

}