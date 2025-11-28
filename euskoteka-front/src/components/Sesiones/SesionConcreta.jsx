import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router";
import { Contexto } from "../../context/Contexto";

import Button from '../Utilidades/Button'
import GifCargandoComp from '../Utilidades/GifCargandoComp';

const SesionConcreta = () => {

  //se coge el id de la sesión desde el parametro de la url
  const { idSes } = useParams();

  const { sesionesConcreta, setSesionConcreta, fetchTracklistSesion, usuario, fetchSesionById, actualizarSesion,
    actualizarDjsSesion, modoVistaUsuario, setTipoObjetivo, setIdObjetivo } = useContext(Contexto);

  const [form, setForm] = useState(null);
  const [guardando, setGuardando] = useState(false);

  const [trackkk, setTrackkk] = useState([]);

  //cuando el valor de idSes cambia, se hace fetch para traer la info de la sesión concreta y su tracklist si lo hay
  useEffect(() => {
    setSesionConcreta(null);

    const fetchData = async () => {
      const cintaConcreta = await fetchSesionById(idSes);
      const trackSesion = await fetchTracklistSesion(idSes);

      setTrackkk(trackSesion);

      setTipoObjetivo("Sesion");
      setIdObjetivo(cintaConcreta.id);

      setSesionConcreta(cintaConcreta);
      setForm(cintaConcreta);
    };

    fetchData();
  }, [idSes]);

  if (!sesionesConcreta) return <GifCargandoComp text="Cargando sesiones..." />;

  //manejador de cambios para los inputs en modo admin
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleGuardar = async () => {
    setGuardando(true);
    const res = await actualizarSesion(form);
    if (res) {
      alert("✅ Sesión actualizada correctamente");
      setSesionConcreta(res);
    } else {
      alert("❌ Error al guardar los cambios");
    }
    setGuardando(false);
  };

  const handleGuardarDjs = async () => {
    setGuardando(true);
    const res = await actualizarDjsSesion(form.id, form.djs);
    if (res) {
      alert("✅ DJs actualizados correctamente");
      setSesionConcreta(res);
      setForm(res);
    } else {
      alert("❌ Error al actualizar DJs");
    }
    setGuardando(false);
  }

  //modo admin editable
  if (usuario?.rol === 'ADMIN' && !modoVistaUsuario) {
    return (
      <div className="adminPanel">
        <div className="adminPanel-group">
          <h2>Modo administrador: editar sesión</h2>
          <label>
            Nombre:
            <input
              name="nombreSesion"
              value={form?.nombreSesion || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Fecha:
            <input
              name="fechaSesion"
              value={form?.fechaSesion || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Medio:
            <input
              name="medioSesion"
              value={form?.medioSesion || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Conservación:
            <input
              name="conservacionSesion"
              value={form?.conservacionSesion || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Dueño del medio:
            <input
              name="duennoMedio"
              value={form?.duennoMedio || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Información:
            <textarea
              name="infoSesion"
              value={form?.infoSesion || ""}
              onChange={handleChange}
              style={{ height: "70px" }}
            />
          </label>

          <label>
            URL Youtube:
            <input
              name="urlSesion"
              value={form?.urlSesion || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Carátula:
            <input
              name="caratulaFoto"
              value={form?.caratulaFoto || ""}
              onChange={handleChange}
            />
          </label>

          <label>
            Carátula (HD):
            <input
              name="caratulaFotoCalidad"
              value={form?.caratulaFotoCalidad || ""}
              onChange={handleChange}
            />
          </label>

          <Button
            text={guardando ? "Guardando..." : "💾 Guardar cambios"}
            func={handleGuardar}
            estilo={{ marginTop: "1rem" }}
          />

          <div style={{ marginTop: "1.5rem" }}>
            <h3>Disc-Jockey/s:</h3>
            {form?.djs?.map((dj, i) => (
              <input
                key={i}
                className="inputDJ"
                value={dj}
                onChange={(e) => {
                  const nuevos = [...form.djs];
                  nuevos[i] = e.target.value;
                  setForm((prev) => ({ ...prev, djs: nuevos }));
                }}
                style={{ display: "block", marginBottom: "0.5rem" }}
              />
            ))}

            <Button
              text='+ Añadir DJ'
              func={() => setForm((prev) => ({
                ...prev,
                djs: [...(prev.djs || []), ""],
              }))}
              estilo='botonAddDJ'
            />

            <Button
              text={guardando ? "Guardando..." : "💾 Guardar DJs"}
              func={handleGuardarDjs}
            />

          </div>
        </div>
      </div>
    );
  }

  //funcion para extraer el identificador real de la url de YT y normalizar el enlace
  const extraeIdYoutube = (url) => {
    if (!url) return "";
    const regExp = /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
    const match = url.match(regExp);
    return match && match[2].length === 11 ? match[2] : "";
  };


  //modo usuario normal
  return (
    <div className="fondoSesionConcreta">
      <div className="bloqueConcretoTextoSesion">
        <h1 id="h1NombreElemento">{sesionesConcreta.nombreSesion}</h1>
        <br />
        <h3>Fecha: {sesionesConcreta.fechaSesion}</h3>
        <br />
        <h3>
          Disc-Jockey/s:
          {/* mostrar todos los DJs separados por guion, y evitar que esté al final de la cadena */}
          {Array.isArray(sesionesConcreta?.djs) &&
            sesionesConcreta.djs.map((dj, index) => (
              <span key={index}>
                {" "}
                {dj}
                {index < sesionesConcreta.djs.length - 1 ? " - " : ""}
              </span>
            ))}
        </h3>
        <br />
        <h3>Medio: {sesionesConcreta.medioSesion}</h3>
        <br />
        <h3>
          Estado de conservación:{" "}
          {sesionesConcreta.conservacionSesion || "Desconocido"}
        </h3>
        <br />
        <br />
        <h2>Sobre esta sesión:</h2>
        <br />
        <div className="infoElementoConcreto">
          <h2>{sesionesConcreta.infoSesion}</h2>
          <br />
          <h4>
            {sesionesConcreta.duennoMedio
              ? "Sesión subida a youtube por " + sesionesConcreta.duennoMedio
              : ""}
          </h4>
        </div>
        <br />
        <br />
        <ul className="trackList">
          {trackkk.length > 0 ? (
            trackkk.map((tema) => (
              <li key={tema.numeroPista}>
                {String(tema.numeroPista).padStart(2, "0")}. {tema.artista} -{" "}
                {tema.nombreTema}
              </li>
            ))
          ) : (
            <h4>Tracklist no disponible</h4>
          )}
        </ul>
        <br />
      </div>

      <div className="bloqueConcretoFotoSesion">
        <img
          src={sesionesConcreta.caratulaFotoCalidad}
          alt={sesionesConcreta.nombreSesion}
        />

        {sesionesConcreta.urlSesion ? (
          <div className="videoWrapper">
            <iframe
              width="100%"
              height="180"
              src={`https://www.youtube.com/embed/${extraeIdYoutube(sesionesConcreta.urlSesion)}`}
              title={sesionesConcreta.nombreSesion}
              frameBorder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            ></iframe>
            <a href={sesionesConcreta.urlSesion} target="_blank" rel="noopener noreferrer">
              <span>Ver en YouTube</span>
            </a>
          </div>
        ) : (
          <h3>Enlace de escucha no disponible</h3>
        )}
      </div>
    </div>
  );
};

export default SesionConcreta;