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
            <Link href='/Habitacion/Buscar'>
                <button>Mostrar Estado Habitaciones</button>
            </Link>
            <Link href='/Factura/Crear'>
                <button>Crea una nueva factura</button>
            </Link>
        </>
    );
}
