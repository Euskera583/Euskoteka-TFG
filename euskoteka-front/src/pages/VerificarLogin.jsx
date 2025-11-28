import React, { useContext, useEffect } from 'react';
import Modal from '../components/Utilidades/Modal'
import { Contexto } from '../context/Contexto'

const VerificarLogin = () => {

    const { setIsOpen, verificarLogin } = useContext(Contexto);

    useEffect(() => {
        setIsOpen(true);

        //Leer token de la URL
        const params = new URLSearchParams(window.location.search);
        const token = params.get("token");


        const ejecuta = async () => {
            await verificarLogin(token);
        }

        ejecuta();
    }, []);

    return (
        <Modal func='/'>
            <div className='modalVerifica'>
                <h1>Inicio de sesión exitoso</h1>
                <p>A partir de este momento podras comentar, dar likes y subir imagenes en la web</p>
                <p>No es necesario volver a iniciar sesión al salir y entrar de la web, está durará un mes. Pasado ese tiempo deberas volver a iniciar sesión</p>
            </div>
        </Modal>
    )
}

export default VerificarLogin