import React from 'react';
import '../App.css';
import '../static/css/home/home.css'; 
import tokenService from '../services/token.service';
import {create, get, remove, update} from '../peticiones'
import { useNavigate } from 'react-router-dom'

export default function Home(){
    const navigator = useNavigate();
    async function Unirse(){
        const user = tokenService.getUser();
        if(user==null){
            alert("Necesitas iniciar sesión para unirte a una partida");
        }
        else{
            const partida = await update(`api/v1/partidas/unirsepartida/${user.id}`,{});
            if(partida==null) {
                alert("No hay partidas");
            }else{
                navigator(`/partidagf/${partida.id}`) 
            }
        }
    }
 
    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1 style={{ color: '#964B00' }}>GunFighter</h1>
                <button onClick={Unirse} style={{ backgroundColor: '#964B00', color: 'white' }}>
                    Unirse Partida
                </button>
            </div>
        </div>
    );
    
    
     
    
}