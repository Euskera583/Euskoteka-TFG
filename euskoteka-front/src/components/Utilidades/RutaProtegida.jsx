import React, { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router';
import { Contexto } from '../../context/Contexto';
import GifCargandoComp from './GifCargandoComp';

const RutaProtegida = ({ children }) => {
  const { isLoged, isAuthChecked } = useContext(Contexto);
  const nav = useNavigate();

  //este componente protege otros para evitar que se pueda acceder si no se cumple la condición
  //en este caso solo se puede entrar si está iniciada la sesión
  useEffect(() => {
    if (isAuthChecked && !isLoged) { //solo cuando ya se haya comprobado
      alert("Inicia sesión para ver esto");
      nav("/");
    }
  }, [isLoged, isAuthChecked, nav]);

  if (!isAuthChecked) {
    return <GifCargandoComp text="Comprobado sesión..." />;
  }

  return isLoged ? children : null;
};

export default RutaProtegida;