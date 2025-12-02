'use client';

import './ReservaHabitacion.css'

export default function TableButton( {start = false, end = false, estado = 'disponible', date, habitacion,
                                         seleccionado = false, hovered = false, onClick, onRightClick, onMouseEnter, onMouseLeave }
                                     : {start?: boolean, end?: boolean, estado?: string, date: Date, habitacion: number,
                                        seleccionado?: boolean, hovered?: boolean, onClick: any, onRightClick: any, onMouseEnter: any, onMouseLeave: any} ) {
    return (
        <div>
            <button className='tableButton' data-estado={estado} data-seleccionado={seleccionado} data-hovered={hovered}
                    data-start={start} data-end={end}/>
        </div>
    )
}