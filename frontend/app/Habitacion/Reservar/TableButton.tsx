'use client';

import './ReservaHabitacion.css'

export default function TableButton( {start = false, end = false, estado = 'disponible', seleccionado = false, hovered = false}
: {start?: boolean, end?: boolean, estado?: string, seleccionado?: boolean, hovered?: boolean} ) {
    return (
        <div>
            <button className='tableButton' data-estado={estado} data-seleccionado={seleccionado} data-hovered={hovered}
                    data-start={start} data-end={end} />
        </div>
    )
}