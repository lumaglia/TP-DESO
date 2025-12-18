'use client';

import Link from 'next/link'
import Encabezado from "./Encabezado";
import Row from './Row'
import './globals.css'

export default function Home() {
    return(
        <>
            <Encabezado titulo='Menu Principal' />
            <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column', marginTop:'30px'}}>
                <Row>
                    <Link href='/Huesped/Alta'>
                        <button className='Button FixedLength'>Dar de alta Huesped</button>
                    </Link>
                    <Link href='/Huesped/Buscar'>
                        <button className='Button FixedLength'>Buscar Huesped</button>
                    </Link>
                </Row>
                <Row>
                    <Link href='/Reserva/Alta'>
                        <button className='Button FixedLength'>Reservar Habitación</button>
                    </Link>
                    <Link href='/Reserva/Baja'>
                        <button className='Button FixedLength'>Cancelar Reserva</button>
                    </Link>
                </Row>
                <Row>
                    <Link href='/Estadia/Alta'>
                        <button className='Button FixedLength'>Ocupar Habitación</button>
                    </Link>
                    <Link href='/Habitacion/Buscar'>
                        <button className='Button FixedLength'>Mostrar Estado Habitaciones</button>
                    </Link>
                </Row>
                <Row>
                    <Link href='/Factura/Crear'>
                        <button className='Button FixedLength'>Facturar Habitación</button>
                    </Link>
                    <Link href='/ResponsablePago/Alta'>
                        <button className='Button FixedLength'>Dar de alta Responsable de Pago</button>
                    </Link>
                </Row>
            </div>
        </>
    );
}
