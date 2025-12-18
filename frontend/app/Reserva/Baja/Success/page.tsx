'use client';

import { useRouter } from 'next/navigation'
import '../../../globals.css'
import Encabezado from "../../../Encabezado";

export default function successPage() {
    const router = useRouter();
    return(
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <Encabezado titulo='Reservar Habitación' />
            <h2>Las reservas han sido canceladas exitosamente</h2>
            <h3>¿Desea cancelar otra?</h3>
            <div>
                <button className='Button' onClick={() => router.push('/')}>No</button>
                <button className='Button' onClick={() => router.push('/Reserva/Baja')}>Si</button>
            </div>
        </div>
    )
}