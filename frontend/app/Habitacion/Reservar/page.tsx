'use client';

import { TablaHabitacion } from '../TablaHabitacion.tsx'
import { tiposTablaHabitacion } from '../../../public/constants'


export default function ReservarHabitacion() {
    const array = [...Array(10).keys()]
    return (
        <>
            <TablaHabitacion tipo={tiposTablaHabitacion.CU04}/>
        </>
    )
}