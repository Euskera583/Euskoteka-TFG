import React, { useContext, useState } from 'react';
import { Contexto } from '../../context/Contexto';
import Button from '../Utilidades/Button'

const Comentario = ({ user, contenido, fecha, like, id, respuestas = [], nivel, eliminado }) => {

  const { toggleLike, crearComentario, isLoged, usuario, eliminarComentario } = useContext(Contexto);
  const [mostrarRespuestas, setMostrarRespuestas] = useState(false);
  const [escribiendoRespuesta, setEscribiendoRespuesta] = useState(false); //variable que mantiene el estado de escritura de la respuesta al momento de hacerlo
  const [textoRespuesta, setTextoRespuesta] = useState(""); //variable con el texto de la repsuesta

  //funcion que da un like a un comentario en especifico
  const handleLike = async () => {
    await toggleLike(id);
  };

  //funcion que envia la respuesta a un comentario
  const enviarRespuesta = async (e) => {
    e.preventDefault();
    if (textoRespuesta.trim().length < 2) return;

    const ok = await crearComentario(textoRespuesta, id);

    if (ok) {
      setTextoRespuesta("");
      setEscribiendoRespuesta(false);
      setMostrarRespuestas(true);
    }
  };

  return (
    <div className='comentario' nivel={nivel} style={{ marginLeft: nivel * 22 }}>

      {/* cabecera del comentario */}
      <div className='comentHeader'>
        <h4>{user}</h4>
        <h4>{fecha}</h4>

        {!eliminado && (
          <Button
            text={`Me gusta ❤️: ${like}`}
            func={handleLike}
            estilo="btnLike"
          />
        )}
      </div>

      {/* contenido del comentario */}
      <div className='comentContent'>
        <p style={{ opacity: eliminado ? 0.6 : 1 }}>
          {eliminado ? "[Comentario eliminado]" : contenido}
        </p>
      </div>

      {/* acciones del comentario */}
      <div className="comentActions">

        {isLoged && !eliminado && (
          <Button
            text={'Responder'}
            func={() => setEscribiendoRespuesta(!escribiendoRespuesta)}
            estilo="btnResponder"
          />
        )}

        {/* muestra boton de ver más respuestas si las hay */}
        {Array.isArray(respuestas) && respuestas.length > 0 && (
          <Button
            text={`${mostrarRespuestas
              ? 'Ocultar respuestas'
              : `Ver respuestas (${respuestas.length})`}
            }`}
            func={() => setMostrarRespuestas(!mostrarRespuestas)}
            estilo="btnToggleResp"
          />
        )}

        {/* muestra boton de eliminar comentario si es el usuario que lo escribió o administrador */}
        {isLoged && !eliminado && (
          (usuario?.username === user || usuario?.rol === "ADMIN") && (
            <Button
              text={'🗑 Eliminar'}
              func={() => eliminarComentario(id)}
              estilo="btnEliminar"
            />
          )
        )}
      </div>

      {/* formulario de respuestas */}
      {escribiendoRespuesta && !eliminado && (
        <form className="formRespuesta" onSubmit={enviarRespuesta}>
          <textarea
            value={textoRespuesta}
            onChange={(e) => setTextoRespuesta(e.target.value)}
            placeholder="Escribe una respuesta..."
          />
          <button type="submit">Enviar</button>
        </form>
      )}

      {/* render recursivo para mostrar respuestas de un comentario */}
      {mostrarRespuestas &&
        (Array.isArray(respuestas) ? respuestas : []).map(resp => (
          <Comentario
            key={resp.id}
            id={resp.id}
            user={resp.username}
            contenido={resp.contenido}
            fecha={resp.fecha}
            like={resp.likes}
            respuestas={resp.respuestas}
            nivel={nivel + 1}
            eliminado={resp.eliminado}
          />
        ))
      }
    </div>
  )
}

export default Comentario;