'use client';


import '../../Alta/AltaHuesped.css'
import Encabezado from "../../../Encabezado";

export default function successPage() {
    const huesped = {
        'nombre':'JUAN',
        'apellido': 'MARTINEZ',

    }
    return(
       <>
           <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
        <Encabezado titulo='Modificar Huésped'/>
        <h2> La modificacion del huesped {huesped.nombre + ' ' + huesped.apellido} ha sido satisfactoriamente registrada </h2>
               </div>
       </>
    );
}
