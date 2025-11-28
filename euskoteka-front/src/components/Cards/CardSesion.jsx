import React from 'react'

const CardSesion = ({ id, func, nombre, caratula, fecha, DJ }) => {
    return (
        <div onClick={()=>func(id)} className='card'>
            <div className='fotoListadoSesion'>
                <img src={caratula} alt={nombre} />
            </div>

            <div className='textoListadoSesion'>
                <h3>{nombre}</h3>
                <h4>Fecha: {fecha}</h4>
                <div>{DJ.length > 0 ? (
                    DJ.map((disc, index) => (
                        <p key={index}>{disc}</p>
                    ))
                ) : (
                    <p>DJ Desconocido</p>
                )}</div>
            </div>
        </div>
    )
}

export default CardSesion