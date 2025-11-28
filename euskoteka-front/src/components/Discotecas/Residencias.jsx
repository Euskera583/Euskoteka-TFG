import React, { useContext, useEffect, useState } from 'react';
import { Contexto } from '../../context/Contexto';

const Residencias = ({ idDiscoteca }) => {

  const { fetchResidenciasByDiscoteca, residencias, setResidencias, crearResidencia, editarResidenciaApi, 
    borrarResidencia, usuario, modoVistaUsuario } = useContext(Contexto);

  const [editId, setEditId] = useState(null);//variable con el identificar de la residencia que se está editando
  const [form, setForm] = useState({});
  const [agregando, setAgregando] = useState(false); //variable que mantiene el estado de carga cuando se está aplicando algun cambio

  useEffect(() => {
    fetchResidenciasByDiscoteca(idDiscoteca);
  }, [idDiscoteca]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  //funcion que activa la edición
  const activarEdit = (r) => {
    setEditId(r.id);
    setForm({
      nombreDj: r.nombreDj,
      fechaInicio: r.fechaInicio || "",
      fechaFin: r.fechaFin || "",
      comentariosResi: r.comentariosResi || ""
    });
  };

  //función que cancela la edición
  const cancelEdit = () => {
    setEditId(null);
    setForm({});
  };

  //funcion que guarda los cambios en la base
  const guardarEdicion = async (id) => {
    const updated = await editarResidenciaApi(id, form);
    if (!updated) return alert("❌ Error guardando residencia");

    setResidencias(prev =>
      prev.map(r => (r.id === id ? updated : r))
    );

    setEditId(null);
  };

  //funcion que activa la creación de nuevos registros
  const activarCrear = () => {
    setAgregando(true);
    setForm({
      nombreDj: "",
      fechaInicio: "",
      fechaFin: "",
      comentariosResi: ""
    });
  };

  const cancelarCrear = () => {
    setAgregando(false);
    setForm({});
  };

  const guardarNueva = async () => {
    const nueva = await crearResidencia(idDiscoteca, form);
    if (!nueva) return alert("❌ Error creando residencia");

    setResidencias(prev => [nueva, ...prev]);
    cancelarCrear();
  };

  const eliminar = async (id) => {
    if (!window.confirm("¿Seguro que deseas eliminar esta residencia?")) return;

    const ok = await borrarResidencia(id);
    if (ok) {
      setResidencias(prev => prev.filter(r => r.id !== id));
    }
  };

  return (
    <div className="lista-residencias">
      <h2>DJ’s Residentes</h2>

      {usuario?.rol === 'ADMIN' && !modoVistaUsuario && !agregando && (
        <button onClick={activarCrear} className="btnAgregar">
          ➕ Añadir residencia
        </button>
      )}

      <div className="tablaResidencias">
        <table>
          <thead>
            <tr>
              <th>DJ</th>
              <th>Inicio</th>
              <th>Fin</th>
              <th>Comentarios</th>
              {usuario?.rol === 'ADMIN' && !modoVistaUsuario && <th>Acciones</th>}
            </tr>
          </thead>

          <tbody>


            {usuario?.rol === 'ADMIN' && !modoVistaUsuario && agregando && (
              <tr className="editando">
                <td>
                  <input
                    name="nombreDj"
                    value={form.nombreDj}
                    onChange={handleChange}
                    placeholder="Nombre del DJ"
                  />
                </td>

                <td>
                  <input
                    name="fechaInicio"
                    value={form.fechaInicio}
                    onChange={handleChange}
                    placeholder="Fecha inicio"
                  />
                </td>

                <td>
                  <input
                    name="fechaFin"
                    value={form.fechaFin}
                    onChange={handleChange}
                    placeholder="Fecha fin"
                  />
                </td>

                <td>
                  <input
                    name="comentariosResi"
                    value={form.comentariosResi}
                    onChange={handleChange}
                    placeholder="Comentarios"
                  />
                </td>

                <td>
                  <button onClick={guardarNueva}>💾 Guardar</button>
                  <button onClick={cancelarCrear}>❌ Cancelar</button>
                </td>
              </tr>
            )}



            {residencias.map((r) => (
              <tr key={r.id} className={editId === r.id ? "editando" : ""}>

                {editId === r.id ? (
                  <>
                    <td>
                      <input
                        name="nombreDj"
                        value={form.nombreDj}
                        onChange={handleChange}
                      />
                    </td>

                    <td>
                      <input
                        name="fechaInicio"
                        value={form.fechaInicio}
                        onChange={handleChange}
                      />
                    </td>

                    <td>
                      <input
                        name="fechaFin"
                        value={form.fechaFin}
                        onChange={handleChange}
                      />
                    </td>

                    <td>
                      <input
                        name="comentariosResi"
                        value={form.comentariosResi}
                        onChange={handleChange}
                      />
                    </td>

                    {usuario?.rol === 'ADMIN' && !modoVistaUsuario && (
                      <td>
                        <button onClick={() => guardarEdicion(r.id)}>💾 Guardar</button>
                        <button onClick={cancelEdit}>❌ Cancelar</button>
                      </td>
                    )}
                  </>
                ) : (
                  <>
                    <td>{r.nombreDj}</td>
                    <td>{r.fechaInicio || "-"}</td>
                    <td>{r.fechaFin || "-"}</td>
                    <td>{r.comentariosResi || "-"}</td>

                    {usuario?.rol === 'ADMIN' && !modoVistaUsuario && (
                      <td>
                        <button onClick={() => activarEdit(r)}>✏️ Editar</button>
                        <button onClick={() => eliminar(r.id)}>🗑️ Borrar</button>
                      </td>
                    )}
                  </>
                )}

              </tr>
            ))}

          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Residencias;