'use client';
import { AlertaCancelar } from '../../Alertas.tsx'
import Encabezado from '../../Encabezado';
import '../Alta/AltaHuesped.css'
import { useSearchParams, useRouter } from 'next/navigation';
import { useState, useEffect } from 'react'
import { useFetch } from '@/hooks/useFetch'

export default function BajaHuesped() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const tipoDoc = searchParams.get('tipo')|| '';
    const nroDoc = searchParams.get('nro')|| '';
    const nombre = searchParams.get('nombre')|| '';
    const apellido = searchParams.get('apellido')|| '';

    const [openAlertaCancelar, setOpenAlertaCancelar] = useState(false);
    const [controlador, setControlador] = useState('1');
    const [mensajeError, setMensajeError] = useState('');
    const fetchApi = useFetch();
    const confirmarEliminacion = async () => {
        const datosBorrar = {
            tipoDoc: tipoDoc,
            nroDoc: nroDoc
        };

        try {
            const res = await fetchApi('/Huesped/Baja', {
                method: 'DELETE',
                body: JSON.stringify(datosBorrar),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });

            if (res?.ok) {
                setControlador('0');
            } else {
                const textoError = await res?.text();
                if(textoError)setMensajeError(textoError);
                setControlador('2');
            }
        } catch (error) {
            setMensajeError("Error de conexión con el servidor.");
            setControlador('2');
        }
    };

    useEffect(() => {
        if (controlador === '0' || controlador === '2') {
            const handleKeyPress = () => {
                router.push('/');
            };
            window.addEventListener('keydown', handleKeyPress);
            return () => window.removeEventListener('keydown', handleKeyPress);
        }
    }, [controlador, router]);

    if(controlador === '1') {
        return (
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>
                <div style={{textAlign: 'center', marginTop: '50px'}}>
                    <h3 style={{fontSize: '1.2rem'}}>
                        Los datos del huesped {nombre} {apellido}, DNI {nroDoc} serán ELIMINADOS, ¿desea continuar?
                    </h3>

                    <div style={{marginTop: '30px'}}>

                        <button className='Button Cancelar'
                                style={{marginRight: '20px'}}
                                onClick={confirmarEliminacion}>
                            Eliminar
                        </button>

                        <button className='Button' onClick={() => router.push('/')}>
                            Cancelar
                        </button>
                    </div>
                </div>
                <AlertaCancelar open={openAlertaCancelar} setOpen={setOpenAlertaCancelar} text='la baja de huesped'/>
            </>
        );
    }
    else if(controlador === '0'){
        return(
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>
                <div style={{textAlign: 'center', marginTop: '50px'}}>
                    <h3>Los datos del huesped {nombre} {apellido} {tipoDoc} {nroDoc} han sido eliminados del sistema.</h3>
                    <p style={{textAlign: 'center', marginTop: '20px', color: 'gray'}}>Presione cualquier tecla para continuar</p>

                </div>
            </>
        );
    }

    else if(controlador === '2'){
        return(
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>
                <div style={{textAlign: 'center', marginTop: '50px', color: 'red'}}>

                    <p style={{textAlign: 'center', fontWeight: 'bold', fontSize: '1.2rem'}}>
                        Error: El huesped {nombre} {apellido}, {tipoDoc} {nroDoc} no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad.
                    </p>
                    <p style={{textAlign: 'center', marginTop: '20px', color: 'gray'}}>Presione cualquier tecla para continuar</p>
                </div>
            </>
        );
    }
}