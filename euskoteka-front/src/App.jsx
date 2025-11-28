import { useState } from 'react'
import { Route, Routes } from 'react-router';

import BotonVistaModo from './components/Utilidades/BotonVistaModo';

import './styles/App.css'
import './styles/StylePagDiscoteca.css'
import './styles/StylePagSesion.css'
import './styles/StyleFiltros.css'
import './styles/StyleHeader.css'
import './styles/StyleCards.css'
import './styles/StyleCanciones.css'
import './styles/StyleComentarios.css'
import './styles/StyleLogin.css'
import './styles/StyleArticulos.css'
import './styles/StyleEdicion.css'

import Header from './components/Esencial/Header'
import Home from './pages/Home'
import Sesiones from './pages/Sesiones'
import PaginaSesionConcreta from './pages/PaginaSesionConcreta';
import PaginaDiscotecaConcreta from './pages/PaginaDiscotecaConcreta';
import Discotecas from './pages/Discotecas';
import Canciones from './pages/Canciones';
import Multimedia from './pages/Multimedia';
import VerificarRegistro from './pages/VerificarRegistro';
import VerificarLogin from './pages/VerificarLogin';
import Footer from './components/Esencial/Footer';
import ArticulosExtra from './pages/ArticulosExtra';
import ArticuloConcreto from './pages/PagArticuloConcreto';
import RutaProtegida from './components/Utilidades/RutaProtegida';

function App() {


  return (
    <div id="app-container">
      <Header />

      <main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/sesiones" element={<Sesiones />} />
          <Route path="/sesion/:idSes" element={<PaginaSesionConcreta />} />
          <Route path="/discoteca/:idDis" element={<PaginaDiscotecaConcreta />} />
          <Route path="/discotecas" element={<Discotecas />} />
          <Route path='canciones' element={<Canciones />} />
          <Route path='multimedia' element={<Multimedia />} />
          <Route path='verificacionRegistroExitosa' element={<VerificarRegistro />} />
          <Route path='verificarLogin' element={<VerificarLogin />} />
          <Route path='articulosExtra' element={
            <RutaProtegida>
              <ArticulosExtra />
            </RutaProtegida>
          }
          />
          <Route path='articuloExtra/:idArt' element={
            <RutaProtegida>
              <ArticuloConcreto />
            </RutaProtegida>
          }
          />
        </Routes>
        <BotonVistaModo />
      </main>

      <Footer />
    </div>
  )
}

export default App
