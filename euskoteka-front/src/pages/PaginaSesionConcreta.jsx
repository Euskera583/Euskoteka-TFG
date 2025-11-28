import React from 'react'


import SesionConcreta from '../components/Sesiones/SesionConcreta';
import ListaComentarios from '../components/Comentarios/ListaComentarios'

const PaginaSesionConcreta = () => {
  return (
    <>
      <div className='fondoNegro'>
        <SesionConcreta />
        <ListaComentarios />
      </div>
    </>
  )
}

export default PaginaSesionConcreta