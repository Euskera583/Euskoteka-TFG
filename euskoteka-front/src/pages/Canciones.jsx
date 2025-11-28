import React from 'react'

import ListaCanciones from '../components/Canciones/ListaCanciones'
import FiltrosCanciones from '../components/Canciones/FiltrosCanciones'

const Canciones = () => {
    return (
        <>
            <div className='fondoNegro'>
                <FiltrosCanciones />
                <ListaCanciones />
            </div>
        </>
    )
}

export default Canciones