import { useState } from "react";
import { Link } from "react-router-dom";
import { Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import getIdFromUrl from "../../util/getIdFromUrl";
import useFetchState from "../../util/useFetchState";

const jwt = tokenService.getLocalAccessToken();

export default function PartidaEditAdmin() {
  const emptyItem = {
    id: "",
    jugadores:"",
    estado: "",
  };
  const id = getIdFromUrl(2);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [partida, setPartida] = useFetchState(
    emptyItem,
    `/api/v1/partidas/${id}`,
    jwt,
    setMessage,
    setVisible,
    id
  );

  function handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    setPartida({ ...partida, [name]: value });
  }

  function handleSubmit(event) {
    event.preventDefault();
    console.log(partida);

    fetch("/api/v1/partidas" + (partida.id ? "/" + partida.id : ""), {
      method: partida.id ? "PUT" : "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(partida),
    })
      .then((response) => response.json())
      .then((json) => {
        if (json.message) {
          setMessage(json.message);
          setVisible(true);
        } else window.location.href = "/partidas";
      })
      .catch((message) => alert(message));
  }

  const modal = getErrorModal(setVisible, visible, message);
/*
  return (
    <div className="auth-page-container">
      {<h2>{id !== "new" ? "Editar Partida" : "Añadir Partida"}</h2>}
      {modal}
      <div className="auth-form-container">
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input">
            <Label for="estado" className="custom-form-input-label">
              Estado
            </Label>
            <Input
              id="estado"
              name="estado"
              required
              type="select"
              value={partida.estado || ""}
              onChange={handleChange}
              className="custom-input"
            >
              <option value="">None</option>
              <option value="DESACTIVADA">DESACTIVADA</option>
              <option value="ESPERANDO">ESPERANDO</option>
              
            </Input>
          </div>
          <div className="custom-button-row">
            <button className="auth-button">Guardar</button>
            <Link
              to={`/partidas`}
              className="auth-button"
              style={{ textDecoration: "none" }}
            >
              Cancelar
            </Link>
          </div>
        </Form>
      </div>
    </div>
  );*/
  return (
    <div className="auth-page-container" style={{ backgroundColor: "#964B00", color: "#ffffff" }}>
      <h2 style={{ color: "#000000" }}>{id !== "new" ? "Editar Partida" : "Añadir Partida"}</h2>
      {modal}
      <div className="auth-form-container">
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input">
            <Label for="estado" className="custom-form-input-label" style={{ color: "#964B00", borderColor: "#964B00" }}>
              Estado
            </Label>
            <Input
              id="estado"
              name="estado"
              required
              type="select"
              value={partida.estado || ""}
              onChange={handleChange}
              className="custom-input"
              style={{ backgroundColor: "#964B00", color: "#ffffff", borderColor: "#964B00" }}
            >
              <option value="">None</option>
              <option value="DESACTIVADA">DESACTIVADA</option>
              <option value="ESPERANDO">ESPERANDO</option>
            </Input>
          </div>
          <div className="custom-button-row">
            <button className="auth-button" style={{ backgroundColor: "#964B00", color: "#ffffff", border: "none" }}>
              Guardar
            </button>
            <Link
              to={`/partidas`}
              className="auth-button"
              style={{ backgroundColor: "red", color: "#ffffff", textDecoration: "none", border: "none" }}
            >
              Cancelar
            </Link>
          </div>
        </Form>
      </div>
    </div>
  );
  
  
  
  
  
  
}
