import React from 'react'
import ArticuloConcreto from '../components/Articulos/ArticuloConcreto'
import ListaComentarios from '../components/Comentarios/ListaComentarios'

const PagArticuloConcreto = () => {
  return (
    <div className='fondoNegro'>
      <ArticuloConcreto />
      <ListaComentarios />
    </div>
  )
}

export default PagArticuloConcreto