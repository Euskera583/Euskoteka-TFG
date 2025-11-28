import React from 'react'
import gifCargando from '../../media/gifCargando.gif'

//componente para mostrar cuando se está gargando algo
const GifCargandoComp = ({ text }) => {
    return (
        <div className='cargandoContenedor'>
            <h1 className="loading">{text}</h1>

            <img
                src={gifCargando}
                alt="Cargando"
                className='gifFoto'
            />
        </div>
    )
}

export default GifCargandoComp;
