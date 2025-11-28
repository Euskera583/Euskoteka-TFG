import React from 'react'

const CardDiscoteca = ({id, func, nombre, foto, localidad, periodo}) => {
    return (
        <div onClick={()=>func(id)} className='card'>
            <div className='bloqueFoto'>
                <img src={foto} alt={nombre} />
            </div>
            <div className='bloqueTexto'>
                <h3>{nombre}</h3>
                <h4>Localidad: {localidad}</h4>
                
                <h5><br/>Periodo de Actividad: {periodo}</h5>
            </div>
        </div>
    )
}

export default CardDiscoteca