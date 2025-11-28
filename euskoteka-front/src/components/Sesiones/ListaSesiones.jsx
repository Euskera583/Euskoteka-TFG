import React, { useContext, useEffect, useState, useRef } from 'react';
import { useNavigate } from 'react-router';
import { Contexto } from '../../context/Contexto';
import CardSesion from '../Cards/CardSesion';
import Button from '../Utilidades/Button'
import GifCargandoComp from '../Utilidades/GifCargandoComp';

const ListaSesiones = ({ idTema }) => {

    const lastIdTema = useRef(idTema);

    const { fetchSesiones, sesiones, setSesiones, paginaSesiones, setPaginaSesiones, fetchSesionByCancion,
        setIdSong, filtros, hasMoreSesiones, hasMoreModalSesiones, setHasMoreModalSesiones, modalPage, setModalPage,
        loadingSesiones, setLoadingSesiones, crearSesion, usuario, actualizarDjsSesion, modoVistaUsuario } = useContext(Contexto);

    //cada vez que idTema cambia, actualizamos el ref
    const paginaRef = useRef(paginaSesiones);

    const nav = useNavigate();

    //si cambia algun filtro, se vuelve a hacer fetch con los nuevos filtros
    useEffect(() => {
        //solo se actua si no esta en modo modal
        if (idTema) return;

        const recargar = async () => {


            setLoadingSesiones(true)
            setSesiones([]);

            setPaginaSesiones(0);
            await fetchSesiones();
            setLoadingSesiones(false)
        };
        recargar();
    }, [filtros.anios.join(','), filtros.djs.join(','), filtros.discotecas.join(',')]);

    //cada vez que cambia idTema al pulsar el boton de ver sesiones con cancion se hace
    //fetch para traer todas las sesiones donde suena una canción en específico
    useEffect(() => {
        if (!idTema) return;

        const cargar = async () => {
            setSesiones([]);
            setModalPage(0);
            setHasMoreModalSesiones(true);

            await fetchSesionByCancion(idTema, 0);
        }

        cargar();

    }, [idTema]);

    //selecciona una sesion para verla en detalle
    const handleSelSesion = async (id) => {
        nav(`/sesion/${id}`)
    }

    //limpiar al desmontar
    useEffect(() => {
        return () => {
            setSesiones([]);
            setPaginaSesiones(0);
            setModalPage(0);
            setIdSong(0);
        };
    }, [setSesiones, setPaginaSesiones, setIdSong]);

    //cada vez que idTema tambien, se asigna el valor a la variable ocn useRef para asegurarnos que se usa el valor mas reciente
    //sin depender del siguiente renderizado
    useEffect(() => {
        lastIdTema.current = idTema;
    }, [idTema]);

    //funcion que se ejecuta cada vez que se le da al boton de cargar más
    //depende de si es es en modao global o modal se hace de una forma u otra
    const handleCargarMas = async () => {
        const temaActual = lastIdTema.current;

        if (!temaActual) {
            // modo global
            const nuevaPagina = paginaRef.current + 1;
            paginaRef.current = nuevaPagina; //actualizar ref
            setPaginaSesiones(nuevaPagina); //actualizar estado para render
            await fetchSesiones(nuevaPagina);
        } else {
            // modo modal
            const nuevaPagina = modalPage + 1;
            setModalPage(nuevaPagina);
            await fetchSesionByCancion(temaActual, nuevaPagina);
        }
    }

    //variable que mantiene el estado cuando se está creando una sesión
    const [creando, setCreando] = useState(false);

    //aray que almacena los DJs de la nueva sesión
    const [djs, setDjs] = useState([""]);

    const [form, setForm] = useState({
        nombreSesion: "",
        infoSesion: "",
        fechaSesion: "",
        caratulaFoto: "",
        caratulaFotoCalidad: "",
        urlSesion: "",
        conservacionSesion: "",
        medioSesion: "",
        duennoMedio: "",
        nombreDiscoteca: ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    //funcion para guardar la nueva sesión
    const handleGuardarNueva = async () => {
        const nueva = await crearSesion(form);

        if (nueva) {
            alert("✅ Sesión creada correctamente");

            setCreando(false);

            setForm({
                nombreSesion: "",
                infoSesion: "",
                fechaSesion: "",
                caratulaFoto: "",
                caratulaFotoCalidad: "",
                urlSesion: "",
                conservacionSesion: "",
                medioSesion: "",
                duennoMedio: "",
                nombreDiscoteca: ""
            });
            console.log(nueva);

            await actualizarDjsSesion(nueva.id, djs);
            window.location.reload();
        } else {
            alert("❌ Error al crear la sesión");
        }
    };

    if (loadingSesiones && sesiones.length === 0) {
        return <GifCargandoComp text="Cargando sesiones..." />;
    }

    return (
        <>
            {usuario?.rol === "ADMIN" && !idTema && !modoVistaUsuario && (
                <div className="crearElemenContainer">
                    <Button
                        text="➕ Crear sesión nueva"
                        func={() => setCreando(true)}
                        estilo="btnCrear"
                    />
                </div>
            )}

            {/* panel de creación de sesiones */}
            {creando && (
                <div className="panel-crear-disco">
                    <h2>Crear nueva sesión</h2>

                    <div className="panelCampos">
                        <label>Nombre sesión:</label>
                        <input name="nombreSesion" value={form.nombreSesion} onChange={handleChange} />

                        <label>Info sesión:</label>
                        <textarea name="infoSesion" value={form.infoSesion} onChange={handleChange} />

                        <label>Fecha (dd/mm/yyyy o yyyy):</label>
                        <input name="fechaSesion" value={form.fechaSesion} onChange={handleChange} />

                        <label>Carátula (URL):</label>
                        <input name="caratulaFoto" value={form.caratulaFoto} onChange={handleChange} />

                        <label>Carátula HD (URL):</label>
                        <input name="caratulaFotoCalidad" value={form.caratulaFotoCalidad} onChange={handleChange} />

                        <label>URL sesión:</label>
                        <input name="urlSesion" value={form.urlSesion} onChange={handleChange} />

                        <label>Conservación:</label>
                        <input name="conservacionSesion" value={form.conservacionSesion} onChange={handleChange} />

                        <label>Medio:</label>
                        <input name="medioSesion" value={form.medioSesion} onChange={handleChange} />

                        <label>Dueño del Medio:</label>
                        <input name="duennoMedio" value={form.duennoMedio} onChange={handleChange} />

                        <label>Discoteca (nombre EXACTO):</label>
                        <input
                            name="nombreDiscoteca"
                            value={form.nombreDiscoteca}
                            onChange={handleChange}
                            placeholder="Ej: Txitxarro"
                        />
                    </div>

                    <div className="bloqueDjs">
                        <h3>Disc-Jockey/s:</h3>

                        {djs.map((dj, i) => (
                            <div key={i} style={{ display: "flex", marginBottom: "0.5rem" }}>
                                <input
                                    className="inputDJ"
                                    value={dj}
                                    placeholder="Nombre del DJ"
                                    onChange={(e) => {
                                        const nuevos = [...djs];
                                        nuevos[i] = e.target.value;
                                        setDjs(nuevos);
                                    }}
                                    style={{ flex: 1, marginRight: "0.5rem" }}
                                />

                                {/* boton para borrar DJs */}
                                {djs.length > 1 && (
                                    <Button
                                        text="❌"
                                        func={() => {
                                            setDjs(prev => prev.filter((_, idx) => idx !== i));
                                        }}
                                    />
                                )}
                            </div>
                        ))}

                        {/* añadir DJ */}
                        <Button
                            text="+ Añadir DJ"
                            func={() => setDjs(prev => [...prev, ""])}
                            estilo="botonAddDJ"
                        />
                    </div>

                    <div className="panelBotones">
                        <Button text="💾 Guardar" func={handleGuardarNueva} />
                        <Button text="❌ Cancelar" func={() => setCreando(false)} />
                    </div>
                </div>
            )}
            <h4 style={{
                textAlign: "center",
                color: (idTema ? "black" : "white"),
                margin: "0.3em"
            }}>
                Nota: Algunas carátulas han sido reconstruidas utilizando versiones previas como referencia con el fin de preservar la calidad de imagen.
            </h4>

            <div className='ConjuntoTarjetas'>
                {sesiones.length > 0 ? (
                    sesiones.map((sesion) => (
                        <CardSesion
                            key={sesion.id}
                            id={sesion.id}
                            func={handleSelSesion}
                            nombre={sesion.nombreSesion}
                            caratula={sesion.caratulaFoto}
                            fecha={sesion.fechaSesion}
                            DJ={sesion.djs}
                        />

                    ))
                ) : (
                    <h1 style={{ color: (idTema ? "black" : "white") }}>No se ha podido cargar</h1>
                )}
            </div>

            <div className='contCargarMas'>

                {/* modo global */}
                {!idTema && hasMoreSesiones && (
                    <Button
                        text="Cargar más"
                        func={handleCargarMas}
                        estilo="btnCargarMas"
                    />
                )}

                {/* modo modal */}
                {idTema && hasMoreModalSesiones && (
                    <Button
                        text="Cargar más"
                        func={handleCargarMas}
                        estilo="btnCargarMas"
                    />
                )}

                {/* fin de lista global */}
                {!idTema && !hasMoreSesiones && sesiones.length > 0 && (
                    <h1 style={{ color: 'white' }}>Fin de la lista</h1>
                )}

                {/* fin de lista modal */}
                {idTema && !hasMoreModalSesiones && sesiones.length > 0 && (
                    <h1 style={{ color: 'black' }}>Fin de la lista</h1>
                )}

            </div>
        </>
    )
}


export default ListaSesiones;
