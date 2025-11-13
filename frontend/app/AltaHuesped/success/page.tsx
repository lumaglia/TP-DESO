'use client';

import { useRouter, useSearchParams } from 'next/navigation'
import '../AltaHuesped.css'

export default function successPage() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const huesped = searchParams.get('huesped');
    return(
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <h3 style={{textAlign:'center'}}>Dar Alta de Huésped</h3>
            <h2>El huesped {huesped} ha sido satisfactoriamente cargado al sistema</h2>
            <h3>¿Desea cargar otro?</h3>
            <div>
                <button className='Button' onClick={() => router.push('/')}>No</button>
                <button className='Button' onClick={() => router.push('/AltaHuesped')}>Si</button>
            </div>
        </div>
    )
}