'use client';

import { useRouter, useSearchParams } from 'next/navigation'
import '../../../globals.css'
import '../AltaHuesped.css'
import Encabezado from "../../../Encabezado";

export default function successPage() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const huesped = searchParams.get('huesped');
    return(
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <Encabezado titulo='Dar Alta de Huésped' />
            <h2>El huesped {huesped} ha sido satisfactoriamente cargado al sistema</h2>
            <h3>¿Desea cargar otro?</h3>
            <div>
                <button className='Button' onClick={() => router.push('/')}>No</button>
                <button className='Button' onClick={() => router.push('/Huesped/Alta')}>Si</button>
            </div>
        </div>
    )
}