'use client';

import Link from 'next/link'
import Encabezado from "./Encabezado";

export default function Home() {
    return(
        <>
            <Encabezado titulo='Menu Principal' />
            <Link href='/Huesped/Alta'>
                <button>Dar de alta Huesped</button>
            </Link>
            <Link href='/Huesped/Buscar'>
                <button>Buscar Huesped</button>
            </Link>
            <Link href='/Estadia/Alta'>
                <button>Ocupar Habitación</button>
            </Link>
            <Link href='/Reserva/Alta'>
                <button>Reservar Habitación</button>
            </Link>
            <Link href='/Reserva/Baja'>
                <button>Cancelar Reserva</button>
            </Link>
            <Link href='/Habitacion/Buscar'>
                <button>Mostrar Estado Habitaciones</button>
            </Link>
        </>
    );
}
