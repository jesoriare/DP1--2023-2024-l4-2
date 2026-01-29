import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, ButtonGroup, Table } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

const jwt = tokenService.getLocalAccessToken();

export default function ListAdmin() {
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

  const partidaList = partidas.map((partida) => {
    const usernames = partida.jugadores.length > 0
    ?partida.jugadores.map((jugador) => jugador.user.username).join(', '):"No hay jugadores";
    return (
      <tr key={partida.id}>
        <td>{usernames}</td>
        <td>{partida.estado}</td>
        <td>
          <ButtonGroup>
            <Button
              size="sm"
              color="warning"
              aria-label={"edit-" + partida.id}
              tag={Link}
              to={"/partidas/" + partida.id}
            >
              Editar
            </Button>
            <Button
              size="sm"
              color="danger"
              aria-label={"delete-" + partida.id}
              onClick={() =>
                deleteFromList(
                  `/api/v1/partidas/${partida.id}`,
                  partida.id,
                  [partidas, setPartidas],
                  [alerts, setAlerts],
                  setMessage,
                  setVisible
                )
              }
            >
              Eliminar
            </Button>
          </ButtonGroup>
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
      <div className="float-right">
        <Button style={{ backgroundColor: "#964B00" }} tag={Link} to="/partidas/new">
           Añadir Partida
        </Button>
      </div>
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