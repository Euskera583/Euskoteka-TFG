import React, { createContext, useState, useEffect } from "react";

export const Contexto = createContext();
export const ContextoProvider = ({ children }) => {

    useEffect(() => {
        autoLogin();
    }, []);

    const [sesiones, setSesiones] = useState([]); //array de objetos de todas las sesiones
    const [sesionesConcreta, setSesionConcreta] = useState({}); //objeto de una sesion concreta

    const [discotecas, setDiscotecas] = useState([]); //array de objetos de todas las discotecas
    const [discoConcreta, setDiscoConcreta] = useState({}); //objeto de una discoteca concreta

    const [articulos, setArticulos] = useState([]); //array de objetos de todos los articulos
    const [artConcreto, setArtConcreto] = useState([]); //objeto de un articulo concreto

    const [canciones, setCanciones] = useState([]); //array de objetos de todas las canciones

    const [multimedia, setMultimedia] = useState([]); //array de objetos de todo el material multimedia

    const [comentarios, setComentarios] = useState([]); //array de objetos con todos los comentarios cargados

    const [pagina, setPagina] = useState(0); //numero de pagina del paginado

    const [idSong, setIdSong] = useState(0); //id de la canción seleccionada para ver las sesiones segun una canción concreta

    const [paginaSesiones, setPaginaSesiones] = useState(0); //numero de página de sesiones

    const [modalSesiones, setModalSesiones] = useState(false); //booleano para mostrar una cosa u otra en lista

    const [tipoObjetivo, setTipoObjetivo] = useState(""); //variable string que establece el tipo de objetivo a consultar para traer los comentarios
    const [idObjetivo, setIdObjetivo] = useState(0); //identificador de sesiones, discotecas u otro para traer loscomentarios de un tipo concreto con un id concreto

    const [residencias, setResidencias] = useState([]); // array de objetos con todos los DJs y sus residencias en discotecas

    const [paginaCancion, setPaginaCancion] = useState(0); //numero de página para canciones

    const [filtros, setFiltros] = useState({ //objeto cons losfiltros de sesion
        anios: [],
        djs: [],
        discotecas: []
    });

    const [anioArray, setAnioArray] = useState([]); //array con todos los años donde hay al menos una sesión (para el selector de años en los filtros de sesiones)
    const [djArray, setDjArray] = useState([]); //array con todos los DJs donde hay al menos una sesión (para el selector de años en los filtros filtros de sesiones)
    const [discotecaArray, setDiscotecaArray] = useState([]); //array con todos los DJs donde hay al menos una sesión (para el selector de años en los filtros filtros de sesiones)

    const [filtrosCanciones, setFiltrosCanciones] = useState({ //objeto cons losfiltros de canciones
        anios: [],
        generos: [],
        buscar: ''
    });

    const [anioArrayCancion, setAnioArrayCancion] = useState([]); //array con todos los años donde hay al menos una cancion (para el selector de años en los filtros de canciones)
    const [generoArrayCancion, setGeneroArrayCancion] = useState([]); //array con todos los generos donde hay al menos una cancion (para el selector de años en los filtros) de canciones

    const [filtrosDiscotecas, setFiltrosDiscotecas] = useState({ //objeto cons losfiltros de discotecas
        anios: [],
        provincias: [],
        localidades: []
    });

    const [aniosDiscotecasDisponibles, setAniosDiscotecasDisponibles] = useState([]); //array con todos los años donde hay al menos una discoteca (para el selector de años en filtros de discotecas)
    const [provinciasArray, setProvinciasArray] = useState([]); //array con todas los provincias donde hay al menos una discoteca (para el selector de provincias en filtros de discotecas)
    const [localidadesPorProvincia, setLocalidadesPorProvincia] = useState({}); //no implementado

    const [isOpen, setIsOpen] = useState(false); //manejo de apertura y cierre para el modal

    const [openLogin, setOpenLogin] = useState(false); //variable para manejar la apertura del modal de login

    const [isLoged, setIsLoged] = useState(false); //variable que comprueba si la sesión está iniciaca
    const [usuario, setUsuario] = useState(null); //datos del usuario logeado

    const [isAuthChecked, setIsAuthChecked] = useState(false); //check para confirmar el login


    const [datosFormLogin, setDatosFormLogin] = useState({ //formulario para el inicio de sesión
        username: "",
        email: ""
    });

    const [datosFormRegistro, setDatosFormRegistro] = useState({ //formulario para el registro
        username: "",
        email: ""
    });

    const [modoVistaUsuario, setModoVistaUsuario] = useState(false); //cambiar entre vista de admin y user cuando el usuari oes un admin

    const [hasMoreSesiones, setHasMoreSesiones] = useState(true); //para parar de cargar la lista paginada de sesiones

    const [hasMoreModalSesiones, setHasMoreModalSesiones] = useState(true); //para parar de cargar la lista paginada de sesiones dentro del modal

    const [hasMoreCanciones, setHasMoreCanciones] = useState(true); //para parar de cargar la lista paginada de canciones

    const [hasMoreDiscotecas, setHasMoreDiscotecas] = useState(true); //para parar de cargar la lista paginada de discotecas

    const [hasMoreArticulos, setHasMoreArticulos] = useState(true); //para parar de cargar la lista paginada de articulos

    const [hasMoreMultimedia, setHasMoreMultimedia] = useState(true); //para parar de cargar la lista paginada de material multimedia

    const [modalPage, setModalPage] = useState(0); //numero de página para las sesiones paginadas dentro del modal

    const [loadingCanciones, setLoadingCanciones] = useState(false); //variable que gestiona el estado de carga de las páginas para mostrar el componente de carga

    const [loadingSesiones, setLoadingSesiones] = useState(false);

    const [loadingDiscotecas, setLoadingDiscotecas] = useState(false);

    const [loadingArticulos, setLoadingArticulos] = useState(false);


    //METODOS DE SEISONES

    //metodo que devuelve la lista paginada de sesiones, con o sin filtros
    const fetchSesiones = async (pagina = 0, filtrosOverride = null) => {
        try {
            const filtrosActuales = filtrosOverride || filtros;

            //se crean los parametros para la url que se envía a la API
            const params = new URLSearchParams();
            params.append("page", pagina);
            params.append("size", 30);

            //se meten los filtros en los parametros
            filtrosActuales.anios.forEach(a => params.append("anios", a));
            filtrosActuales.djs.forEach(dj => params.append("djs", dj));
            filtrosActuales.discotecas.forEach(d => params.append("discotecas", d));

            params.append("sort", "fechaSesionYear,asc");
            params.append("sort", "fechaSesionMonth,asc");
            params.append("sort", "fechaSesionDay,asc");

            const url = `https://eukoteka-api.onrender.com/api/sesiones?${params.toString()}`;
            const response = await fetch(url);
            const data = await response.json();

            if (response.status === 200) {
                setHasMoreSesiones(!data.last);

                /* Si data.last = false (no es la última página)
                    !false = true -> sí hay más.
    
                    Si data.last = true (es la última página)
                    !true = false -> no hay más, esconder botón. */
                if (pagina === 0) {
                    setSesiones(data.content); // reemplaza
                } else {
                    setSesiones(prev => [...prev, ...data.content]); // concatena
                }
            } else {
                console.error("Error al cargar sesiones");
            }
        } catch (error) {
            console.log('Error al cargar las sesiones: ', error);
        }
    };

    //metodo que devuelve la información detallada de una sesión por su id
    const fetchSesionById = async (id) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/sesiones/${id}`);
            const data = await response.json();

            if (response.ok) {
                return data[0];
            } else {
                console.log(`Error al cargar la sesión con el id: ${id}`);
            }
        } catch (error) {
            console.log(`Error al cargar la sesión con el id ${id}: `, error);
        }
    }

    //metodo que devuelve una lista paginada segun el id de una canción especifica
    const fetchSesionByCancion = async (id, page = 0) => {
        try {
            const params = new URLSearchParams();
            params.append("page", page);
            params.append("size", 20);
            params.append("sort", "fechaSesionYear,asc");
            params.append("sort", "fechaSesionMonth,asc");
            params.append("sort", "fechaSesionDay,asc");

            const url = `https://eukoteka-api.onrender.com/api/canciones/${id}/sesiones?${params.toString()}`;
            const response = await fetch(url);
            const data = await response.json();

            if (response.ok) {
                setHasMoreModalSesiones(!data.last);
                if (page === 0) {
                    setSesiones(data.content);
                } else {
                    setSesiones(prev => {
                        //evitar duplicados simples por id
                        const idsPrev = new Set(prev.map(s => s.id));
                        const nuevos = data.content.filter(s => !idsPrev.has(s.id));
                        return [...prev, ...nuevos];
                    });
                }
            }

        } catch (error) {
            console.error("Error al cargar las sesiones: ", error);
        }
    };

    //metodo que devuelkve los filtros disponibles de las sesiones
    const fetchFiltrosDisponibles = async () => {
        try {
            let url = 'https://eukoteka-api.onrender.com/api/sesiones/filtros';

            //si ya hay filtros seleccionados, se pasa como query params
            const params = new URLSearchParams();
            if (filtros.anios.length > 0) params.append('anios', filtros.anios.join(','));
            if (filtros.djs.length > 0) params.append('djs', filtros.djs.join(','));
            if (filtros.discotecas.length > 0) params.append('discotecas', filtros.discotecas.join(','));

            if ([...params].length > 0) url += `?${params.toString()}`;

            const response = await fetch(url);
            const data = await response.json();

            if (response.ok) {
                console.log(data);
                //usar solo lo que viene del backend
                setAnioArray(data.anios || []);
                setDjArray(data.djs || []);
                setDiscotecaArray(data.discotecas || []);

            } else {
                console.log('Error al cargar los filtros');

            }
        } catch (error) {
            console.log('Error al cargar los filtros: ', error);
        }
    }

    //metodo que envia la nueva sesion
    const crearSesion = async (sesion) => {
        try {
            const response = await fetch("https://eukoteka-api.onrender.com/api/sesiones", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(sesion),
            });

            if (!response.ok) {
                console.error("Error al crear sesión:", response.status);
                return null;
            }

            const data = await response.json();
            return data;

        } catch (error) {
            console.error("Error al crear la sesion: ", error);
            return null;
        }
    };

    //metodo que edita una sesión concreta
    const actualizarSesion = async (sesion) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/sesiones/${sesion.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(sesion),
            });

            if (!response.ok) {
                console.error("Error al actualizar sesión:", response.status);
                return null;
            }

            const data = await response.json();
            return data; //Devuelve la sesión actualizada
        } catch (error) {
            console.error("Error al actualizar la sesión:", error);
            return null;
        }
    };

    //metodo que edita los DJs de una sesión concreta
    const actualizarDjsSesion = async (idSesion, djs) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/sesiones/${idSesion}/djs`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(djs),
            });

            if (!response.ok) {
                console.error("Error al actualizar DJs:", response.status);
                return null;
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Error al actualizar DJs: ", error);
            return null;
        }
    };

    //----------------------------





    //METODOS CANCIONES

    //metodo que devuelve la lista paginada de canciones, con o sin filtros
    const fetchCanciones = async (paginaL = 0, filtrosOverride = null) => {
        try {
            const filtrosActuales = filtrosOverride || filtrosCanciones; // si vienen, usamos los filtros pasados
            let url = 'https://eukoteka-api.onrender.com/api/canciones';

            //si ya hay filtros seleccionados, se pasa como query params
            const params = new URLSearchParams();
            if (filtrosActuales.anios.length > 0) params.append('anios', filtrosActuales.anios.join(','));
            if (filtrosActuales.generos.length > 0) params.append('generos', filtrosActuales.generos.join(','));
            if (filtrosActuales.buscar.length > 0) params.append('search', filtrosActuales.buscar);

            const query = params.toString();
            if (query) url += `?${query}&page=${paginaL}&size=45`;
            else url += `?page=${paginaL}&size=45`;

            const response = await fetch(url);
            const data = await response.json();

            if (response.ok) {
                setHasMoreCanciones(!data.last);
                // console.log(data.content);
                setCanciones(prev => {
                    const idsPrev = new Set(prev.map(c => c.id));
                    const nuevos = data.content.filter(c => !idsPrev.has(c.id));
                    return [...prev, ...nuevos];
                });
            }

        } catch (error) {
            console.log('Error al cargar las canciones: ', error);
        }
    }

    //metodo que devuelve los filtros disponibles de las canciones
    const fetchFiltrosCancionesDisponibles = async () => {
        try {
            const params = new URLSearchParams();


            if (filtrosCanciones.anios.length > 0) params.append('anios', filtrosCanciones.anios.join(','));
            if (filtrosCanciones.generos.length > 0) params.append('generos', filtrosCanciones.generos.join(','));

            const url = `https://eukoteka-api.onrender.com/api/canciones/filtros?${params.toString()}`;
            const response = await fetch(url);
            const data = await response.json();

            if (response.ok) {
                setAnioArrayCancion(data.anios || []);
                setGeneroArrayCancion(data.generos || []);
            } else {
                console.error('Error al filtrar canciones', data);
            }
        } catch (error) {
            console.error('Error al filtrar canciones: ', error);
        }
    };

    const crearCancion = async (cancion) => {
        try {
            const resp = await fetch("https://eukoteka-api.onrender.com/api/canciones", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cancion)
            });

            if (!resp.ok) {
                const err = await resp.json().catch(() => null);
                alert(err?.message || "No se pudo crear la canción");
                return null;
            }

            alert("✅ Canción creada con éxito!");
            return await resp.json();

        } catch (error) {
            alert("Error de red al crear canción");
            console.error("Error al crear la canción: ", error);
            return null;
        }
    }

    //editar canciones
    const actualizarCancion = async (cancion) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/canciones/${cancion.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cancion),
            });

            if (!response.ok) {
                const err = await response.json().catch(() => null);
                alert(err?.message || "No se pudo actualizar la canción");
                return null;
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Error al actualizar canción:", error);
            return null;
        }
    };

    //----------------------------





    //METODOS TRACKLIST

    //metodo que devuelve la lista e canciones de una sesión concreta
    const fetchTracklistSesion = async (idSesion) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/tracklist/${idSesion}`);
            const data = await response.json();
            if (response.ok) {
                return data;
            }
        } catch (error) {
            console.log('Error al cargar la tracklist: ', error);

        }
    }

    //----------------------------





    //METODOS DE DISCOTECAS

    //metodo que devuelve la lista paginada de discotecas, con o sin filtros
    const fetchDiscotecas = async (pagina = 0, filtrosOverride = null) => {
        try {
            const filtrosActuales = filtrosOverride || filtrosDiscotecas;

            const params = new URLSearchParams();
            params.append("page", pagina);
            params.append("size", 30);

            filtrosActuales.anios.forEach(a => params.append("anios", a));
            filtrosActuales.provincias.forEach(p => params.append("provincias", p));
            filtrosActuales.localidades.forEach(l => params.append("localidades", l));

            const url = `https://eukoteka-api.onrender.com/api/discotecas?${params.toString()}`;
            const response = await fetch(url);
            const data = await response.json();

            if (response.status === 200) {
                setHasMoreDiscotecas(!data.last);

                if (pagina === 0) setDiscotecas(data.content);
                else setDiscotecas(prev => [...prev, ...data.content]);
            } else console.error("Error al cargar discotecas");
        } catch (error) {
            console.log('Error al cargar discotecas:', error);
        }
    };

    const fetchFiltrosDisponiblesDiscotecas = async () => {
        try {
            const params = new URLSearchParams();
            if (filtrosDiscotecas.provincias.length > 0) {
                filtrosDiscotecas.provincias.forEach(p => params.append('provincias', p));
            }

            let url = 'https://eukoteka-api.onrender.com/api/discotecas/filtros';
            if ([...params].length > 0) url += `?${params.toString()}`;

            const response = await fetch(url);
            const data = await response.json();

            if (response.ok) {
                setProvinciasArray(data.provincias.map(p => p.value));
            } else {
                console.error("Error al traer filtros de discotecas", data);
            }
        } catch (error) {
            console.error("Error al traer filtros de discotecas", error);
        }
    };

    const fetchDiscoById = async (id) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/discotecas/${id}`);
            const data = await response.json();

            if (response.ok) {
                return data[0];
            } else {
                console.log(`Error al traer la discoteca con el id ${id}`);
            }
        } catch (error) {
            console.log(`Error al traer la discoteca con el id ${id}: `, error);
        }
    };

    const crearDiscoteca = async (discoteca) => {
        try {
            const response = await fetch("https://eukoteka-api.onrender.com/api/discotecasCrear", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(discoteca),
            });

            if (!response.ok) {
                const err = await response.json().catch(() => null);
                alert(err?.message || "No se pudo crear la discoteca");
                return null;
            }

            return await response.json();
        } catch (e) {
            console.error("Error al crear discoteca:", e);
            return null;
        }
    };

    const actualizarDiscoteca = async (discoteca) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/discotecas/${discoteca.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(discoteca),
            });

            if (!response.ok) {
                const err = await response.json().catch(() => null);
                alert(err?.message || "No se pudo editar la discoteca");
                return null;
            }

            return await response.json();
        } catch (e) {
            console.error("Error al editar discoteca:", e);
            return null;
        }
    };

    //----------------------------





    //METODOS DE MULTIMEDIA

    const fetchTodoMultimedia = async () => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/multimedia?page=${pagina}&size=30`)
            const data = await response.json();

            if (response.ok) {
                setHasMoreMultimedia(!data.last)
                setMultimedia(pre => [...pre, ...data.content]);
            }

        } catch (error) {
            console.log('Error la cargar el material multimedia: ', error);
        }
    }


    //----------------------------





    //METODOS DE COMENTARIOS

    //metodo que devuelve los comentarios de un tipo especifico
    const fetchComentarios = async () => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/comentarios?page=0&size=15&tipo=${tipoObjetivo}&id=${idObjetivo}`);
            const data = await response.json();

            if (response.ok) {
                setComentarios(data.content);
            }
        } catch (error) {
            console.log('Error al cargar los comentarios: ', error);
        }
    }

    const crearComentario = async (contenido, idComentarioPadre = null) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/comentarios`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify({
                    tipoObjetivo,
                    idObjetivo,
                    contenido,
                    idComentarioPadre
                })
            });

            if (response.ok) {
                const nuevo = await response.json();

                setComentarios(prev => {
                    //Si NO es respuesta - comentario raíz
                    if (!idComentarioPadre) {
                        return [nuevo, ...prev];
                    }

                    //Si SÍ es respuesta - se inserta en el árbol
                    return insertarRespuesta(prev, nuevo);
                });

                return true;
            } else {
                const err = await response.json().catch(() => null);
                alert(err?.message || "Error al comentar");
                return;
            }
        } catch (error) {
            console.error("Error al crear el comentario:", error);
        }
    };

    //metodo que mete el comentario en el lugar correct del arbol
    const insertarRespuesta = (lista, respuestaNueva) => {
        return lista.map(coment => {
            //si este comentario es el padre -> añadir la respuesta
            if (coment.id === respuestaNueva.idComentarioPadre) {
                return {
                    ...coment,
                    respuestas: [...coment.respuestas, respuestaNueva]
                };
            }
            //si tiene respuestas -> buscar en sus hijos
            if (coment.respuestas?.length > 0) {
                return {
                    ...coment,
                    respuestas: insertarRespuesta(coment.respuestas, respuestaNueva)
                };
            }

            //si no es el padre y no tiene respuestas -> devolver igual
            return coment;
        });
    };

    //metodo que elimina un comentario
    const eliminarComentario = async (idComentario) => {
        try {
            const res = await fetch(`https://eukoteka-api.onrender.com/api/comentarios/${idComentario}`, {
                method: "DELETE",
                credentials: "include"
            });

            if (!res.ok) return false;

            //actualizar estado
            setComentarios(prev => marcarComentarioComoEliminado(prev, idComentario));

            return true;

        } catch (error) {
            console.error("Error al eliminar el comentario: ", error);
            return false;
        }
    };

    //metodo que modifica los comentarios eliminados para no romper el arbol
    const marcarComentarioComoEliminado = (lista, id) => {
        return lista.map(c => {
            //si este comentario es el que queremos eliminar -> modificar su contenido sin borrarlo del todo
            if (c.id === id) {
                return {
                    ...c,
                    eliminado: true,
                    contenido: "[Comentario eliminado]"
                };
            }
            //si el comentario tiene respuestas -> buscar dentro el correcto
            if (c.respuestas?.length > 0) {
                return {
                    ...c,
                    respuestas: marcarComentarioComoEliminado(c.respuestas, id)
                };
            }

            //si no -> devolver igual
            return c;
        });
    };

    //metodo que suma un like a un comentario con un ide concreto
    const toggleLike = async (idComentario) => {
        try {
            const res = await fetch(
                `https://eukoteka-api.onrender.com/api/comentarios/${idComentario}/like`,
                {
                    method: "POST",
                    credentials: "include",
                }
            );

            if (res.ok) {
                const data = await res.json();
                setComentarios(prev => actualizarLikeEnArbol(prev, idComentario, data));
            } else {
                const err = await res.json().catch(() => null);
                alert(err?.message || "Error al dar like");
                return;
            }
        } catch (error) {
            console.error("Error al dar like: ", error);
        }
    };

    //metodo que actualiza el estado de los likes del arbol
    const actualizarLikeEnArbol = (lista, targetId, data) => {
        return lista.map(coment => {
            //actualiza si este es el comentario que queremos dar like
            if (coment.id === targetId) {

                //actualiza si el backend devuelve los likes exactos
                if (data && typeof data.likes === 'number') {
                    return { ...coment, likes: data.likes };
                }

                //si el comentario ya tiene un like del mismo usuario, no permitir darle otra vez
                const delta = (data && data.liked) ? 1 : -1;

                return { ...coment, likes: (typeof coment.likes === 'number' ? coment.likes : 0) + delta };
            }

            //si tiene respuestas -> buscar dentro el correcto
            const respList = Array.isArray(coment.respuestas) ? coment.respuestas : [];
            if (respList.length > 0) {
                return {
                    ...coment,
                    respuestas: actualizarLikeEnArbol(respList, targetId, data)
                };
            }

            //si no -> devolver igual
            return coment;
        });
    };

    //----------------------------





    //METODOS DE LOGIN Y REGISTRO

    //metodo para registrar un usuario nuevo
    const registrarUsuario = async () => {
        try {
            const { username, email } = datosFormRegistro;

            //validación básica cliente
            if (!username || username.trim().length < 2) {
                alert("El nombre de usuario es obligatorio (2+ caracteres).");
                return;
            }

            if (!email || email.trim().length === 0) {
                alert("El correo es obligatorio. Recibirás un enlace para verificar.");
                return;
            }
            //validacion simple de email
            const emailNormalized = email.trim().toLowerCase();
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(emailNormalized)) {
                alert("Introduce un correo válido.");
                return;
            }

            //construir la url con codificación
            const url = `https://eukoteka-api.onrender.com/api/register?username=${encodeURIComponent(username.trim())}&email=${encodeURIComponent(emailNormalized)}`;

            const response = await fetch(url, {
                method: 'POST'
            });

            if (!response.ok) {
                //intenta leer JSON de error
                try {
                    const err = await response.json();
                    alert(err.message || 'Error al registrarse');
                } catch (e) {
                    alert('Error al registrarse');
                }
                return;
            }

            const created = await response.json();
            console.log('Usuario creado (pendiente verificación):', created);

            //limpiar form y abrir modal que avisa de revisar el spam
            setDatosFormRegistro({ username: '', email: '' });
            alert('Cuenta creada. Revisa tu correo y sigue el enlace para verificar tu cuenta.\nRevisa la carpeta de spam.\nEsta pestaña debe cerrarse');
        } catch (error) {
            console.error('Error en registrarUsuario:', error);
            alert('Error de red al registrar usuario');
        }

    }

    //metodo para iniciar sesión
    const loginUsuario = async () => {
        try {
            const { email } = datosFormLogin;

            //validaciones básicas
            if (!email || email.trim().length === 0) {
                alert("El correo es obligatorio.");
                return;
            }

            const emailNormalized = email.trim().toLowerCase();
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(emailNormalized)) {
                alert("Introduce un correo válido.");
                return;
            }

            //envío del correo para verificación de login
            const response = await fetch("https://eukoteka-api.onrender.com/api/login", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({ email: emailNormalized }),
            });

            const text = await response.text();

            if (response.ok) {
                alert("Se ha enviado un enlace a tu correo para iniciar sesión.\nRevisa la carpeta de spam.\nEsta pestaña debe cerrarse");
                console.log("✅ Respuesta del backend:", text);
                setDatosFormLogin({ email: "" }); // limpia el form
                //setUsuario (hacer metodo para coger el nombre del user)
                window.close();
            } else {
                console.error("Error al iniciar sesión:", text);
                alert("Error al iniciar sesión: " + text);
            }
        } catch (error) {
            console.error("Error al iniciar sesión: ", error);
        }
    };

    //metodo que hace autologin y se ejecuta en cualquier página
    const autoLogin = async () => {
        try {
            const response = await fetch("https://eukoteka-api.onrender.com/api/auto-login", {
                method: "GET",
                credentials: "include"
            });

            if (response.ok) {
                const user = await response.json();
                setUsuario(user);
                setIsLoged(true);
                // console.log(user);

                console.log("✅ Sesión restaurada:", user.username);
            } else {
                console.log("No hay sesión activa");
                setIsLoged(false);
                setUsuario(null);
            }
        } catch (error) {
            console.error("Error error al iniciar sesión autmaticamente: ", error);
        } finally {
            setIsAuthChecked(true); //confirmamos que la carga de inicio de sesión ha finalizado
        }
    }

    //metodo para cerrar sesión
    const logout = async () => {
        try {
            await fetch("https://eukoteka-api.onrender.com/api/logout", {
                method: "POST",
                credentials: "include",
            });
            setIsLoged(false);
            setUsuario(null);
            console.log("Sesion cerrada");
        } catch (error) {
            console.error("Error al cerrar sesion:", error);
        }
    }

    //metodo para verificar el inicio de sesión
    const verificarLogin = async (token) => {
        try {
            const res = await fetch(
                `https://eukoteka-api.onrender.com/api/verificar-login?token=${token}`,
                {
                    method: "GET",
                    credentials: "include"
                }
            );

            if (!res.ok) {
                alert("Error verificando login");
                return;
            }

            const user = await res.json();
            setUsuario(user);
            setIsLoged(true);

            console.log("Usuario logueado:", user);

        } catch (e) {
            console.error("Error al verificar el usuario", e);
        }
    };

    //metodo para verificar el registro
    const verificarRegistro = async (token) => {
        try {
            const res = await fetch(
                `https://eukoteka-api.onrender.com/api/verificar-registro?token=${token}`,
                {
                    method: "GET",
                    credentials: "include"
                }
            );

            if (!res.ok) {
                alert("Error verificando registro");
                return;
            }

            console.log("Cuenta verificada correctamente");
            await autoLogin();

        } catch (error) {
            console.error("Error al verificar el registro: ", error);
        }
    };

    //----------------------------





    //METODOS A ARTICULOS
    //metodo que devuelve todos los articulos paginados
    const fetchTodoArticulos = async () => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/articulos?page=${pagina}&size=30`)
            const data = await response.json();
            if (response.ok) {
                setHasMoreArticulos(!data.last)
                setArticulos(pre => [...pre, ...data.content]);
            }

        } catch (error) {
            console.log('Error al cargar los articulos: ', error);
        }
    }


    //metodo que devuelve un articulo concreto por ID
    const fetchArtByID = async (id) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/articulos/${id}`);
            const data = await response.json();

            if (response.ok) {
                return data;
            } else {
                console.log('Error');
            }
        } catch (error) {
            console.log('Error al cargar el articulo: ', error);
        }
    }

    //metodo que edita un articulo
    const actualizarArticulo = async (id, datos) => {
        try {
            const res = await fetch(`https://eukoteka-api.onrender.com/api/articulos/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(datos),
            });

            if (!res.ok) {
                const err = await res.json().catch(() => null);
                alert(err?.message || "No se pudo actualizar el artículo");
                return null;
            }

            alert("✅ Artículo actualizado con éxito");
            return await res.json();

        } catch (error) {
            alert("Error de red al actualizar el artículo");
            console.error(error);
            return null;
        }
    };
    //----------------------------





    //METODOS RESIDENCIAS

    //meotdo que devuelve las residencias de los DJs
    const fetchResidenciasByDiscoteca = async (idDiscoteca) => {
        try {
            const response = await fetch(`https://eukoteka-api.onrender.com/api/residencias/${idDiscoteca}`);
            const data = await response.json();
            if (response.ok) {
                setResidencias(data)
            } else {
                console.error("Error al cargar residencias:", response.status);
                return [];
            }
        } catch (error) {
            console.error("Error al cargar residencias:", error);
            return [];
        }
    };

    //crear residencia
    const crearResidencia = async (idDiscoteca, residencia) => {
        try {
            const res = await fetch(`https://eukoteka-api.onrender.com/api/residencias/${idDiscoteca}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(residencia)
            });

            if (!res.ok) {
                const err = await res.json().catch(() => null);
                alert(err?.message || "No se pudo crear la residencia");
                return null;
            }

            const data = await res.json();
            return data;
        } catch (e) {
            console.error("Error al crear residencia:", e);
            return null;
        }
    };

    //editar residencia
    const editarResidenciaApi = async (id, residencia) => {
        try {
            const res = await fetch(`https://eukoteka-api.onrender.com/api/residencias/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(residencia)
            });

            if (!res.ok) {
                const err = await res.json().catch(() => null);
                alert(err?.message || "No se pudo editar la residencia");
                return null;
            }

            const data = await res.json();
            return data;
        } catch (e) {
            console.error("Error al editar residencia:", e);
            return null;
        }
    };

    //eliminar residencia
    const borrarResidencia = async (id) => {
        try {
            const res = await fetch(`https://eukoteka-api.onrender.com/api/residencias/${id}`, {
                method: "DELETE"
            });

            return res.ok;
        } catch (e) {
            console.error("Error al eliminar residencia:", e);
            return false;
        }
    };

    return (
        <Contexto.Provider value={{
            anioArray, fetchSesiones, sesionesConcreta, setSesionConcreta,
            fetchCanciones, fetchSesionById, sesiones, setSesiones, pagina,
            setPagina, fetchDiscoById, fetchDiscotecas, discotecas, setDiscotecas,
            discoConcreta, setDiscoConcreta, isOpen, setIsOpen, filtros,
            setFiltros, djArray, setDjArray, fetchFiltrosDisponibles,
            discotecaArray, setDiscotecaArray, canciones, setCanciones,
            filtrosCanciones, setFiltrosCanciones, fetchTracklistSesion,
            fetchTodoMultimedia, multimedia, hasMoreSesiones, setHasMoreSesiones,
            setMultimedia, modalSesiones, setModalSesiones, fetchSesionByCancion,
            paginaSesiones, setPaginaSesiones, idSong, setIdSong, fetchComentarios,
            comentarios, datosFormLogin, setDatosFormLogin, datosFormRegistro, setDatosFormRegistro,
            registrarUsuario, loginUsuario, isLoged, setIsLoged, usuario, setUsuario,
            logout, openLogin, setOpenLogin, articulos, setArticulos, fetchTodoArticulos,
            fetchArtByID, isAuthChecked, setIsAuthChecked, artConcreto, setArtConcreto,
            crearComentario, actualizarSesion, actualizarDjsSesion, toggleLike, tipoObjetivo,
            fetchResidenciasByDiscoteca, residencias, setResidencias, actualizarDiscoteca,
            actualizarCancion, eliminarComentario, modoVistaUsuario, setModoVistaUsuario,
            crearResidencia, editarResidenciaApi, borrarResidencia, idObjetivo, setTipoObjetivo,
            setIdObjetivo, verificarLogin, verificarRegistro, hasMoreModalSesiones, setHasMoreModalSesiones,
            modalPage, setModalPage, hasMoreCanciones, setHasMoreCanciones, hasMoreDiscotecas,
            setHasMoreDiscotecas, hasMoreArticulos, setHasMoreArticulos, hasMoreMultimedia,
            setHasMoreMultimedia, paginaCancion, setPaginaCancion, anioArrayCancion,
            setAnioArrayCancion, generoArrayCancion, setGeneroArrayCancion,
            fetchFiltrosCancionesDisponibles, loadingCanciones, setLoadingCanciones,
            loadingSesiones, setLoadingSesiones, fetchFiltrosDisponiblesDiscotecas,
            filtrosDiscotecas, setFiltrosDiscotecas, aniosDiscotecasDisponibles,
            setAniosDiscotecasDisponibles, provinciasArray, setProvinciasArray,
            localidadesPorProvincia, setLocalidadesPorProvincia, crearDiscoteca,
            crearSesion, crearCancion, actualizarArticulo, loadingDiscotecas,
            setLoadingDiscotecas, loadingArticulos, setLoadingArticulos
        }}>
            {children}
        </Contexto.Provider>
    )

}
