'use client';

import { useState } from 'react';
import TableButton from './TableButton';
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import './TablaHabitacion.css'
import '../Huesped/Buscar/Buscar.css'
import { tiposTablaHabitacion, infoDisponibilidad } from '../../public/constants'
import Row from "../Row";

export function TablaHabitacion({tipo=tiposTablaHabitacion.CU05, infoDisponibilidad, fechaInicio, fechaFin,
                                    seleccionadas= new Map<string, Array<Array<Date>>>(), setSeleccionadas=()=>{}}
                                : {tipo?: tiposTablaHabitacion, infoDisponibilidad: Array<infoDisponibilidad>, fechaInicio: Date, fechaFin: Date,
    seleccionadas?: Map<string, Array<Array<Date>>>, setSeleccionadas?: Function}) {

    const habitaciones = [...infoDisponibilidad.map(e => e.habitacion.nroHabitacion)];

    const dates = [];
    const date = new Date(fechaInicio)
    while (date <= fechaFin) {
        dates.push(new Date(date));
        date.setDate(date.getDate() + 1);
    }

    // Lo dejo comentado para poder copiarlo facil en loscodigos del CU04 y CU15
    // const [seleccionadas, setSeleccionadas] = useState(new Map<string, Array<Array<Date>>>());
    const [indiceSeleccionActual, setIndiceSeleccionActual] = useState<string | null>(null);
    const [hovered, setHovered] = useState<Date | null>(null);

    function getKey(key: string, map: Map<string, Array<Array<Date>>>) {
        return map.get(key) || [[]];
    }

    function handleClick(date: Date, habitacion: string) {
        if(tipo === tiposTablaHabitacion.CU15 && indiceSeleccionActual === null){
            let i = 0
            seleccionadas.forEach((value, key) => {if(value[0].length > 0) i++})
            if (i === 0){
                setSeleccionadas(new Map<string, Array<Array<Date>>>())
            }else if(i > 0){
                return
            }
        }
        console.log(infoDisponibilidad.find(h => h.habitacion.nroHabitacion === indiceSeleccionActual));
        if(!indiceSeleccionActual || indiceSeleccionActual === habitacion){
            setSeleccionadas((seleccionadas: Map<string, Array<Array<Date>>>) => {
                const arr = getKey(habitacion, seleccionadas)
                const newMap = new Map<string, Array<Array<Date>>>(seleccionadas);
                const index = arr.findIndex(x =>
                    x.length === 1);
                if (index > -1) {
                    if(!arr.some(selection => selection[0] <= date && selection[1] >= date
                        || selection[0] > date && selection[0] < arr[index][0]
                        || selection[1] > date && selection[1] < arr[index][0]
                        || selection[0] > date && selection[1] < arr[index][0]
                        || selection[1] < date && selection[1] > arr[index][0]) &&
                        !infoDisponibilidad.find(h => h.habitacion.nroHabitacion === indiceSeleccionActual)
                            ?.estadias.some(reserva => new Date(reserva.fechaInicio) <= date && new Date(reserva.fechaFin) >= date
                            || new Date(reserva.fechaInicio) > date && new Date(reserva.fechaInicio) < arr[index][0]
                            || new Date(reserva.fechaFin) > date && new Date(reserva.fechaFin) < arr[index][0]
                            || new Date(reserva.fechaInicio) > date && new Date(reserva.fechaFin) < arr[index][0]
                            || new Date(reserva.fechaFin) < date && new Date(reserva.fechaFin) > arr[index][0])){
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
                            || selection[1] < date && selection[1] > arr[arr.length - 1][0])) &&
                        !infoDisponibilidad.find(h => h.habitacion.nroHabitacion === habitacion)
                            ?.estadias.some(reserva => new Date(reserva.fechaInicio) <= date && new Date(reserva.fechaFin) >= date)) {
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

    function handleRightClick(date: Date, habitacion: string) {
        setSeleccionadas((seleccionadas: Map<string, Array<Array<Date>>>) => {
            const newMap = new Map<string, Array<Array<Date>>>(seleccionadas);

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
    function handleMouseEnter(date: Date, habitacion: string) {
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
                                    {habitaciones.map((habitacion) => <th key={parseInt(habitacion)}>{habitacion}</th>)}
                                </tr>
                                </thead>
                                <tbody>
                                {dates.map((date, i) => <tr key={date.toLocaleDateString("en-GB")}>
                                    <th>{date.toLocaleDateString("en-GB")}</th>
                                    {habitaciones.map((h, i) => <td key={date.toLocaleDateString("en-GB") + i}
                                                                    className={tipo !== tiposTablaHabitacion.CU05
                                                                        ? (indiceSeleccionActual === null || h === indiceSeleccionActual)
                                                                            ? ''
                                                                            : 'invalido'
                                                                        : 'noSeleccionable'}
                                                                    onMouseEnter={tipo !== tiposTablaHabitacion.CU05
                                                                        ? () => handleMouseEnter(date, h)
                                                                        : undefined}
                                                                    onMouseLeave={tipo !== tiposTablaHabitacion.CU05
                                                                        ? () => handleMouseLeave()
                                                                        : undefined}
                                                                    onClick={tipo !== tiposTablaHabitacion.CU05
                                                                        ? () => handleClick(date, h)
                                                                        : undefined}
                                                                    onContextMenu={tipo !== tiposTablaHabitacion.CU05
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
                                                     estado={infoDisponibilidad[parseInt(h)-1]?.estadias.some((arr: {
                                                         fechaInicio: string,
                                                         fechaFin: string,
                                                     }) => new Date(arr.fechaInicio) <= date && new Date(arr.fechaFin) >= date)
                                                         ? 'ocupado'

                                                         : infoDisponibilidad[parseInt(h)-1]?.reservas.some((arr: {
                                                             fechaInicio: string,
                                                             fechaFin: string,
                                                         }) => new Date(arr.fechaInicio) <= date && new Date(arr.fechaFin) >= date)
                                                             ? 'reservado'
                                                             :'disponible'}/>
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
                <TableButton estado={"ocupado"}/><p style={{alignSelf:'flex-end', marginBottom:'2px', marginRight:'4px'}}> Ocupado </p>
                <TableButton estado={"reservado"}/><p style={{alignSelf:'flex-end', marginBottom:'2px', marginRight:'4px'}}> Reservado </p>
                { tipo == tiposTablaHabitacion.CU05? <></>
                    : <><TableButton start={true} end={true} seleccionado={true} estado={'leyenda'}/>
                        <p style={{alignSelf:'flex-end', marginBottom:'2px', marginRight:'4px'}}> Selección </p>
                        <TableButton start={true} end={true} seleccionado={true} hovered={true} estado={'leyenda'}/>
                        <p style={{alignSelf:'flex-end', marginBottom:'2px', marginRight:'4px'}}> Selección Invalida </p></>
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