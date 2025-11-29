'use client';

import './ReservaHabitacion.css'

export default function TableButton( {start = false, end = false, estado = 'disponible', date, habitacion, seleccionado = false, onClick, onRightClick }
                                     : {start?: boolean, end?: boolean, estado?: string, date: Date, habitacion: number, seleccionado?: boolean, onClick: any, onRightClick: any} ) {
    return (
        <div>
            <button className='tableButton' data-estado={estado} data-seleccionado={seleccionado} data-start={start} data-end={end}
                    onClick={() => onClick(date, habitacion)} onContextMenu={(e) => {
                        e.preventDefault(); // Prevent the normal behavior of the click
                        e.stopPropagation(); // Stop the event from propagating
                        onRightClick(date, habitacion);
            }} />
        </div>
    )
}