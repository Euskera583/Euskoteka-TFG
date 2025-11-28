import React, { useContext, useEffect } from 'react'
import { Contexto } from '../../context/Contexto'
import Modal from '../Utilidades/Modal';
import Button from '../Utilidades/Button';

const FiltrosSesiones = () => {

    const { anioArray, setSesiones, djArray, setPaginaSesiones, filtros,
        setFiltros, discotecaArray, fetchFiltrosDisponibles } = useContext(Contexto);

    //hace fetch para comprobar que filtros hay disponibles cuando se elige alguna opción
    useEffect(() => {
        const cargar = async () => {
            await fetchFiltrosDisponibles();
        }

        cargar();
    }, [
        filtros.anios.join(','),
        filtros.djs.join(','),
        filtros.discotecas.join(',')
    ]);

    //cuando se selecciona un año, se cambian los filtros disponibles
    const handleCheckAnio = (e) => {
        const anio = parseInt(e.target.value);
        let nuevosAnios;

        if (e.target.checked) {
            nuevosAnios = [...filtros.anios, anio];
        } else {
            nuevosAnios = filtros.anios.filter(fecha => fecha !== anio);
        }

        //actualizo el objeto filtros
        setFiltros(prev => ({ ...prev, anios: nuevosAnios }));

        //reset para recargar correctamente la lista de sesiones
        setSesiones([]);
        setPaginaSesiones(0);
    }

    const handleCheckDJ = (e) => {
        const dj = (e.target.value);

        let nuevosDjs;

        if (e.target.checked) {
            nuevosDjs = [...filtros.djs, dj];
        } else {
            nuevosDjs = filtros.djs.filter(persona => persona !== dj);
        }

        setFiltros(prev => ({ ...prev, djs: nuevosDjs }));

        setSesiones([]);
        setPaginaSesiones(0);
    }

    const handleCheckDisco = (e) => {
        const disco = (e.target.value);
        let nuevasDiscos;

        if (e.target.checked) {
            nuevasDiscos = [...filtros.discotecas, disco];
        } else {
            nuevasDiscos = filtros.discotecas.filter(local => local !== disco);
        }

        setFiltros(prev => ({ ...prev, discotecas: nuevasDiscos }));

        setSesiones([]);
        setPaginaSesiones(0);
    }

    const borraFiltro = () => {
        setFiltros({
            anios: [],
            djs: [],
            discotecas: []
        })
    }

    return (
        <div>
            <Modal text='Abrir filtros' local={true}>
                <div className='filtros'>
                    <div className='opcionesFiltroAnio'>
                        <h3>Filtrar por año</h3>
                        {anioArray.map((yearObj, index) => (
                            <label key={index} style={{ display: yearObj.compatible ? "block" : "none" }}>
                                <input
                                    type="checkbox"
                                    value={yearObj.value}
                                    disabled={!yearObj.compatible}
                                    checked={filtros.anios.includes(parseInt(yearObj.value))}
                                    onChange={handleCheckAnio}
                                />
                                {yearObj.value}
                            </label>
                        ))}
                    </div>

                    <div className='opcionesFiltroDj'>
                        <h3>Filtrar por DJ</h3>
                        {djArray.map((dj, index) => (
                            <label key={index} style={{ display: dj.compatible ? "block" : "none" }}>
                                <input
                                    type='checkbox'
                                    value={dj.value}
                                    disabled={!dj.compatible}
                                    checked={filtros.djs.includes(dj.value)}
                                    onChange={handleCheckDJ}
                                />
                                {dj.value}
                            </label>
                        ))}
                    </div>

                    <div className='opcionesFiltroDiscoteca'>
                        <h3>Filtrar por discoteca</h3>
                        {discotecaArray.map((disco, index) => (
                            <label key={index} style={{ display: disco.compatible ? "block" : "none" }}>
                                <input
                                    type='checkbox'
                                    value={disco.value}
                                    disabled={!disco.compatible}
                                    checked={filtros.discotecas.includes(disco.value)}
                                    onChange={handleCheckDisco}
                                />
                                {disco.value}
                            </label>
                        ))}
                    </div>
                </div>
                <Button
                    text='Borrar filtros'
                    func={borraFiltro}
                    estilo='btnBorraFiltro'
                />
            </Modal>
        </div>
    )
}

export default FiltrosSesiones
