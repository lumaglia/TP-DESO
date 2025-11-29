'use client';

import './ReservaHabitacion.css'

export default function TableButton( {start = false, end = false, estado = 'disponible', date, habitacion, seleccionado = false, onClick}
                                     : {start?: boolean, end?: boolean, estado?: string, date: Date, habitacion: number, seleccionado?: boolean, onClick: any} ) {
    return (
        <div>
            <button className='tableButton' data-estado={estado} data-seleccionado={seleccionado} data-start={start} data-end={end} onClick={() => onClick(date, habitacion)} />
        </div>
    )
}