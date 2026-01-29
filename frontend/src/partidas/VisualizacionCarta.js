export default function VisualizacionCarta(props) {
    const {carta,descartarCarta,cartasJugador,usarCarta,cartaUsada}=props;
    const cartaStyle = {
        border: '2px solid #444',
        backgroundColor: '#fff',
        padding: '40px',
        margin: '10px',
        textAlign: 'center',
        borderRadius: '10px',
        boxShadow: '0 10px 20px rgba(0, 0, 0, 0.5)',
        width: '300px', 
        height: '450px',
        backgroundPosition: 'center',
        backgroundSize: 'cover'
      };
      const visualizacionStyle = {
        width: '600px',
        height: '600px',
        background: 'rgba(0, 0, 0, 0.5)', 
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1000, 
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
      };
      
    return(
        <div style={visualizacionStyle}>
             <div style={{ ...cartaStyle,
                backgroundImage:`url(${carta.imagen})`}}>
            </div>
            {cartasJugador.some(carta => carta.tipo==="MIRADA_FIJA")&&cartaUsada==null&&
            <button style={{ backgroundColor: '#8B4513', color: 'white' }} onClick={() => usarCarta(carta)}>Usar</button>}
            {!cartasJugador.some(carta => carta.tipo==="MIRADA_FIJA")&&cartaUsada==null&&
            <button style={{ backgroundColor: 'red', color: 'white' }} onClick={() => descartarCarta(carta)}>Descartar</button>}
        </div>
    );
    
    
}