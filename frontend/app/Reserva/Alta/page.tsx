'use client';
import {useState} from 'react'
import {useForm} from 'react-hook-form'
import {useRouter} from 'next/navigation'
import Encabezado from '../../Encabezado.tsx'
import {tiposTablaHabitacion, infoDisponibilidad, validation} from '../../../public/constants.ts'
import BuscarHabitacion from '../../Habitacion/Buscar/page.tsx'
import '../../Huesped/Alta/AltaHuesped.css'
import '../../Huesped/Buscar/Buscar.css'
import Row from "@/app/Row.tsx";
import { AlertaCancelar } from "@/app/Alertas.tsx";
import { ScrollArea } from "@base-ui-components/react";
import Campo from "@/app/Campo.tsx";
import { useFetch } from '@/hooks/useFetch'

export default function ReservarHabitacion() {
    const { register, formState, handleSubmit } = useForm();
    const router = useRouter();
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ errorNoSeleccionado, setErrorNoSeleccionado] = useState(false);
    const [ aceptar, setAceptar] = useState(false);
    const [seleccionadas, setSeleccionadas] = useState(new Map<string, Array<Array<Date>>>());
    const [ solicitudValida, setSolicitudValida] = useState(false);
    const fetchApi = useFetch();
    const [ reservas, setReservas] = useState([
            {
                nroHabitacion : "5",
                tipoHabitacion : "Individual Estandar",
                fechaInicio : "Jueves 04-12-2025 12:00:00",
                fechaFin : "Martes 09-12-2025 10:00:00",
            },
            {
                nroHabitacion : "12",
                tipoHabitacion : "Doble Estandar",
                fechaInicio : "Jueves 04-12-2025 12:00:00",
                fechaFin : "Martes 09-12-2025 10:00:00",
            },
            ]);

    function onSubmit(data: any) {
        let reservasDTO;
        reservasDTO = reservas.map(reserva => ({
            nroHabitacion: reserva.nroHabitacion,
            fechaInicio: reserva.fechaInicio,
            fechaFin: reserva.fechaFin,
            apellido: data.apellido,
            nombre: data.nombre,
            telefono: data.telefono,
        }));

        console.log(JSON.stringify(reservasDTO))
        fetchApi('/Habitacion/Reservar/', {
           method: 'POST',
           body: JSON.stringify(reservasDTO),
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           }
       }).then(res => {
           if(res?.ok) {
               console.log("EXITO")
           }
       })
        router.push('/Reserva/Alta/Success');
    }

    function onNext(infoDisponibilidad: Array<infoDisponibilidad>) {
        const conflictos: any[] = []
        infoDisponibilidad.forEach(info => {
            info.reservas.forEach(reserva => {
                if(seleccionadas.get(info.habitacion.nroHabitacion)?.[0].length ?? 0 > 0) {
                    seleccionadas.get(info.habitacion.nroHabitacion)?.forEach(seleccion => {
                        const reservaInicio = new Date(reserva.fechaInicio);
                        const reservaFin = new Date(reserva.fechaFin);
                        const seleccionInicio = new Date(seleccion[0].getTime()-24*3600000);
                        const seleccionFin = new Date(seleccion[1].getTime()-24*3600000)
                        if (
                            (reservaInicio <= seleccionInicio && reservaFin >= seleccionFin) ||
                            (reservaInicio >= seleccionInicio && (reservaInicio <= seleccionFin
                                || reservaInicio.toLocaleDateString('en-GB') == seleccionFin.toLocaleDateString('en-GB'))) ||
                            (reservaFin >= seleccionInicio && reservaFin <= seleccionFin) || (
                                seleccionInicio.toLocaleDateString('en-GB') === seleccionFin.toLocaleDateString('en-GB') && seleccionInicio.toLocaleDateString('en-GB') === reservaInicio.toLocaleDateString('en-GB')
                            )
                        ) {
                            conflictos.push({
                                reserva,
                                seleccion,
                                habitacion: info.habitacion.nroHabitacion
                            });
                        }
                    });
                }
            });
        });
        if(conflictos.length > 0){
            console.log("MOSTRAR POPUP NO SE PUEDE PORQUE CASILLAS RESERVADAS")
        }else{
            const res: any[] = []
            seleccionadas.forEach((value, key) =>{
                if(value[0].length > 0){
                    value.forEach((val) => {
                        let fechaInicioVal = val[0];
                        let fechaFinVal = val[1];
                        fechaInicioVal.setTime(fechaInicioVal.getTime()+11*3600000);
                        fechaFinVal.setTime(fechaFinVal.getTime()+9*3600000);
                        res.push({
                            nroHabitacion: key,
                            tipoHabitacion: infoDisponibilidad.find(d => d.habitacion.nroHabitacion === key)?.habitacion.tipo,
                            fechaInicio: fechaInicioVal,
                            fechaFin: fechaFinVal,
                        })
                    })
                }
            })
            if(res.length > 0) {
                setReservas(res)
                setSolicitudValida(true)
            }
        }
    }

    return(

        <>
            <Encabezado titulo="Reservar Habitación"/>
            {
                !solicitudValida
                ?(
                        <>
                            <BuscarHabitacion tipo={tiposTablaHabitacion.CU04} seleccionadas={seleccionadas}
                                              setSeleccionadas={setSeleccionadas} onNext={onNext}/>
                        </>
                    )
                :(
                        <>
                            <h3 style={{textAlign: 'center'}}>Se van a reservar las siguientes habitaciones:</h3>

                            <div style={{
                                display: 'flex',
                                flexDirection: 'column',
                                width: 'fit-content',
                                marginLeft: 'auto',
                                marginRight: 'auto'
                            }}>
                                <ScrollArea.Root className='ScrollArea'>
                                    <ScrollArea.Viewport className='Viewport'>
                                        <ScrollArea.Content className='Content'>
                                            <table>
                                                <thead style={{position: 'sticky', top: '0'}}>
                                                <tr>
                                                    <th>N° Habitación</th>
                                                    <th>Tipo de Habitación</th>
                                                    <th>Fecha de Ingreso</th>
                                                    <th>Fecha de Egreso</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {reservas.map((reserva: any) => (
                                                    <tr key={reserva.nroHabitacion + reserva.fechaInicio.toString() + reserva.fechaFin.toString()}>
                                                        <td>{reserva.nroHabitacion}</td>
                                                        <td>{reserva.tipoHabitacion}</td>
                                                        <td>{`${reserva.fechaInicio.toLocaleDateString('es-ES', {
                                                            weekday: 'long',
                                                            day: '2-digit',
                                                            month: '2-digit',
                                                            year: 'numeric',
                                                        })} ${reserva.fechaInicio.toLocaleTimeString('es-ES', {
                                                            hour: '2-digit',
                                                            minute: '2-digit',
                                                            second: '2-digit',
                                                            hour12: false,
                                                        })}`}</td>
                                                        <td>{`${new Date(reserva.fechaFin.getTime()+24*3600000).toLocaleDateString('es-ES', {
                                                            weekday: 'long',
                                                            day: '2-digit',
                                                            month: '2-digit',
                                                            year: 'numeric',
                                                        })} ${reserva.fechaFin.toLocaleTimeString('es-ES', {
                                                            hour: '2-digit',
                                                            minute: '2-digit',
                                                            second: '2-digit',
                                                            hour12: false,
                                                        })}`}</td>
                                                    </tr>
                                                ))}
                                                </tbody>
                                            </table>

                                        </ScrollArea.Content>
                                    </ScrollArea.Viewport>
                                    <ScrollArea.Scrollbar className='Scrollbar'>
                                        <ScrollArea.Thumb className='Thumb'/>
                                    </ScrollArea.Scrollbar>
                                </ScrollArea.Root>
                            </div>
                            {
                                (!aceptar) ? <Row>
                                        <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Rechazar
                                        </button>
                                        <button type='button' className='Button' onClick={() => setAceptar(true)}>Aceptar</button>
                                    </Row> :
                                    <>
                                        <Row>
                                            <button className='Button Disabled'>Rechazar</button>
                                            <button className='Button Disabled'>Aceptar</button>
                                        </Row>
                                        <h3 style={{textAlign: 'center'}}>Ingrese datos del Huesped</h3>
                                        <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                                            <Row>
                                                <Campo field="Nombre" register={register} errors={errors}
                                                       validation={validation['nombre']} placeholder="ej. JUAN" isRequired={true}/>
                                                <Campo field="Apellido" register={register} errors={errors}
                                                       validation={validation['apellido']} placeholder="ej. MARTINEZ"
                                                       isRequired={true}/>
                                                <Campo field="Telefono" register={register} errors={errors}
                                                       validation={validation['numeroTelefono']} placeholder='+54 9 xxxx xxxx'
                                                       isRequired={true}/>
                                            </Row>
                                            <Row>
                                                <button type='submit' className='Button'>Reservar</button>
                                            </Row>
                                        </form>
                                    </>
                            }


                            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen}
                                            text='la busqueda de huespedes'/>
                        </>
                )
            }



        </>
    );
}