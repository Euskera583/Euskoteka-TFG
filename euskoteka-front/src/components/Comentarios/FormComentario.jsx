import React, { useState, useContext } from 'react';
import { Contexto } from '../../context/Contexto';

const FormComentario = () => {
  const { crearComentario, isLoged } = useContext(Contexto);
  const [contenido, setContenido] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (contenido.trim().length < 2) return alert("El comentario es demasiado corto.");

    const ok = await crearComentario(contenido);
    if (ok) setContenido('');
  };

  //si no está iniciada la seisón no se puede comentar
  if (!isLoged) {
    return <p className="noCarga">Inicia sesión para comentar.</p>;
  }

  return (
    <form onSubmit={handleSubmit} className="formComentario">
      <textarea
        value={contenido}
        onChange={(e) => setContenido(e.target.value)}
        placeholder="Escribe un comentario..."
      />
      <button type="submit">Comentar</button>
    </form>
  );
};

export default FormComentario;