import React, { useContext } from 'react';
import { Contexto } from '../../context/Contexto';
import Button from './Button';

const BotonVistaModo = () => {
    const { usuario, modoVistaUsuario, setModoVistaUsuario } = useContext(Contexto);

    if (usuario?.rol !== 'ADMIN') return null;

    //botn que sirve para alternar la vista de un usuario normal y otro admin
    //util para el admin que puede ver como el usuario verá los cambios que está aplicando
    return (
        <div className="botonVistaFlotante">
            <Button
                text={modoVistaUsuario ? "Volver a ADMIN" : "Ver como usuario"}
                func={() => setModoVistaUsuario(prev => !prev)}
                estilo="btnVistaModo"
            />
        </div>
    );
};

export default BotonVistaModo;