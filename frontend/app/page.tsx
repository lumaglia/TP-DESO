'use client';

import Link from 'next/link'
import Encabezado from "./Encabezado";

export default function Home() {
    return(
        <>
            <Encabezado titulo='Menu Principal' />
            <Link href='/AltaHuesped'>
                <button>Dar de alta Huesped</button>
            </Link>
        </>
    );
}
