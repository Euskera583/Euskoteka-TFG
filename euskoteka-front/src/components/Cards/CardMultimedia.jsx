import React, { useState } from 'react';
import Modal from '../Utilidades/Modal';

const CardMultimedia = ({ discoteca, descripcion, anio, url, urlFotoCalidad }) => {
  const [open, setOpen] = useState(false);

  return (
    <>
      <div className='card' onClick={() => setOpen(true)}>
        <img className='imagenMultimedia' src={url} alt={descripcion} />
      </div>

      <Modal local open={open} setOpen={setOpen}>
        <div className="cardMultimedia">
          <h3>Discoteca: {discoteca}</h3>
          <h3>Año: {anio}</h3>
          <p>{descripcion}</p>
          <img src={urlFotoCalidad} alt={descripcion} />
        </div>
      </Modal>
    </>
  )
}

export default CardMultimedia;
