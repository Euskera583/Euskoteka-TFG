import React, { useContext, useEffect, useState } from 'react'
import { useParams } from "react-router";
import { Contexto } from '../../context/Contexto';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import remarkBreaks from 'remark-breaks';
import remarkDirective from 'remark-directive';
import { visit } from 'unist-util-visit';
import Button from '../Utilidades/Button';
import GifCargandoComp from '../Utilidades/GifCargandoComp';

const ArticuloConcreto = () => {

  //coge el parámetro del id de la url para cargar el articulo de ese ID
  const { idArt } = useParams();

  const { artConcreto, setArtConcreto, usuario, modoVistaUsuario, loadingArticulos, setLoadingArticulos,
    actualizarArticulo, fetchArtByID, setTipoObjetivo, setIdObjetivo } = useContext(Contexto);

  const [form, setForm] = useState(null);
  const [guardando, setGuardando] = useState(false);

  //cuando carga la página, se hace fetch para traer la lista de articulos y asignar el tipo de comentarios
  useEffect(() => {
    setArtConcreto(null);

    const fetchData = async () => {
      setLoadingArticulos(true)

      const art = await fetchArtByID(idArt);
      setTipoObjetivo('Articulo');
      setIdObjetivo(art.id);

      setArtConcreto(art)
      setForm(art)

      setLoadingArticulos(false)
    }

    fetchData();

  }, [idArt]);

  //vaciar el objeto al desmontar el componente
  useEffect(() => {
    return () => {
      setArtConcreto({});
    }
  }, [])

  //mientras se cumplan esas condiciones, renderizar el componente de carga
  if (loadingArticulos && !artConcreto) {
    return <GifCargandoComp text="Cargando artículo..." />;
  }

  //variable que normaliza la cadena de salto de linea del markdown por si viene de diferentes formas
  const markdownTexto = (artConcreto.texto || '')
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

  //normaliza lo mismo de antes pero para el subtitulo
  const subti = (artConcreto.subtitulo || '')
    .replace(/\\\\n/g, '\n')
    .replace(/\\n/g, '\n')
    .replace(/\\r\\n/g, '\n');

  //cambia el valor de la propiedad del formulario
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  //hace fetch para guardar los cambios de la edición del artículo
  const handleGuardar = async () => {
    setGuardando(true);

    const res = await actualizarArticulo(form.id, form);

    if (res) {
      //articulo actualizado
      setArtConcreto(res);
    } else {
      console.log('Error, articulo no actualizado');
      
    }

    setGuardando(false);
  };

  // MODO ADMIN
  if (usuario?.rol === "ADMIN" && !modoVistaUsuario) {
    return (
      <div className="adminPanel">
        <div className='adminPanel-group'>

          <h2>Modo administrador: editar artículo</h2>

          <label>
            Nombre:
            <input name="nombre"
              value={form?.nombre || ""}
              onChange={handleChange} />
          </label>

          <label>
            Subtítulo:
            <input name="subtitulo"
              value={form?.subtitulo || ""}
              onChange={handleChange} />
          </label>

          <label>
            Texto:
            <textarea name="texto"
              value={form?.texto || ""}
              onChange={handleChange} />
          </label>

          <label>
            Tipo:
            <input name="tipo"
              value={form?.tipo || ""}
              onChange={handleChange} />
          </label>

          <label>
            Enlace foto:
            <input name="foto"
              value={form?.foto || ""}
              onChange={handleChange} />
          </label>

          <Button
            text={guardando ? "Guardando..." : "💾 Guardar cambios"}
            func={handleGuardar}
            estilo="adminPanel-button"
            disabled={guardando}
          />
        </div>
      </div>
    );
  }

  // MODO USUARIO
  return (
    <>
      <div className='contentArtConcreto'>
        <h1>{artConcreto.nombre}</h1>
        <p style={{ whiteSpace: "pre-line" }}>{subti}</p> {/* Para forzar a hacer el salto de linea por \n */}

        <img src={artConcreto.foto} id="fotoArt" />

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
    </>
  );
}

export default ArticuloConcreto;
