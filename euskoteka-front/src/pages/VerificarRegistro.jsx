import React, { useContext, useEffect } from 'react';
import Modal from '../components/Utilidades/Modal'
import { Contexto } from '../context/Contexto'

const VerificarRegistro = () => {

  const { setIsOpen, verificarRegistro } = useContext(Contexto);

  useEffect(() => {
    setIsOpen(true)

    //Leer la token de la url
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    const ejecuta = async () => {
      await verificarRegistro(token);
    }

    ejecuta();

  }, []);

  return (
    <Modal func='/'>
      <div className='modalVerifica'>
        <h1>¡Tu cuenta ha sido creada y verificada con éxito!</h1>
        <p>¡Disfruta de tu estancia en Euskoteka 😊!.</p>
        <p>No es necesario volver a iniciar sesión al salir y entrar de la web, esta durará un mes. Pasado ese tiempo deberas volver a iniciar sesión</p>
      </div>
    </Modal>
  )
}

export default VerificarRegistro