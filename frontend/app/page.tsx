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
                        <button className='Button'>Dar de alta Huesped</button>
                    </Link>
                    <Link href='/Huesped/Buscar'>
                        <button className='Button'>Buscar Huesped</button>
                    </Link>
                </Row>
                <Row>
                    <Link href='/Estadia/Alta'>
                        <button className='Button'>Ocupar Habitación</button>
                    </Link>
                    <Link href='/Reserva/Alta'>
                        <button className='Button'>Reservar Habitación</button>
                    </Link></Row>
                <Row>
                    <Link href='/Reserva/Baja'>
                        <button className={'Button'}>Cancelar Reserva</button>
                    </Link>
                    <Link href='/Factura/Crear'>
                        <button className='Button'>Facturar Habitación</button>
                    </Link>
                    <Link href='/ResponsablePago/Alta'>
                        <button className='Button'>Dar de alta Responsable de Pago</button>
                    </Link>
                </Row>
            </div>
        </>
    );
}
