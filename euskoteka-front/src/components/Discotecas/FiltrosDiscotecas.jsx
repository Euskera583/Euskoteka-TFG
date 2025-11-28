import React, { useContext, useEffect, useState } from 'react';
import { Contexto } from '../../context/Contexto';
import Button from '../Utilidades/Button';

const FiltrosDiscotecas = () => {
    const { filtrosDiscotecas, setFiltrosDiscotecas, setDiscotecas, setPagina, provinciasArray, 
        fetchFiltrosDisponiblesDiscotecas, fetchDiscotecas, setLoadingDiscotecas } = useContext(Contexto);

    const [buscando, setBuscando] = useState(false);

    useEffect(() => {
        fetchFiltrosDisponiblesDiscotecas();
    }, []);

    //llama automáticamente al fetch cuando los filtros cambian
    useEffect(() => {
        const cargar = async () => {
            setLoadingDiscotecas(true)
            setBuscando(true)


            setDiscotecas([]);
            setPagina(0);
            await fetchDiscotecas(0, filtrosDiscotecas);

            setBuscando(false)
            setLoadingDiscotecas(false)
        }

        cargar();
    }, [filtrosDiscotecas]);

    const handleAnioChange = (e) => {
        setLoadingDiscotecas(true)
        setBuscando(true)

        const year = parseInt(e.target.value);
        setFiltrosDiscotecas(prev => ({
            ...prev,
            anios: year ? [year] : []
        }));

        setBuscando(false)
        setLoadingDiscotecas(false)
    };

    const handleProvinciaChange = (e) => {
        setLoadingDiscotecas(true)
        setBuscando(true)

        const prov = e.target.value;
        setFiltrosDiscotecas(prev => ({
            ...prev,
            provincias: prov ? [prov] : [],
            localidades: []
        }));

        setBuscando(false)
        setLoadingDiscotecas(false)
    };

    const borrarFiltros = () => {
        setFiltrosDiscotecas({
            anios: [],
            provincias: [],
            localidades: []
        });
    };

    return (
        <div className="filtrosDiscotecasContainer">

            <div className="filtroItem">
                <label>Año activo</label>
                <input
                    type="number"
                    value={filtrosDiscotecas.anios[0] || ""}
                    onChange={handleAnioChange}
                    placeholder="Ej: 1995"
                />
            </div>

            <div className="filtroItem">
                <label>Provincia</label>
                <select
                    value={filtrosDiscotecas.provincias[0] || ""}
                    onChange={handleProvinciaChange}
                >
                    <option value="">Selecciona provincia</option>
                    {provinciasArray.map(p => (
                        <option key={p} value={p}>{p}</option>
                    ))}
                </select>
            </div>

            <Button
                text="Borrar filtros"
                func={borrarFiltros}
                estilo="btnFiltroReset"
                disable={buscando}
            />
        </div>
    );
};

export default FiltrosDiscotecas;