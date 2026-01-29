import React, { useState, useEffect } from 'react';
import "../../src/static/css/partida/partidaLobbyPage.css";
import tokenService from '../services/token.service';
export default function PartidaFinal(props){
    const{partida,salirPartida}=props;
    const [ganador,setGanador]=useState(false);
    useEffect(()=>{
        for(const jugador of partida.jugadores){
            if(jugador.salud!==0&&jugador.user.username===tokenService.getUser().username)setGanador(true);
        }
    },[partida]);
    return (
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", height: "100vh" }}>
          <div className="partidaLobby-page-container" style={{ textAlign: "center" }}>
            <h2 style={{ marginBottom: "30px", fontSize: "3em" }}>GUNFIGHTER</h2>
            <div style={{ marginBottom: "20px"}}>
              {ganador&&<div>
                <p style={{ fontSize: "1.5em" }}>Has ganado la partida, enhorabuena</p>
                <div style={{marginLeft:"120px",backgroundImage: `url("/trofeo.png")`,backgroundSize: 'cover',width:'200px',height:'200px'}}></div>
                </div>}
                {!ganador&&<div>
                <p style={{ fontSize: "1.5em" }}>Has sido derrotado, suerte en la próxima</p>
                <div style={{marginLeft:"140px",backgroundImage: `url("/partidaperdida.png")`,backgroundSize: 'cover',width:'200px',height:'200px'}}></div>
                </div>}
            </div>
            <button
              style={{
                backgroundColor: "red", 
                color: "white", 
                fontSize: "1.2em", 
                marginTop: "10px", 
              }}
              onClick={salirPartida}
            >
              Salir de la partida
            </button>
          </div>
        </div>
      );
}