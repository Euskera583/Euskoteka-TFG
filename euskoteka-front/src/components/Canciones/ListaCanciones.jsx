import React, { useContext, useEffect, useState, useRef } from 'react';
import { Contexto } from '../../context/Contexto'

import Modal from '../Utilidades/Modal';
import ListaSesiones from '../Sesiones/ListaSesiones';

import Button from '../Utilidades/Button'

import GifCargandoComp from '../Utilidades/GifCargandoComp'

const ListaCanciones = () => {

    const [editandoId, setEditandoId] = useState(null); //ID de la canción que se está editando
    const [form, setForm] = useState({}); //datos temporales de edición
    const [guardando, setGuardando] = useState(false); //estado para saber si se está guardando

    const { fetchCanciones, idSong, setIdSong, modoVistaUsuario, canciones, crearCancion,
        setCanciones, setIsOpen, setModalSesiones, setSesiones, setPaginaSesiones,
        usuario, actualizarCancion, fetchSesionByCancion, setHasMoreModalSesiones,
        paginaCancion, setPaginaCancion, hasMoreCanciones, loadingCanciones, setLoadingCanciones } = useContext(Contexto);

    //carga la lista de canciones
    useEffect(() => {
        const cargar = async () => {
            setLoadingCanciones(true);

            await fetchCanciones(paginaCancion);
            setLoadingCanciones(false);
        };

        cargar();
    }, [paginaCancion]);

    //resetea el valor del id de la canción seleccionada
    useEffect(() => {
        return () => {
            setIdSong(0);
        }
    }, [idSong]);

    //uso de useRef para utilizar el id de la canción sin depender de los re renderizados de react y evitar seteos asincronos
    const idSongRef = useRef(idSong);

    //función que hace un fetch a la lista de seisones pero
    //solo muestra las sesiones que tienen una canción en especifico
    const handleSelSesionTema = async (e) => {
        const idSeleccionado = e.target.value;
        setIdSong(idSeleccionado);
        idSongRef.current = idSeleccionado;

        // RESETEAR TODO ANTES DE ABRIR EL MODAL
        setSesiones([]);
        setPaginaSesiones(0);
        setHasMoreModalSesiones(true);

        setModalSesiones(true);

        await fetchSesionByCancion(idSeleccionado, 0);
        setIsOpen(true);
    };

    //funcion que asigna valores de la canción seleccionada para editar
    const handleEdit = (tema) => {
        setEditandoId(tema.id);
        setForm({ ...tema });
    };

    //cambiar propiedades del formulario de edición
    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    //funcion que guarda la canción editada
    const handleGuardar = async (id) => {
        setGuardando(true);
        const updated = await actualizarCancion(form);
        if (updated) {
            setCanciones(prev =>
                prev.map(c => (c.id === id ? updated : c))
            );
            setEditandoId(null);
        } else {
            alert("❌ Error al actualizar la canción");
        }
        setGuardando(false);
    };

    //funcion para quitar valores al cancelar la edicion
    const handleCancelar = () => {
        setEditandoId(null);
        setForm({});
    };

    //variable que monitorea si se está creando una canción nueva
    const [creando, setCreando] = useState(false);

    const [formCrear, setFormCrear] = useState({
        artista: "",
        nombreTema: "",
        anio: "",
        genero: "",
        urlYt: ""
    });

    //funcion que guarda la nueva cancion
    const handleGuardarNuevaCancion = async () => {
        const nueva = await crearCancion(formCrear);

        if (!nueva) {
            alert("❌ Error al crear la canción");
            return;
        }

        setCanciones(prev => [nueva, ...prev]); // añadir al inicio
        setFormCrear({
            artista: "",
            nombreTema: "",
            anio: "",
            genero: "",
            urlYt: ""
        });
        setCreando(false);
        window.location.reload();
    };

    if (loadingCanciones && canciones.length === 0) {
        return <GifCargandoComp text='Cargando canciones...' />;
    }

    if (!loadingCanciones && canciones.length === 0) {
        return <h1 className="cargandoContenedor">No hay resultados</h1>;
    }

    return (
        <>
            {/* boton para crear nueva canción */}
            {usuario?.rol === "ADMIN" && !modoVistaUsuario && (
                <div className="crearElemenContainer">
                    <Button
                        text="➕ Crear canción nueva"
                        func={() => setCreando(true)}
                        estilo="btnCrear"
                    />
                </div>
            )}
            <div className={`tablaCanciones ${usuario?.rol === "ADMIN" ? "adminSongs" : ""}`}>
                <table>
                    <thead>
                        <tr>
                            {usuario?.rol === "ADMIN" && !modoVistaUsuario && (
                                <th>ID</th>
                            )}
                            <th>Artista o Grupo</th>
                            <th>Nombre</th>
                            <th>Año</th>
                            <th>Género</th>
                            <th>Enlace escucha</th>
                            <th>Sesiones donde suena</th>
                            {usuario?.rol === 'ADMIN' && !modoVistaUsuario && <th>Acciones</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {creando && (
                            <tr className="filaCrear">
                                <td>—</td>
                                <td>
                                    <input
                                        name="artista"
                                        value={formCrear.artista}
                                        onChange={e => setFormCrear(prev => ({ ...prev, artista: e.target.value }))}
                                    />
                                </td>
                                <td>
                                    <input
                                        name="nombreTema"
                                        value={formCrear.nombreTema}
                                        onChange={e => setFormCrear(prev => ({ ...prev, nombreTema: e.target.value }))}
                                    />
                                </td>
                                <td>
                                    <input
                                        name="anio"
                                        type="number"
                                        value={formCrear.anio}
                                        onChange={e => setFormCrear(prev => ({ ...prev, anio: e.target.value }))}
                                    />
                                </td>
                                <td>
                                    <input
                                        name="genero"
                                        value={formCrear.genero}
                                        onChange={e => setFormCrear(prev => ({ ...prev, genero: e.target.value }))}
                                    />
                                </td>
                                <td>
                                    <input
                                        name="urlYt"
                                        value={formCrear.urlYt}
                                        onChange={e => setFormCrear(prev => ({ ...prev, urlYt: e.target.value }))}
                                    />
                                </td>
                                <td>
                                    <Button text="💾 Guardar" func={handleGuardarNuevaCancion} />
                                    <Button text="❌ Cancelar" func={() => setCreando(false)} />
                                </td>
                            </tr>
                        )}
                        {canciones.map((tema) => (
                            <tr key={tema.id} className={editandoId === tema.id ? "editando" : ""}>
                                {editandoId === tema.id ? (
                                    <>
                                        <td>{tema.id}</td>
                                        <td>
                                            <input
                                                name="artista"
                                                value={form.artista || ''}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                name="nombreTema"
                                                value={form.nombreTema || ''}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                name="anio"
                                                type="number"
                                                value={form.anio || ''}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                name="genero"
                                                value={form.genero || ''}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                name="urlYt"
                                                value={form.urlYt || ''}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <Button
                                                text="💾 Guardar"
                                                func={() => handleGuardar(tema.id)}
                                                estilo=""
                                                disabled={guardando}
                                            />
                                            <Button text="❌ Cancelar" func={handleCancelar} estilo="" />
                                        </td>
                                    </>
                                ) : (
                                    <>
                                        {usuario?.rol === "ADMIN" && !modoVistaUsuario && (
                                            <td>ID CANCIÓN: {tema.id}</td>
                                        )}
                                        <td>{tema.artista}</td>
                                        <td>{tema.nombreTema}</td>
                                        <td>{tema.anio}</td>
                                        <td>{tema.genero}</td>
                                        <td>
                                            <a className='urlYT' href={tema.urlYt} target="_blank" rel="noreferrer">Escuchar</a>
                                        </td>
                                        <td>
                                            <Button
                                                text="Ver sesiones con este tema"
                                                func={handleSelSesionTema}
                                                estilo="btnSesSong"
                                                valor={tema.id}
                                            />
                                        </td>
                                        {usuario?.rol === 'ADMIN' && !modoVistaUsuario && (
                                            <td>
                                                <Button text="✏️ Editar" func={() => handleEdit(tema)} estilo="btnEdit" />
                                            </td>
                                        )}
                                    </>
                                )}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            {/* boton para cargar mas en la lista */}
            <div className='contCargarMas'>
                {hasMoreCanciones ? (
                    <Button
                        text="Cargar más"
                        func={() => setPaginaCancion(prev => prev + 1)}
                        estilo="btnCargarMas"
                    />
                ) : (
                    <h2 style={{ color: "white" }}>Fin de la lista</h2>
                )}
            </div>
            <Modal>
                <ListaSesiones idTema={idSongRef.current} />
            </Modal>
        </>
    )
}

export default ListaCanciones