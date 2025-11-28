import React, { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router';
import { Contexto } from '../../context/Contexto';

import CardArticulo from '../Cards/CardArticulo';
import Button from '../Utilidades/Button'
import GifCargandoComp from '../Utilidades/GifCargandoComp';

const ListaArticulos = () => {

    const { articulos, setArticulos, hasMoreArticulos, fetchTodoArticulos, setPagina, loadingArticulos, setLoadingArticulos } = useContext(Contexto);
    const nav = useNavigate();

    //carga la lista de articulos al montar el componennte
    useEffect(() => {
        setLoadingArticulos(true)

        fetchTodoArticulos()

        setLoadingArticulos(false)
    }, []);

    //limpia el array y página al desmontar el componente
    useEffect(() => {
        return () => {
            setPagina(0);
            setArticulos([]);
        }
    }, []);

    //navega a la página de un artículo
    const handleSelArt = async (e) => {
        nav(`/articuloExtra/${e}`);
    }

    //mientras se cumplan esas condiciones, renderizar el componente de carga
    if (loadingArticulos && articulos.length === 0) {
        return <GifCargandoComp text="Cargando artículos..." />;
    }

    return (
        <>
            <div className='ConjuntoTarjetas'>
                {articulos.length > 0 ? (
                    articulos.map((art) => (
                        <CardArticulo
                            key={art.id}
                            id={art.id}
                            nombre={art.nombre}
                            tipo={art.tipo}
                            foto={art.foto}
                            func={handleSelArt}
                        />
                    ))
                ) : (
                    <h1 className='noCarga'>No se ha podido cargar</h1>
                )}
            </div>
            <div className='contCargarMas'>
                {hasMoreArticulos ? (
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

export default ListaArticulos