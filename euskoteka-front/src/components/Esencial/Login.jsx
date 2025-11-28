import React, { useContext, useEffect } from 'react';

import { Contexto } from '../../context/Contexto'
import Button from '../Utilidades/Button'
import Form from '../Utilidades/Form';

const Login = () => {
  const { openLogin, setOpenLogin, datosFormLogin, setDatosFormLogin, loginUsuario,
    datosFormRegistro, setDatosFormRegistro, registrarUsuario } = useContext(Contexto);

  const handleLogBtb = () => {
    setOpenLogin(true)
  }

    //funciona para confirmar el inicio de sesión
  const handleLogin = async (e) => {
    e.preventDefault();
    await loginUsuario();
  }

  //funciona para confirmar el registro de usuario
  const handleRegister = async (e) => {
    e.preventDefault();
    await registrarUsuario();
  }

  return (
    <>
      <div className='btnHeader'>
        <Button
          text="Identifícate"
          func={handleLogBtb}
          estilo='btnHeader'
        />
      </div>
      <div className={openLogin ? 'modalContainerOpen' : 'modalContainerClosed'}>
        <div className="modalContent">
          <div className='contenedorForm'>
            <div className='contLogin'>
              {/* dos contenedores, uno para login y otro para registro re usando componentes */}
              <h1>Iniciar sesión</h1>
              <Form
                func={handleLogin}
                formData={datosFormLogin}
                setFormData={setDatosFormLogin}
                textBtn='Iniciar sesión'
              />
            </div>
            <div className='contRegistro'>
              <h1>Crear cuenta</h1>
              <Form
                func={handleRegister}
                formData={datosFormRegistro}
                setFormData={setDatosFormRegistro}
                textBtn='Crear cuenta'
              />
            </div>
          </div>
          <Button
            text='Cerrar'
            func={() => setOpenLogin(false)}
            estilo='btnCierraModal'
          />
        </div>
      </div>
    </>
  )
}

export default Login