import React, { useContext, useEffect, useState } from 'react'
import { useParams } from "react-router";
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import remarkBreaks from 'remark-breaks';
import remarkDirective from 'remark-directive';
import { visit } from 'unist-util-visit';
import Residencias from './Residencias';

import { Contexto } from '../../context/Contexto'
import Button from '../Utilidades/Button';
import GifCargandoComp from '../Utilidades/GifCargandoComp'

const DiscotecaConcreta = () => {

    //coge el parámetro del id de la url para cargar la discoteca de ese ID
    const { idDis } = useParams();

    const { discoConcreta, setDiscoConcreta, usuario, actualizarDiscoteca,
        modoVistaUsuario, setTipoObjetivo, setIdObjetivo, fetchDiscoById } = useContext(Contexto);
    const [form, setForm] = useState(null);
    const [guardando, setGuardando] = useState(false);

    useEffect(() => {
        setDiscoConcreta(null)

        const fetchData = async () => {
            const dConcreto = await fetchDiscoById(idDis);
            setTipoObjetivo('Discoteca')
            setIdObjetivo(dConcreto.id);

            setDiscoConcreta(dConcreto);
            setForm(dConcreto);

        }

        fetchData();
    }, [idDis]);

    //vaciar el objeto al desmontar el componente
    useEffect(() => {
        return () => {
            setDiscoConcreta({});
        }
    }, [])

    if (!discoConcreta) {
        return <GifCargandoComp text='Cargando discoteca...' />;
    }

    //variable que normaliza la cadena de salto de linea del markdown por si viene de diferentes formas
    const markdownTexto = (discoConcreta.historia || '')
        //primero convertir las secuencias literales "\n" a saltos reales
        .replace(/\\\\n/g, '\n')
        .replace(/\\n/g, '\n')
        .replace(/\\r\\n/g, '\n');

    //funcion para extraer y crear clases personalizadas desde el propio markdown
    const remarkCustomClass = () => (tree) => {
        visit(tree, (node) => {
            if (
                node.type === 'textDirective' ||
                node.type === 'leafDirective' ||
                node.type === 'containerDirective'
            ) {
                const data = node.data || (node.data = {});
                const hast = (data.hProperties || (data.hProperties = {}));
                if (node.name) {
                    hast.className = (hast.className || []).concat(node.name);
                }
            }
        });
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const handleGuardar = async () => {
        setGuardando(true);
        const res = await actualizarDiscoteca(form);
        if (res) {
            alert("✅ Discoteca actualizada correctamente");
            setDiscoConcreta(res);
        } else {
            alert("❌ Error al guardar los cambios");
        }
        setGuardando(false);
    };

    // modo admin editable
    if (usuario?.rol === 'ADMIN' && !modoVistaUsuario) {
        return (
            <div className="adminPanel">
                <div className="adminPanel-group">
                    <h2>Modo administrador: editar discoteca</h2>

                    <label>
                        Nombre:
                        <input
                            name="nombre"
                            value={form?.nombre || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Localidad:
                        <input
                            name="localidad"
                            value={form?.localidad || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Año apertura:
                        <input
                            name="fechaApertura"
                            value={form?.fechaApertura || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Año cierre:
                        <input
                            name="fechaCierre"
                            value={form?.fechaCierre || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Dueño:
                        <input
                            name="duenno"
                            value={form?.duenno || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Información:
                        <textarea
                            name="historia"
                            value={form?.historia || ""}
                            onChange={handleChange}
                            style={{ height: "200px" }}
                        />
                    </label>

                    <label>
                        Foto:
                        <input
                            name="foto"
                            value={form?.foto || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Foto (HD):
                        <input
                            name="fotoCalidad"
                            value={form?.fotoCalidad || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Logo:
                        <input
                            name="logo"
                            value={form?.logo || ""}
                            onChange={handleChange}
                        />
                    </label>

                    <Button
                        text={`${guardando ? "Guardando..." : "💾 Guardar cambios"}`}
                        func={handleGuardar}
                        estilo="btnEliminar"
                    />
                </div>

                <div className="bloqueResidentesDisco">
                    <Residencias idDiscoteca={discoConcreta.id} />
                </div>
            </div>
        );
    }
    return (
        <>
            <div className='fondoDiscotecaConcreta'>
                <div className='bloqueConcretoCabeceraDiscoteca'>
                    <div className='bloqueConcretoTextoDiscoteca'>
                        <h1 id='h1NombreElemento'>{discoConcreta.nombre}</h1>
                        <br />
                        <h3>Fecha de apertura: {discoConcreta.fechaApertura}</h3>
                        <h3>Fecha de cierre: {discoConcreta.fechaCierre}</h3>
                        <br />
                        <h3>Dueño/s: {discoConcreta.duenno}</h3>
                        <br />
                        <h3>Localidad: {discoConcreta.localidad}</h3>
                        <h3>Provincia: {discoConcreta.provincia}</h3>
                        <br />
                    </div>

                    <div className='bloqueConcretoFotoDiscoteca'>
                        <img src={discoConcreta.logo} alt={discoConcreta.nombre} />
                        <img src={discoConcreta.fotoCalidad} alt={discoConcreta.nombre} />
                    </div>

                </div>

                <div className='bloqueConcretoHistoria'>

                    <div className='infoElementoConcreto markdown-body'>
                        <ReactMarkdown
                            remarkPlugins={[remarkGfm, remarkBreaks, remarkDirective, remarkCustomClass]}
                            components={{
                                img: ({ node, ...props }) => {
                                    // busca en el texto si hay alguna clase css personalizada
                                    const classMatch = props.alt?.match(/\{\.([^}]+)\}/);
                                    const className = classMatch ? classMatch[1] : '';
                                    const cleanAlt = props.alt?.replace(/\{\.([^}]+)\}/, '').trim();

                                    return <img {...props} alt={cleanAlt} className={className} />;
                                },
                            }}
                        >
                            {markdownTexto}
                        </ReactMarkdown>
                    </div>
                </div>
                <div className='bloqueResidentesDisco'>
                    <Residencias
                        idDiscoteca={idDis}
                    />
                </div>

            </div>
        </>
    )
}

export default DiscotecaConcreta