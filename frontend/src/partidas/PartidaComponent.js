import React, { useEffect } from 'react';
import { useState } from "react";
import getIdFromUrl from '../util/getIdFromUrl';
import tokenService from '../services/token.service';
import useFetchState from '../util/useFetchState';
import Stomp from 'webstomp-client'; //npm install webstomp-client
import SockJS from 'sockjs-client';//npm install sockjs-client
import PartidaLobby from './PartidaLobby';
import { update } from '../peticiones';
import PartidaStart from './PartidaStart';
import PartidaFinal from './PartidaFinal';
import { useNavigate } from 'react-router-dom';
const jwt = tokenService.getLocalAccessToken();

export default function PartidaComponent(props){
const navigator = useNavigate();
const {setNavVisible}=props;
const gameId = getIdFromUrl(2);
const [stompClient, setStompClient] = useState(null);
const [message, setMessage] = useState(null);
const [visible, setVisible] = useState(false);
const [partida,setPartida] = useFetchState(
    [],
    `/api/v1/partidas/${gameId}`,
    jwt,
    setMessage,
    setVisible
  );
  useEffect(()=>{
  const socket = new SockJS(`http://localhost:8080/gunfightersocket`);
  const stomp = Stomp.over(socket);
  setStompClient(stomp);
  stomp.connect({}, 
    () => {
      stomp.subscribe('/partidasgf/'+gameId, (mensaje) => {
        const mensajeString=String(mensaje);
        const index=mensajeString.indexOf("{");
        const partidaString=mensajeString.substring(index);
        const partidaActualizada=JSON.parse(partidaString);
        setPartida(partidaActualizada);
    });
  }
  );
  setNavVisible(false);
  },[]);
    useEffect(()=>{
    
    },[partida]); 
    const sendPartidaToSocket=async function(partida){
      if(partida.id==null)return;
      await stompClient.send("/gunfighter/updateGame/"+partida.id,JSON.stringify(partida),{});
   }
   async function salirPartida(){
       const user = tokenService.getUser();
       if(user!=null){
           const partida = await update(`/api/v1/partidas/salirpartida/${user.id}`,{});
           if(partida==null) {
               alert("No estás en ninguna partida")
           }else{
           navigator(`/`);
          stompClient.disconnect();
          setNavVisible(true); 
          }  
       }
   } 
    if (partida.estado==="JUGANDO"){
      return (
        <div>
         <PartidaStart partida={partida}
         sendPartidaToSocket={sendPartidaToSocket}/>
        </div>
     );
    }else if (partida.estado ==="ESPERANDO"){
      return (
        <div>
         <PartidaLobby partida={partida}
        salirPartida={salirPartida}
        sendPartidaToSocket={sendPartidaToSocket}/>
        </div>
     );
    }else if (partida.estado ==="FINALIZANDO"){
      return (
        <div>
         <PartidaFinal 
         partida={partida}
        salirPartida={salirPartida}/>
        </div>
        );
    }
} 