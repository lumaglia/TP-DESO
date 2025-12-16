'use client';

import { useRouter, useSearchParams } from 'next/navigation'
import '../../Alta/AltaHuesped.css'
import Encabezado from "../../../Encabezado";
import Row from '../../../Row.tsx'

export default function successPage() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const huesped = searchParams.get('huesped');

    return(
       <>
           <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
        <Encabezado titulo='Modificar Huésped'/>
        <h2> La modificacion del huesped {huesped} ha sido satisfactoriamente registrada </h2>
               </div>
           <Row>
               <button type='button' className='Button' onClick={() => router.push('/')}>Volver al menu</button>
           </Row>
       </>
    );
}
