import React from 'react';
import tokenService from '../services/token.service';

const jwt = tokenService.getLocalAccessToken();

export default function Reglas() {

    return (
        <div>
            <h2>Reglas</h2>
            <img src="/Rulebook_page-0001.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0002.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0003.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0004.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0005.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0006.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0007.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />
            <img src="/Rulebook_page-0008.jpg" alt="Reglas pt1" style={{ height: '300vh', width: '100%' }} />


        </div>
    );
}

