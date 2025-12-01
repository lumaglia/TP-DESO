'use client';
import { AlertaCancelar } from '../../Alertas.tsx'
import Link from 'next/link'
import Encabezado from '../../Encabezado';
import '../Alta/AltaHuesped.css'
import { useState } from 'react'

export default function BajaHuesped() {
    const [openAlertaCancelar, setOpenAlertaCancelar] = useState (false)
    const [controlador, setControlador] = useState('1')
    const huesped = {
        'nombre':'JUAN',
        'apellido': 'MARTINEZ',
        'tipoDoc': 'DNI',
        'nroDoc': '1222333'
    }
    function handleClick(){
        setControlador('2');
    }


    if(controlador === '1') {
        return (
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>
                <p>Los datos del huesped {huesped.nombre + ' ' + huesped.apellido + ', ' + huesped.tipoDoc + ' ' + huesped.nroDoc} seran ELIMINADOS, ¿desea continuar?</p>

                <button className='Button' onClick={handleClick}> Eliminar</button>


                <button className='Button' onClick={() => setOpenAlertaCancelar(true)}> Cancelar </button>
                <AlertaCancelar open={openAlertaCancelar} setOpen={setOpenAlertaCancelar} text='la baja de huesped'/>

            </>
        );
    } else if(controlador === '0'){
        return(
        <>
            <Encabezado titulo='Dar de Baja Huesped'/>

        <p>Los datos del huesped {huesped.nombre + ' ' + huesped.apellido + ', ' + huesped.tipoDoc + ' ' + huesped.nroDoc} han sido eliminados del sistema</p>
        <p>Presione cualquier tecla para continuar</p>
        </>
    );
    } else if(controlador === '2'){
        return(
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>

                <p>Error: El huesped {huesped.nombre + ' ' + huesped.apellido + ', ' + huesped.tipoDoc + ' ' + huesped.nroDoc} no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad</p>
                <p>Presione cualquier tecla para continuar</p>
            </>
        );
    }


}