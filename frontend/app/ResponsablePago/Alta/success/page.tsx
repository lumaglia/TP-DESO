'use client'

import { useEffect } from 'react'
import Encabezado from "../../../Encabezado";
import { useRouter, useSearchParams } from 'next/navigation'

export default function SuccessPage() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const responsablePago = searchParams.get('responsablePago');
    const returnTo = searchParams.get('returnTo') ?? '/'

    useEffect(() => {
        const timer = setTimeout(() => {
            router.push(returnTo);
        }, 4000);

        return () => clearTimeout(timer);
    }, [router, returnTo]);

    return (
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <Encabezado titulo='Dar Alta de Responsable de pago' />
            <h2>
                La firma {responsablePago} ha sido satisfactoriamente cargada al sistema.
            </h2>
        </div>
    );
}
