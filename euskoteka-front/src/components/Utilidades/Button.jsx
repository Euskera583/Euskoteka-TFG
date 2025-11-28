import React from 'react'

const Button = ({text, func, estilo, valor}) => {

    //componente de boton con parametros
    return (
        <button value={valor} className={estilo} onClick={func}>{text}</button>
    )
}

export default Button