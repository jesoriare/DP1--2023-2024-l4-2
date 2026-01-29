export default function VisualizacionChat(props) {
  const { mensaje, partida, manejarCambioInput, manejarPresionarEnter, renderMensaje,jugadorActual,toggleChat } = props;
  const chatContainerStyle = {
    backgroundColor: 'white',
    padding: '20px', // Aumentando el padding para hacer el contenedor blanco más grande
    borderRadius: '10px',
    marginTop: '20px',
    maxHeight: '400px',
    overflowY: 'auto',
    width: '100%', // Haciendo el contenedor blanco más ancho
    margin: '0 auto', // Centrando el contenedor blanco en el componente

  };

  const inputStyle = {
    width: '80%',
    padding: '10px',
    borderRadius: '5px',
    border: '5px solid #ccc',
    boxSizing: 'border-box',
  };

  const visualizacionStyle = {
    width: '600px',
    height: '600px',
    background: 'rgba(0, 0, 0, 0.8)',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',    
    zIndex: 1000,
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    boxShadow: '0 0 20px rgba(0, 0, 0, 0.3)',
    justifyContent: 'center',
  };
  const closeButtonStyle = {
    cursor: 'pointer',
    color: 'white',
    borderRadius: '5px',
    fontSize: '18px',
    alignSelf: 'flex-end',
    marginRight: '20px',
    backgroundColor: '#9C0606 ',
    padding: '5px'
  };
  return (
    <div style={visualizacionStyle}>
      <div style={closeButtonStyle} onClick={toggleChat}>
        X
      </div>
      <input
          type="text"
          value={mensaje}
          onChange={manejarCambioInput}
          onKeyPress={manejarPresionarEnter}
          style={inputStyle}
          placeholder="Escribir mensaje"
        />  
      <div style={{width:'475px',height:'400px',backgroundColor: 'white'}}>
      <div style={chatContainerStyle}>
        <div>
          {partida.chat.map((mensaje) => (
            <div
              key={mensaje.timestamp}
              style={{
                color: mensaje.escritor === jugadorActual.user.username ? '#8B4513' : '#DAA520',
              }}
            >
              {renderMensaje(mensaje)}
            </div>
          ))}
        </div>
      </div>
      </div>
    </div>
  );
  
}