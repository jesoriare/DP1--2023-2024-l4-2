import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, ButtonGroup, Table } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";
import { update } from "../../peticiones";
import { useNavigate } from "react-router-dom";

const jwt = tokenService.getLocalAccessToken();

export default function ListOwner() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [partidas, setPartidas] = useFetchState(
    [],
    `/api/v1/partidas`,
    jwt,
    setMessage,
    setVisible
  );
  const [alerts, setAlerts] = useState([]);

  const navigator = useNavigate();
  async function Unirse(){
      const user = tokenService.getUser();
      if(user==null){
          alert("Necesitas iniciar sesión para unirte a una partida");
      }
      else{
          const partida = await update(`api/v1/partidas/unirsepartida/${user.id}`,{});
          if(partida==null) {
              alert("No hay partidas")
          }
          navigator(`/partidagf/${partida.id}`) 
          console.log(partida); 
      }
  }

  const partidaList = partidas.filter(partida=>partida.estado==='ESPERANDO').map((partida) => {
    const usernames = partida.jugadores.length > 0
    ?partida.jugadores.map((jugador) => jugador.user.username).join(', '):"No hay jugadores";
    return (
      <tr key={partida.id}>
        <td>{usernames}</td>
        <td>{partida.estado}</td>
        <td>
            <Button
              size="sm"
              color="danger"
              aria-label={"unirse-" + partida.id}
              onClick={Unirse}
            >
              Unirse
            </Button>
        </td>
      </tr>
    );
  });

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div>
      <div className="admin-page-container">
        <h1 className="text-center">Listado de Partidas</h1>
        {alerts.map((a) => a.alert)}
        {modal}
        <div>
          <Table aria-label="owners" className="mt-4">
            <thead>
              <tr>
                <th width="60%">Jugadores</th>
                <th width="20%">Estado</th>
                <th width="20%">Acciones</th>
              </tr>
            </thead>
            <tbody>{partidaList}</tbody>
          </Table>
        </div>
      </div>
    </div>
  );
  
}