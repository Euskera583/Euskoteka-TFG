import React, { useContext, useEffect } from 'react';

import { Contexto } from '../../context/Contexto'
import Comentario from './Comentario';
import FormComentario from './FormComentario';

const ListaComentarios = () => {

  const { fetchComentarios, comentarios, tipoObjetivo, idObjetivo } = useContext(Contexto);

  //se obtiene la lista de comentarios segun el tipo e id de objetivo
  useEffect(() => {
    if (!tipoObjetivo || !idObjetivo) return;
    fetchComentarios();
  }, [tipoObjetivo, idObjetivo]);

  return (
    <>
      <div className='listaComentarios'>
        <FormComentario />
        {comentarios.length > 0 ? (
          comentarios.map((coment) => (
            <Comentario
              key={coment.id}
              id={coment.id}
              user={coment.username}
              contenido={coment.contenido}
              fecha={coment.fecha}
              like={coment.likes}
              respuestas={coment.respuestas}
              nivel={0}
              eliminado={coment.eliminado}
            />
          ))
        ) : (
          <h2 className='noCarga'>No hay comentarios</h2>
        )}
      </div>
    </>
  )
}

export default ListaComentarios