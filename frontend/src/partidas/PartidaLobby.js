import React from 'react';
import { Button } from 'reactstrap';
import "../../src/static/css/partida/partidaLobbyPage.css";

export default function PartidaLobby(props) {

    //const {partida,stompClient,taskId} = props;
const {partida,salirPartida,sendPartidaToSocket} = props;
 
   return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", height: "100vh" }}>
      <div className="partidaLobby-page-container" style={{ textAlign: "center" }}>
        <h2 style={{ marginBottom: "30px", fontSize: "3em" }}>GUNFIGHTER</h2>
        <div style={{ marginBottom: "20px" }}>
          <p style={{ fontSize: "1.5em" }}>Esperando a otro jugador...</p>
        </div>
        <Button
          style={{
            backgroundColor: "red", 
            color: "white", 
            fontSize: "1.2em", 
            marginTop: "10px", 
          }}
          onClick={salirPartida}
        >
          Salir de la partida
        </Button>
      </div>
    </div>
  );
  
  
  
  
  
  
}