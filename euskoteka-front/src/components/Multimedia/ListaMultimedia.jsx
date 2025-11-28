import React, { useContext, useEffect } from 'react';

import { Contexto } from '../../context/Contexto';
import CardMultimedia from '../Cards/CardMultimedia';
import Button from '../Utilidades/Button'

const ListaMultimedia = () => {

    const { fetchTodoMultimedia, pagina, setPagina, multimedia,
        setMultimedia, hasMoreMultimedia } = useContext(Contexto);

    useEffect(() => {
        fetchTodoMultimedia();        
    }, [pagina]);

    useEffect(() => {
        return () => {
            setMultimedia([]);
            setPagina(0)
        }
    }, [setMultimedia, setPagina]);


    return (
        <>
            <div className='ConjuntoTarjetas'>
                {multimedia.length > 0 ? (
                    multimedia.map((multi) => (
                        <CardMultimedia
                            key={multi.id}
                            idd={multi.id}
                            discoteca={multi.discoteca}
                            descripcion={multi.descripcion}
                            anio={multi.anio}
                            url={multi.url}
                            urlFotoCalidad={multi.urlCalidad}
                        />
                    ))
                ) : (
                    <h1 className='noCarga'>No se ha podido cargar</h1>
                )}
            </div>
            <div style={{ textAlign: "center", margin: "20px 0" }}>
                {hasMoreMultimedia ? (
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

export default ListaMultimedia;