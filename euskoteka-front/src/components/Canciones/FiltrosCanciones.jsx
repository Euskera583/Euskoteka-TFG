import React, { useContext, useEffect, useState } from 'react';
import { Contexto } from '../../context/Contexto';
import Modal from '../Utilidades/Modal';
import Button from '../Utilidades/Button';

const FiltrosCanciones = () => {
    const {
        filtrosCanciones, setFiltrosCanciones,
        setCanciones, setPaginaCancion, setHasMoreCanciones,
        fetchCanciones, anioArrayCancion, generoArrayCancion,
        fetchFiltrosCancionesDisponibles, setLoadingCanciones
    } = useContext(Contexto);

    //variable que mantiene el estado de buscando cuando se está ejecutando uan busqueda con algun filtro
    const [buscando, setBuscando] = useState(false);

    useEffect(() => {
        fetchFiltrosCancionesDisponibles();
    }, [
        filtrosCanciones.anios.join(','),
        filtrosCanciones.generos.join(',')
    ]);

    //funcion que selecciona o quita un filtro de año y cambia el objeto que contiene los filtros
    const handleCheckAnio = async (e) => {
        const anio = parseInt(e.target.value);
        const nuevosAnios = e.target.checked
            ? [...filtrosCanciones.anios, anio]
            : filtrosCanciones.anios.filter(a => a !== anio);

        setFiltrosCanciones(prev => ({ ...prev, anios: nuevosAnios }));
        setCanciones([]);
        setPaginaCancion(0);
        setHasMoreCanciones(true);

        setLoadingCanciones(true);
        await fetchCanciones(0, { ...filtrosCanciones, anios: nuevosAnios });
        setLoadingCanciones(false);
    };

    //funcion que selecciona o quita un filtro de género y cambia el objeto que contiene los filtros
    const handleCheckGenero = async (e) => {
        const genero = e.target.value;
        const nuevosGeneros = e.target.checked
            ? [...filtrosCanciones.generos, genero]
            : filtrosCanciones.generos.filter(g => g !== genero);

        setFiltrosCanciones(prev => ({ ...prev, generos: nuevosGeneros }));
        setCanciones([]);
        setPaginaCancion(0);
        setHasMoreCanciones(true);

        setLoadingCanciones(true);
        await fetchCanciones(0, { ...filtrosCanciones, generos: nuevosGeneros });
        setLoadingCanciones(false);
    };

    //funcion que cambia el texto de busqueda de la propiedad del objeto que contiene los filtros
    const handleChangeBusqueda = (e) => {
        setFiltrosCanciones(prev => ({ ...prev, buscar: e.target.value }));
    };

    //buscar con los filtros seleccionados cuando se le da a buscar
    const handleBuscar = async () => {
        setBuscando(true);
        setLoadingCanciones(true);
        setCanciones([]);
        setPaginaCancion(0);
        setHasMoreCanciones(true);

        await fetchCanciones(0);
        setLoadingCanciones(false);
        setBuscando(false);
    };

    //boton que borra los filtros y restaura el listado
    const handleBorraFiltros = async () => {
        const filtrosLimpiados = { anios: [], generos: [], buscar: '' };
        setFiltrosCanciones(filtrosLimpiados);
        setCanciones([]);
        setPaginaCancion(0);
        setHasMoreCanciones(true);

        setLoadingCanciones(true);
        await fetchCanciones(0, filtrosLimpiados);
        setLoadingCanciones(false);
    };

    return (
        <Modal text="Abrir filtros" local={true}>
            <div className='filtrosCanciones'>

                {/* busqueda */}
                <div className='opcionesFiltroBusqueda'>
                    <input
                        type="text"
                        value={filtrosCanciones.buscar}
                        onChange={handleChangeBusqueda}
                        placeholder="Buscar canción o artista..."
                        className="inputBusquedaNombre"
                    />

                    <Button
                        text="Buscar"
                        func={handleBuscar}
                        estilo="btnVistaModo"
                        disabled={buscando}
                    />

                    <Button
                        text="Borrar filtros"
                        func={handleBorraFiltros}
                        estilo="btnVistaModo"
                    />
                </div>

                <div className='filtrosCanciones-row'>

                    {/* añops */}
                    <div className='filtroBloque'>
                        <h3>Años</h3>
                        {anioArrayCancion.map((a, i) => (
                            <label key={i} >
                                <input
                                    type='checkbox'
                                    value={a.value}
                                    checked={filtrosCanciones.anios.includes(parseInt(a.value))}
                                    disabled={!a.compatible}
                                    onChange={handleCheckAnio}
                                />
                                <span>{a.value}</span>
                            </label>
                        ))}
                    </div>

                    {/* generos */}
                    <div className='filtroBloque'>
                        <h3>Géneros</h3>
                        {generoArrayCancion.map((g, i) => (
                            <label key={i}>
                                <input
                                    type='checkbox'
                                    value={g.value}
                                    checked={filtrosCanciones.generos.includes(g.value)}
                                    disabled={!g.compatible}
                                    onChange={handleCheckGenero}
                                />
                                <span>{g.value}</span>
                            </label>
                        ))}
                    </div>

                </div>

            </div>
        </Modal>
    );
};

export default FiltrosCanciones;
