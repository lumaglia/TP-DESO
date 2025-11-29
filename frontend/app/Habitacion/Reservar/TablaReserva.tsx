'use client';

import { useState } from 'react';
import TableButton from './TableButton';
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import './ReservaHabitacion.css'
import '../../Huesped/Buscar/Buscar.css'

export default function TablaReserva() {
    const habitaciones = [...Array(50).keys()]

    const d1 = new Date('2022-01-18');
    const d2 = new Date('2022-02-24');
    const date = new Date(d1.getTime());

    const dates = [];

    while (date <= d2) {
        dates.push(new Date(date));
        date.setDate(date.getDate() + 1);
    }


    const [seleccionadas, setSeleccionadas] = useState(new Map<number, Array<Array<Date>>>());

    function getKey(key: number, map: Map<number, Array<Array<Date>>>) {
        return map.get(key) || [[]];
    }

    function parseDate(date: string) {
        const [day, month, year] = date.split('/');
        return new Date(Number(year), Number(month) - 1, Number(day));
    }

    console.log(seleccionadas);
    function handleClick(date: Date, habitacion: number) {

        setSeleccionadas((seleccionadas: Map<number, Array<Array<Date>>>) => {
            const arr = getKey(habitacion, seleccionadas)
            const newMap = new Map<number, Array<Array<Date>>>(seleccionadas);
            const index = arr.findIndex(x => x[0]?.getTime() == date.getTime() || x[1]?.getTime() == date.getTime());
            console.log('size', arr[arr.length - 1].length);
            if (index > -1) {
                if(arr.filter(selection => selection.length == 1).length < 1 || arr[index].length == 1) {
                    if(arr[index][0].getTime() === date.getTime() && arr[index][1]){
                        console.log("ENTRO 1");
                        return newMap.set(habitacion, [...arr.slice(0, index), [arr[index][1]], ...arr.slice(index + 1)])
                    }else if(arr[index][1]?.getTime() === date.getTime()){
                        console.log("ENTRO 2");
                        return newMap.set(habitacion, [...arr.slice(0, index), [arr[index][0]], ...arr.slice(index + 1)])
                    }else if(arr.length > 1){
                        console.log("ENTRO 3");
                        return newMap.set(habitacion, [...arr.slice(0, index), ...arr.slice(index + 1)])
                    }else return newMap.set(habitacion, [[]])
                }else{
                    console.log("ENTRO 4");
                    return seleccionadas
                }

            }else{
                console.log("ENTRO AÑADIR");
                console.log(arr[arr.length - 1][0], arr[arr.length - 1][1])
                if(!arr.some(selection => selection[0] <= date && selection[1] >= date
                    || arr[arr.length - 1].length == 1 && (
                        selection[0] > date && selection[0] < arr[arr.length - 1][0]
                    || selection[1] > date && selection[1] < arr[arr.length - 1][0]
                    || selection[0] > date && selection[1] < arr[arr.length - 1][0]
                    || selection[1] < date && selection[1] > arr[arr.length - 1][0]))){
                    if(arr[arr.length - 1].length == 2) {
                        return newMap.set(habitacion, arr.concat([[date]]))
                    }else if(arr[arr.length - 1].length == 1){
                        if(arr[arr.length - 1][0] > date){
                            return newMap.set(habitacion, [...arr.slice(0, -1), [date, arr[arr.length - 1][0]]]);
                        }else {
                            return newMap.set(habitacion, [...arr.slice(0, -1), [arr[arr.length - 1][0], date]]);
                        }
                    }else if(arr[arr.length - 1].length == 0) {
                        return newMap.set(habitacion, [[date]])
                    }else{
                        console.log('ENTRO', arr);
                        return newMap.set(habitacion, arr.concat([date]))
                    }
                }else return seleccionadas
            }
        });
    }


    return (
        <>
            <ScrollArea.Root className='ScrollArea'>
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
                                {habitaciones.map((h, i) => <td key={date.toLocaleDateString("en-GB") + i}>
                                    <TableButton date={date} habitacion={h}
                                                 onClick={handleClick} seleccionado={getKey(h, seleccionadas).some((arr: Array<Date>) =>
                                        arr[0]?.getTime() == date.getTime() || (arr[0] <= date && arr[1] >= date))}
                                                 start={getKey(h, seleccionadas).some((arr: Array<Date>) => arr[0]?.getTime() == date.getTime())}
                                                 end={getKey(h, seleccionadas).some((arr: Array<Date>) => arr[1]?.getTime() == date.getTime())} />
                                </td>)}
                            </tr>)}
                            </tbody>
                        </table>

                    </ScrollArea.Content>
                </ScrollArea.Viewport>
                <ScrollArea.Scrollbar className='Scrollbar'>
                    <ScrollArea.Thumb className='Thumb'/>
                </ScrollArea.Scrollbar>
            </ScrollArea.Root>

        </>
    )
}