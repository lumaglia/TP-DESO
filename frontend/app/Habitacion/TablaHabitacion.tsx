'use client';

import { useState } from 'react';
import TableButton from './TableButton';
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import './TablaHabitacion.css'
import '../Huesped/Buscar/Buscar.css'
import { tiposTablaHabitacion } from '../../public/constants'
import Row from "../Row";

export function TablaHabitacion({tipo=tiposTablaHabitacion.CU05, infoDisponibilidad, fechaInicio, fechaFin,
                                    seleccionadas= new Map<number, Array<Array<Date>>>(), setSeleccionadas=()=>{}}
                                : {tipo?: tiposTablaHabitacion, infoDisponibilidad: Array<Object>, fechaInicio: Date, fechaFin: Date,
                                    seleccionadas?: Map<number, Array<Array<Date>>>, setSeleccionadas?: Function}) {

    const habitaciones = [...Array(50).keys()]

    const dates = [];
    const date = new Date(fechaInicio)
    while (date <= fechaFin) {
        dates.push(new Date(date));
        date.setDate(date.getDate() + 1);
    }

    // Lo dejo comentado para poder copiarlo facil en loscodigos del CU04 y CU15
    // const [seleccionadas, setSeleccionadas] = useState(new Map<number, Array<Array<Date>>>());
    const [indiceSeleccionActual, setIndiceSeleccionActual] = useState<number | null>(null);
    const [hovered, setHovered] = useState<Date | null>(null);

    function getKey(key: number, map: Map<number, Array<Array<Date>>>) {
        return map.get(key) || [[]];
    }

    function parseDate(date: string) {
        const [day, month, year] = date.split('/');
        return new Date(Number(year), Number(month) - 1, Number(day));
    }

    function handleClick(date: Date, habitacion: number) {
        if(!indiceSeleccionActual || indiceSeleccionActual === habitacion){
            setSeleccionadas((seleccionadas: Map<number, Array<Array<Date>>>) => {
                const arr = getKey(habitacion, seleccionadas)
                const newMap = new Map<number, Array<Array<Date>>>(seleccionadas);
                const index = arr.findIndex(x =>
                    x.length === 1);
                if (index > -1) {
                    if(!arr.some(selection => selection[0] <= date && selection[1] >= date
                        || selection[0] > date && selection[0] < arr[index][0]
                        || selection[1] > date && selection[1] < arr[index][0]
                        || selection[0] > date && selection[1] < arr[index][0]
                        || selection[1] < date && selection[1] > arr[index][0])){
                        if(indiceSeleccionActual) {
                            setIndiceSeleccionActual(null)
                        }else setIndiceSeleccionActual(habitacion)
                        if(arr[index][0] > date){
                            return newMap.set(habitacion, [...arr.slice(0, index), [date, arr[index][0]], ...arr.slice(index + 1)]);
                        }else {
                            return newMap.set(habitacion, [...arr.slice(0, index), [arr[index][0], date], ...arr.slice(index + 1)]);
                        }
                    }else return seleccionadas;
                }else {
                    const index = arr.findIndex(x => x[0]?.getTime() == date.getTime() || x[1]?.getTime() == date.getTime());
                    if(index > -1) {
                        if(arr[index][0].getTime() === date.getTime() && arr[index][1]){
                            setIndiceSeleccionActual(habitacion)
                            return newMap.set(habitacion, [...arr.slice(0, index), [arr[index][1]], ...arr.slice(index + 1)])
                        }else if(arr[index][1]?.getTime() === date.getTime()){
                            setIndiceSeleccionActual(habitacion)
                            return newMap.set(habitacion, [...arr.slice(0, index), [arr[index][0]], ...arr.slice(index + 1)])
                        }else return seleccionadas;
                    }else if(!arr.some(selection => selection[0] <= date && selection[1] >= date
                        || arr[arr.length - 1].length == 1 && (
                            selection[0] > date && selection[0] < arr[arr.length - 1][0]
                            || selection[1] > date && selection[1] < arr[arr.length - 1][0]
                            || selection[0] > date && selection[1] < arr[arr.length - 1][0]
                            || selection[1] < date && selection[1] > arr[arr.length - 1][0]))) {
                        if(indiceSeleccionActual) {
                            setIndiceSeleccionActual(null)
                        }else setIndiceSeleccionActual(habitacion)
                        if (arr[arr.length - 1].length == 2) {
                            return newMap.set(habitacion, arr.concat([[date]]))
                        } else if (arr[arr.length - 1].length == 1) {
                            if (arr[arr.length - 1][0] > date) {
                                return newMap.set(habitacion, [...arr.slice(0, -1), [date, arr[arr.length - 1][0]]]);
                            } else {
                                return newMap.set(habitacion, [...arr.slice(0, -1), [arr[arr.length - 1][0], date]]);
                            }
                        } else {
                            return newMap.set(habitacion, [...arr.slice(0, -1), [date]])
                        }
                    }else return seleccionadas;
                }
            });
        }
    }

    function handleRightClick(date: Date, habitacion: number) {
        setSeleccionadas((seleccionadas: Map<number, Array<Array<Date>>>) => {
            const newMap = new Map<number, Array<Array<Date>>>(seleccionadas);

            if(indiceSeleccionActual === null){
                const arr = getKey(habitacion, seleccionadas)
                const index = arr.findIndex(x => x[0]?.getTime() <= date.getTime() && (!x[1] || x[1]?.getTime() >= date.getTime()))
                if (index > -1) {
                    setIndiceSeleccionActual(null)
                    if(arr.length > 1) {
                        return newMap.set(habitacion, [...arr.slice(0, index), ...arr.slice(index + 1)])
                    }else{
                        return newMap.set(habitacion, [[]])
                    }

                }else return seleccionadas
            }else {
                habitacion = indiceSeleccionActual
                const arr = getKey(habitacion, seleccionadas)
                const index = arr.findIndex(x => x.length === 1)
                setIndiceSeleccionActual(null)
                if(arr.length > 1){
                    return newMap.set(habitacion, [...arr.slice(0, index), ...arr.slice(index + 1)])
                }else return newMap.set(habitacion, [[]])
            }
        });
    }
    function handleMouseEnter(date: Date, habitacion: number) {
        if(habitacion === indiceSeleccionActual) {
            setHovered(date);
        }
    }

    function handleMouseLeave() {
        setHovered(null);
    }


    return (
        <>
            <Row>
            <ScrollArea.Root className='ScrollArea TablaHabitacion'>
                <ScrollArea.Viewport className='Viewport'>
                    <ScrollArea.Content className='Content'>
                        <table>
                            <thead>
                            <tr>
                                <th></th>
                                {habitaciones.map((habitacion) => <th key={habitacion}>{habitacion}</th>)}
                            </tr>
                            </thead>
                            <tbody>
                            {dates.map((date, i) => <tr key={date.toLocaleDateString("en-GB")}>
                                <th>{date.toLocaleDateString("en-GB")}</th>
                                {habitaciones.map((h, i) => <td key={date.toLocaleDateString("en-GB") + i}
                                                                className={tipo === tiposTablaHabitacion.CU04
                                                                    ? (indiceSeleccionActual === null || h === indiceSeleccionActual)
                                                                        ? ''
                                                                        : 'invalido'
                                                                    : 'noSeleccionable'}
                                                                onMouseEnter={tipo === tiposTablaHabitacion.CU04
                                                                    ? () => handleMouseEnter(date, h)
                                                                    : undefined}
                                                                onMouseLeave={tipo === tiposTablaHabitacion.CU04
                                                                    ? () => handleMouseLeave()
                                                                    : undefined}
                                                                onClick={tipo === tiposTablaHabitacion.CU04
                                                                    ? () => handleClick(date, h)
                                                                    : undefined}
                                                                onContextMenu={tipo === tiposTablaHabitacion.CU04
                                                                    ? (e) => {
                                                                    e.preventDefault();
                                                                    e.stopPropagation();
                                                                    handleRightClick(date, h)}
                                                                    : undefined}>
                                    <TableButton seleccionado={getKey(h, seleccionadas).some((arr: Array<Date>) =>
                                        arr[0]?.getTime() == date.getTime() || (arr[0] <= date && arr[1] >= date))}
                                                 hovered={h===indiceSeleccionActual
                                                     && hovered !== null
                                                     && ((date > (getKey(h, seleccionadas).find((arr: any) => arr.length === 1)?.[0] ?? hovered)
                                                     && date <= hovered)
                                                     || (date < (getKey(h, seleccionadas).find((arr: any) => arr.length === 1)?.[0] ?? hovered)
                                                     && date >= hovered))}
                                                 start={getKey(h, seleccionadas).some((arr: Array<Date>) => arr[0]?.getTime() == date.getTime())}
                                                 end={getKey(h, seleccionadas).some((arr: Array<Date>) => arr[1]?.getTime() == date.getTime())}
                                                 estado={(date.getDate()*h)%3==0
                                                     ? 'disponible'
                                                     : (date.getDate()*h)%3==1
                                                         ? 'ocupado'
                                                         : 'reservado'} />
                                </td>)}
                            </tr>)}
                            </tbody>
                        </table>

                    </ScrollArea.Content>
                </ScrollArea.Viewport>
                <ScrollArea.Scrollbar className='Scrollbar' orientation='vertical'>
                    <ScrollArea.Thumb className='Thumb'/>
                </ScrollArea.Scrollbar>
                <ScrollArea.Scrollbar className='Scrollbar' orientation="horizontal">
                    <ScrollArea.Thumb className='Thumb' />
                </ScrollArea.Scrollbar>
                <ScrollArea.Corner />
            </ScrollArea.Root>
            </Row>
            <Row>
                <TableButton estado={"ocupado"}/><p> Ocupado </p><TableButton estado={"reservado"}/><p> Reservado </p>
                { tipo == tiposTablaHabitacion.CU05? <></>
                    : <><TableButton start={true} end={true} seleccionado={true}/><p> Selección </p><TableButton start={true} end={true} seleccionado={true} hovered={true}/><p> Selección Invalida </p></>
                }
            </Row>
            {
                tipo == tiposTablaHabitacion.CU05? <></>:<Row>
                    <p>Seleccione o modifique rangos de fecha con Click Izquierdo y elimine selecciones con Click Derecho</p>
                </Row>
            }

        </>
    )
}