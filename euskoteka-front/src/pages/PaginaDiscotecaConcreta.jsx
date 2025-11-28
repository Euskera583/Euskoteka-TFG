import React from 'react'
import DiscotecaConcreta from '../components/Discotecas/DiscotecaConcreta'
import ListaComentarios from '../components/Comentarios/ListaComentarios'

const PaginaDiscotecaConcreta = () => {
    return (
        <>
            <div className='fondoNegro'>
            <DiscotecaConcreta />
            <ListaComentarios />
        </div>
        </>
    )
}

export default PaginaDiscotecaConcreta