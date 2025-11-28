import React, { useContext, useState, useEffect } from 'react';
import { Contexto } from '../../context/Contexto';
import { useNavigate } from 'react-router';
import Button from './Button';

const Modal = ({ children, text, local = false, open: controlledOpen,
  setOpen: controlledSetOpen, func }) => {
  const { isOpen, setIsOpen } = useContext(Contexto);
  const [localOpen, setLocalOpen] = useState(false);

  // Prioridad de control:
  // 1. Props controladas
  // 2. Local 
  // 3. Global
  const open = controlledOpen ?? (local ? localOpen : isOpen);
  const setOpen = controlledSetOpen ?? (local ? setLocalOpen : setIsOpen);
  const nav = useNavigate();

  //bloquaer el scroll ddel body cuando el modal esté abierto
  useEffect(() => {
    if (open) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'auto';
    }

    //al desmontar, siempre restaurar
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, [open]);

  const opcionCerrar = () => {
    if (func) {
      //si se le pasa un parametro de cierre
      //el modal se cierra pero navega a otra pagina
      setOpen(false)
      console.log(func);

      nav('/');
    } else {
      setOpen(false)
    }
  }

  return (
    <>
      {text && (
        <div className="btnFiltrosFlotante">
          <Button
            text={text}
            func={() => setOpen(true)}
            estilo='btnVistaModo'
          />
        </div>
      )}

      <div className={open ? 'modalContainerOpen' : 'modalContainerClosed'}>
        <div className="modalContent">
          {children}
          <Button
            text='Cerrar'
            func={opcionCerrar}
            estilo='btnCierraModal'
          />
        </div>
      </div>
    </>
  );
};

export default Modal;