'use client';
import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { useRouter } from 'next/navigation'
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import Campo from '../../Campo.tsx'
import Row from '../../Row'
import Encabezado from '../../Encabezado.tsx'
import { AlertaCancelar } from '../../Alertas.tsx'
import { validation, comboValues, FormValues, fieldTypes } from '../../../public/constants.ts'
import '../../Huesped/Alta/AltaHuesped.css'
import '../../Huesped/Buscar/Buscar.css'

export default function ReservarHabitacion() {
    const { register, formState, handleSubmit } = useForm();
    const router = useRouter();
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ errorNoSeleccionado, setErrorNoSeleccionado] = useState(false);
    const [ aceptar, setAceptar] = useState(false);
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

        /*fetch('http://localhost:8081/Huesped/Buscar', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.status === 204) {
                setHuespedes([])
                setSelectedHuesped(null)
                setAlertaHuespedNoEncontradoOpen(true)
            }else if(res.ok) {
                res.json().then(data => {
                    setHuespedes(data)
                    setSelectedHuesped(null)
                })
            }
        })*/
        router.push('/Reserva/Alta/Success');
    }
    return(
        <>
            <Encabezado titulo="Reservar Habitación" />
            <h3 style={{textAlign:'center'}}>Se van a reservar las siguientes habitaciones:</h3>

            <div style={{display: 'flex', flexDirection: 'column', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>
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
                                    <tr>
                                        <td>{reserva.nroHabitacion}</td>
                                        <td>{reserva.tipoHabitacion}</td>
                                        <td>{reserva.fechaInicio}</td>
                                        <td>{reserva.fechaFin}</td>
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
                (!aceptar)?<Row>
                    <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Rechazar</button>
                    <button type='button' className='Button' onClick={() => setAceptar(true)}>Aceptar</button>
                </Row>:
                    <>
                        <Row>
                        <button className='Button Disabled'>Rechazar</button>
                        <button className='Button Disabled'>Aceptar</button>
                    </Row>
                        <h3 style={{textAlign:'center'}}>Ingrese datos del Huesped</h3>
                        <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                            <Row>
                                <Campo field="Nombre" register={register} errors={errors} validation={validation['nombre']} placeholder="ej. JUAN" isRequired={true}/>
                                <Campo field="Apellido" register={register} errors={errors} validation={validation['apellido']} placeholder="ej. MARTINEZ" isRequired={true}/>
                                <Campo field="Telefono" register={register} errors={errors} validation={validation['numeroTelefono']} placeholder='+54 9 xxxx xxxx' isRequired={true}/>
                            </Row>
                            <Row>
                                <button type='submit' className='Button'>Reservar</button>
                            </Row>
                        </form>
                    </>
            }



            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la busqueda de huespedes'/>

        </>
    );
}