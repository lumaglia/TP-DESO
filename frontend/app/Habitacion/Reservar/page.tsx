'use client';

import TablaReserva from './TablaReserva.tsx'
import './ReservaHabitacion.css'


export default function ReservarHabitacion() {
    const array = [...Array(10).keys()]
    return (
        <>
            <TablaReserva/>
        </>
    )
}