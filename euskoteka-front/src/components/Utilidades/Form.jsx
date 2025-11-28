import React from 'react';

const Form = ({func, formData, setFormData, textBtn}) => {

    const handleChange = (e) => {
        setFormData({
            ...formData,[e.target.name]:e.target.value
        });
    }

    //componente del formulario de registro e inicio de sesión
    return (
        <div>
            <form onSubmit={func} className='formAcceso'>
                <div className='formElements'>
                    <label htmlFor="username">Nombre de usuario:</label>
                    <input type="text" name="username" id="username" value={formData.username} onChange={handleChange} />
                </div>

                <div className='formElements'>
                    <label htmlFor="email">Correo electornico (obligatorio):</label>
                    <input type="text" name="email" id="email" value={formData.email} onChange={handleChange} />
                </div>
                <button type='submit'>{textBtn}</button>
            </form>
        </div>
    )
}

export default Form