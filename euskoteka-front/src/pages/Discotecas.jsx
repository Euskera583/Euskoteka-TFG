import React from 'react'
import ListaDiscotecas from '../components/Discotecas/ListaDiscotecas'
import FiltrosDiscotecas from '../components/Discotecas/FiltrosDiscotecas'

const Discotecas = () => {
    return (
        <div className='fondoNegro'>
            <FiltrosDiscotecas />
            <ListaDiscotecas />
        </div>
    )
}

export default Discotecas