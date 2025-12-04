'use client';

import { useRouter, useSearchParams } from 'next/navigation'
import '../../../globals.css'
import '../../../Huesped/Alta/AltaHuesped.css'
import Encabezado from "../../../Encabezado";

export default function successPage() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const huesped = searchParams.get('huesped');
    return(
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <Encabezado titulo='Reservar Habitación' />
            <h2>Las reservas han sido cargadas al sistema exitosamente</h2>
            <h3>¿Desea cargar otra?</h3>
            <div>
                <button className='Button' onClick={() => router.push('/')}>No</button>
                <button className='Button' onClick={() => router.push('/Reserva/Alta')}>Si</button>
            </div>
        </div>
    )
}