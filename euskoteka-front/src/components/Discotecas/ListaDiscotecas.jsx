import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { Contexto } from '../../context/Contexto';
import CardDiscoteca from '../Cards/CardDiscoteca';
import Button from '../Utilidades/Button'
import GifCargandoComp from '../Utilidades/GifCargandoComp'

const ListaDiscotecas = () => {

    const { fetchDiscotecas, discotecas,
        setDiscotecas, usuario, setPagina,
        hasMoreDiscotecas, filtrosDiscotecas, crearDiscoteca,
        modoVistaUsuario, loadingDiscotecas } = useContext(Contexto);
    const nav = useNavigate();

    useEffect(() => {
        setDiscotecas([]);
        setPagina(0);
        fetchDiscotecas(0, filtrosDiscotecas);
    }, [filtrosDiscotecas]);

    //vaciar el valor del numero de pagina y obejto de discotecas al desmontar el componente
    useEffect(() => {
        return () => {
            setDiscotecas([]);
            setPagina(0);
        }
    }, [setDiscotecas, setPagina]);

    const handleSelDisco = async (id) => {
        nav(`/discoteca/${id}`)
    }

    //abre o cierra el pane lde creación de discoteca
    const [creando, setCreando] = useState(false);

    const [form, setForm] = useState({
        nombre: '',
        historia: '',
        fechaApertura: '',
        fechaCierre: '',
        duenno: '',
        foto: '',
        fotoCalidad: '',
        logo: '',
        provincia: '',
        localidad: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    const handleGuardarNueva = async () => {
        const nueva = await crearDiscoteca(form);

        if (nueva) {
            alert("✅ Discoteca creada correctamente");

            // reset panel
            setCreando(false);
            setForm({
                nombre: '',
                historia: '',
                fechaApertura: '',
                fechaCierre: '',
                duenno: '',
                foto: '',
                fotoCalidad: '',
                logo: '',
                provincia: '',
                localidad: '',
            });

            window.location.reload();
        } else {
            alert("❌ Error al crear la discoteca");
        }
    };
    if (loadingDiscotecas && discotecas.length === 0) {
        return <GifCargandoComp text='Cargando discotecas...' />;
    }

    return (
        <>
            {/* boton crear nueva disco (solo admin) */}
            {usuario?.rol === "ADMIN" && !modoVistaUsuario && (
                <div className="crearElemenContainer">
                    <Button
                        text="➕ Crear discoteca nueva"
                        func={() => setCreando(true)}
                        estilo="btnCrear"
                    />
                </div>
            )}

            {/* panel creación nueva disco */}
            {creando && (
                <div className="panel-crear-disco">
                    <h2>Crear nueva discoteca</h2>

                    <div className="panelCampos">
                        <label>Nombre:</label>
                        <input
                            name="nombre"
                            value={form.nombre}
                            onChange={handleChange}
                        />

                        <label>Historia:</label>
                        <textarea
                            name="historia"
                            value={form.historia}
                            onChange={handleChange}
                        />

                        <label>Año apertura:</label>
                        <input
                            type="number"
                            name="fechaApertura"
                            value={form.fechaApertura}
                            onChange={handleChange}
                        />

                        <label>Año cierre:</label>
                        <input
                            type="number"
                            name="fechaCierre"
                            value={form.fechaCierre}
                            onChange={handleChange}
                        />

                        <label>Dueño:</label>
                        <input
                            name="duenno"
                            value={form.duenno}
                            onChange={handleChange}
                        />

                        <label>Foto (URL):</label>
                        <input
                            name="foto"
                            value={form.foto}
                            onChange={handleChange}
                        />

                        <label>Foto calidad (URL):</label>
                        <input
                            name="fotoCalidad"
                            value={form.fotoCalidad}
                            onChange={handleChange}
                        />

                        <label>Logo (URL):</label>
                        <input
                            name="logo"
                            value={form.logo}
                            onChange={handleChange}
                        />

                        <label>Provincia:</label>
                        <input
                            name="provincia"
                            value={form.provincia}
                            onChange={handleChange}
                            placeholder="Ejemplo: Gipuzkoa"
                        />

                        <label>Localidad:</label>
                        <input
                            name="localidad"
                            value={form.localidad}
                            onChange={handleChange}
                            placeholder="Ejemplo: Donostia"
                        />
                    </div>

                    <div className="panelBotones">
                        <Button text="💾 Guardar" func={handleGuardarNueva} />
                        <Button text="❌ Cancelar" func={() => setCreando(false)} />
                    </div>
                </div>
            )}



            <div className='ConjuntoTarjetas'>
                {discotecas.length > 0 ? (
                    discotecas.map((disco) => (
                        <CardDiscoteca
                            key={disco.id}
                            id={disco.id}
                            func={handleSelDisco}
                            nombre={disco.nombre}
                            foto={disco.foto}
                            localidad={disco.localidad}
                            periodo={disco.periodoActividad}
                        />
                    ))
                ) : (
                    <h1 style={{color: "white"}}>No se ha podido cargar</h1>
                )}
            </div>
            <div className='contCargarMas'>
                {hasMoreDiscotecas ? (
                    <Button
                        text="Cargar más"
                        func={() => setPagina(prev => prev + 1)}
                        estilo="btnCargarMas"
                    />
                ) : (
                    <h2 style={{ color: "white" }}>Fin de la lista</h2>
                )}
            </div>
        </>
    )
}

export default ListaDiscotecas