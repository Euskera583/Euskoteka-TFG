import React from 'react'

const CardArticulo = ({id, nombre, tipo, foto, func}) => {
  return (
    <div onClick={()=>func(id)} className='card'>
        <div className='bloqueFoto'>
            <img src={foto} alt={nombre} />
        </div>
        <div className='textCardArt'>
            <h3>{nombre}</h3>
            <p>Tipo: {tipo}</p>
        </div>
    </div>
  )
}

export default CardArticulo