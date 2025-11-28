import React, { useContext, useState } from "react";
import { Link } from "react-router";
import logo from "../../media/logo.png"
import Login from "./Login";
import { Contexto } from "../../context/Contexto";
import { useNavigate } from 'react-router';
import Button from "../Utilidades/Button";

const Header = () => {
  const { isLoged, usuario, logout } = useContext(Contexto);
  const nav = useNavigate();

  const [menuOpen, setMenuOpen] = useState(false);

  //funciona para manejar cierre de sesión
  const cierra = async () => {
    const seguro = window.confirm("¿Seguro que quieres cerrar sesión?");
    if (!seguro) return;

    await logout();
    alert("Sesión cerrada");
    window.location.reload(true);
  }

  return (
    <header>
      <div className="header-left">
        <img onClick={() => nav("/")} src={logo} alt="Euskoteka logo" className="logo" />
      </div>

      {/* menu normal (solo escritorio) */}
      <ul className="listaHeader lista-escritorio">
        <Link to={'/'}><li>Pagina principal</li></Link>
        <Link to={'/discotecas'}><li>Discotecas</li></Link>
        <Link to={'/sesiones'}><li>Sesiones</li></Link>
        <Link to={'/canciones'}><li>Canciones</li></Link>
        <Link to={'/multimedia'}><li>Multimedia</li></Link>
        {/* articulos extra solo aparece si se tiene la seisón iniciada */}
        {isLoged && (
          <Link to={'/articulosExtra'}><li>Artículos extra</li></Link>
        )}
      </ul>

      {/* boton hamburguesa (solo movil) */}
      <Button
        text="☰"
        func={() => setMenuOpen(!menuOpen)}
        estilo='hamburger'
      />

      {/* menu movil desplegable */}
      <nav className={`menu-movil ${menuOpen ? "abierto" : ""}`}>
        <Link onClick={() => setMenuOpen(false)} to={'/'}><p>Pagina principal</p></Link>
        <Link onClick={() => setMenuOpen(false)} to={'/discotecas'}><p>Discotecas</p></Link>
        <Link onClick={() => setMenuOpen(false)} to={'/sesiones'}><p>Sesiones</p></Link>
        <Link onClick={() => setMenuOpen(false)} to={'/canciones'}><p>Canciones</p></Link>
        <Link onClick={() => setMenuOpen(false)} to={'/multimedia'}><p>Multimedia</p></Link>
        {isLoged ? (
          <>
            <Link to="/articulosExtra" onClick={() => setMenuOpen(false)}>
              <p>Artículos extra</p>
            </Link>

            <Button
              text="Cerrar sesión"
              func={() => {
                setMenuOpen(false);
                cierra();
              }}
              estilo="btnHeader"
            />
          </>
        ):(
          <Login />
        )}
      </nav>

      <div className="header-right">
        {isLoged ? (
          <div className="userHeader">
            <span className="estiloNombreUser">👤 {usuario?.username}</span>
            <Button
              text="Cerrar sesión"
              func={cierra}
              estilo='btnHeader'
            />
          </div>
        ) : (
          <Login />
        )}
      </div>
    </header>
  )
};

export default Header;