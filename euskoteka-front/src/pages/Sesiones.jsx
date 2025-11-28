import React from 'react'
import ListaSesiones from '../components/Sesiones/ListaSesiones'
import FiltrosSesiones from '../components/Sesiones/FiltrosSesiones'

const Sesiones = () => {
    return (
        <>
            <div className='fondoNegro'>
                <FiltrosSesiones />
                <ListaSesiones />
            </div>
        </>
    )
}

export default Sesiones