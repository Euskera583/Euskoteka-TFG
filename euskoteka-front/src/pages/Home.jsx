import React from 'react'

import logoCompleto from '../media/logoCompleto.png'
import gifCargando from '../media/gifCargando.gif'

const Home = () => {

  const introTexto = `En los años 90, las noches de Euskal Herria tenían su propia magia. Euskoteka nace para preservar la memoria de aquellas discotecas que marcaron toda una generación.
Revive las sesiones más memorables, descubre los temazos que hicieron vibrar a la gente y recuerda los lugares que todos amamos.`;

  return (
    <div className='pagHome'>
      <img src={logoCompleto} alt="Euskoteka logo" className='logoCompleto' />
      <div className='contenedorHomeTexto'>
        <h1>¡Bienvenido a Euskoteka!</h1>
        {introTexto.split("\n").map((p, i) => (
          <p key={i}>{p}</p>
        ))}
      </div>
    </div>
  )
}

export default Home