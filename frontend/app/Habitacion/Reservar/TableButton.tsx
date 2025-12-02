'use client';

import './ReservaHabitacion.css'

export default function TableButton( {start = false, end = false, estado = 'disponible', date, habitacion,
                                         seleccionado = false, hovered = false, onClick, onRightClick, onMouseEnter, onMouseLeave }
                                     : {start?: boolean, end?: boolean, estado?: string, date: Date, habitacion: number,
                                        seleccionado?: boolean, hovered?: boolean, onClick: any, onRightClick: any, onMouseEnter: any, onMouseLeave: any} ) {
    // if(hovered) console.log(habitacion, date.toLocaleDateString('en-GB'))
    return (
        <div>
            <button className='tableButton' data-estado={estado} data-seleccionado={seleccionado} data-hovered={hovered}
                    data-start={start} data-end={end}
                    onClick={() => onClick(date, habitacion)} onContextMenu={(e) => {
                        e.preventDefault(); // Prevent the normal behavior of the click
                        e.stopPropagation(); // Stop the event from propagating
                        onRightClick(date, habitacion);
            }} onMouseEnter={() => onMouseEnter(date, habitacion)} onMouseLeave={() => onMouseLeave(date, habitacion)} />
        </div>
    )
}