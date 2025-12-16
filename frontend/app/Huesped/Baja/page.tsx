'use client';
import { AlertaCancelar } from '../../Alertas.tsx'
import Encabezado from '../../Encabezado';
import '../Alta/AltaHuesped.css'
import { useState } from 'react'
import { useSearchParams, useRouter } from 'next/navigation';

export default function BajaHuesped() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const tipoDoc = searchParams.get('tipo');
    const nroDoc = searchParams.get('nro');
    const nombre = searchParams.get('nombre');
    const apellido = searchParams.get('apellido');

    const [openAlertaCancelar, setOpenAlertaCancelar] = useState(false);
    const [controlador, setControlador] = useState('1');
    const [mensajeError, setMensajeError] = useState('');
    const confirmarEliminacion = async () => {
        const datosBorrar = {
            tipoDoc: tipoDoc,
            nroDoc: nroDoc
        };

        try {
            const res = await fetch('http://localhost:8081/Huesped/Baja', {
                method: 'DELETE',
                body: JSON.stringify(datosBorrar),
                headers: { 'Content-Type': 'application/json' }
            });

            if (res.ok) {
                setControlador('0');
            } else {
                const textoError = await res.text();
                setMensajeError(textoError);
                setControlador('2');
            }
        } catch (error) {
            setMensajeError("Error de conexión con el servidor.");
            setControlador('2');
        }
    };

    if(controlador === '1') {
        return (
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>
                <div style={{textAlign: 'center', marginTop: '50px'}}>
                    <p style={{fontSize: '1.2rem'}}>
                        Los datos del huesped <strong>{nombre} {apellido}</strong>,
                        DNI <strong>{nroDoc}</strong> serán ELIMINADOS, ¿desea continuar?
                    </p>

                    <div style={{marginTop: '30px'}}>

                        <button className='Button'
                                style={{backgroundColor: 'red', color: 'white', marginRight: '20px'}}
                                onClick={confirmarEliminacion}>
                            Eliminar
                        </button>

                        <button className='Button' onClick={() => setOpenAlertaCancelar(true)}>
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
                    <p>Los datos del huesped <strong>{nombre} {apellido}</strong> han sido eliminados del sistema.</p>
                    <p style={{marginTop: '20px', color: 'gray'}}>Presione cualquier tecla para continuar</p>

                    <button className='Button' style={{marginTop:'20px'}} onClick={() => router.push('/')}>
                        Volver al Inicio
                    </button>
                </div>
            </>
        );
    }

    else if(controlador === '2'){
        return(
            <>
                <Encabezado titulo='Dar de Baja Huesped'/>
                <div style={{textAlign: 'center', marginTop: '50px', color: 'red'}}>

                    <p style={{fontWeight: 'bold', fontSize: '1.2rem'}}>
                        Error: El huesped {nombre} {apellido}, {tipoDoc} {nroDoc}
                        no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad.
                    </p>

                    <button className='Button' style={{marginTop:'30px'}} onClick={() => router.back()}>
                        Volver
                    </button>
                </div>
            </>
        );
    }
}