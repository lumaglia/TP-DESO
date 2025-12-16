'use client';
import { useState, ChangeEvent } from 'react'
import { useForm } from 'react-hook-form'
import { useRouter } from 'next/navigation'
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import Campo from '../../Campo.tsx'
import Row from '../../Row'
import Encabezado from '../../Encabezado.tsx'
import { AlertaCancelar } from '../../Alertas.tsx'
import '../../globals.css'
import '../../Huesped/Buscar/Buscar.css'
import { useFetch } from '@/hooks/useFetch'

type ReservaValues = {
    nombre: string;
    apellido: string;
    nroHabitacion: string;
    tipoHabitacion: string;
    fechaInicio: string;
    fechaFin: string;
}

export default function CancelarReservas() {
    const { register, formState, handleSubmit } = useForm();
    const { errors } = formState;
    const router = useRouter();
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ alertaReservasNoEncontradasOpen, setAlertaReservasNoEncontradasOpen] = useState(false);
    const [ reservas , setReservas ] = useState<Array<ReservaValues>>([])
    const [ reservasSeleccionadas, setReservasSeleccionadas ] = useState<Array<ReservaValues>>([]);
    const [ errorNoSeleccionado, setErrorNoSeleccionado] = useState(false)
    const fetchApi = useFetch();

    const validation = {
        'nombre': {
            maxLength: {
                value: 100,
                message: 'Máximo 100 caracteres',
            },
            pattern: {
                value: /^[A-ZÁÉÍÓÚ\s]+$/,
                message: 'Ingresar solo letras o espacios'
            },
            validate: {
                notStartingBlank: (value: string) => {
                    return value[0] !== ' ' || 'El nombre debe comenzar con una letra';
                },
                notEndingBlank: (value: string) => {
                    return value.slice(-1) !== ' ' || 'El nombre debe terminar con una letra';
                }
            },
            setValueAs: (value: string) => value.replace(/\s+/g, ' ').toUpperCase(),
            onChange: (e: ChangeEvent<HTMLInputElement>) => {e.target.value = e.target.value.toUpperCase()}
        },
        'apellido': {
            required: 'El apellido es obligatorio',
            maxLength: {
                value: 30,
                message: 'Máximo 30 caracteres',
            },
            pattern: {
                value: /^[A-ZÁÉÍÓÚ]+$/,
                message: 'Ingresar solo letras'
            },
            setValueAs: (value: string) => value.toUpperCase(),
            onChange: (e: ChangeEvent<HTMLInputElement>) => {e.target.value = e.target.value.toUpperCase()}
        }
    }
    function onSubmit(data: any) {
        for (let key in data) {
            if(!data[key]){
                data[key] = null
            }
        }
        setAlertaReservasNoEncontradasOpen(false)
        fetchApi('/Habitacion/Reserva/Buscar', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res?.status === 204) {
                setReservas([])
                setAlertaReservasNoEncontradasOpen(true)
            }else if(res?.ok) {
                res?.json().then(data => {
                    if(data)setReservas(data)
                })
            }
        })
    }
    function cancelarReservas() {
        let status = true;
        Promise.all(reservasSeleccionadas.map(r =>
            fetchApi('/Habitacion/Reserva/Cancelar', {
                method: 'DELETE',
                body: JSON.stringify(r),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(res => {
                if(res?.ok) {
                    console.log("EXITO "+r.nroHabitacion+" "+r.fechaInicio)
                }else{
                    status = false;
                }
            })
        )).finally(() => {
            if(status){
                router.push('/Reserva/Baja/Success');
            }
        })

    }
    return(
        <>
            <Encabezado titulo="Cancelar Reserva" />
            <h3 style={{textAlign:'center'}}>Ingrese datos en los campos por los que desea filtrar</h3>
            <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                <Row>
                    <Campo field="Apellido" register={register} errors={errors} validation={validation['apellido']} isRequired={true} placeholder="ej. MARTINEZ"/>
                    <Campo field="Nombre" register={register} errors={errors} validation={validation['nombre']} placeholder="ej. JUAN"/>
                </Row>
                <Row>
                    <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Cancelar</button>
                    <button type='submit' className='Button'>Buscar</button>
                </Row>
            </form>
            {
                alertaReservasNoEncontradasOpen ? <Row><p>No se han encontrado reservas para los criterios seleccionados.</p></Row>
                    :
                    <></>
            }
            <div style={{display: 'flex', flexDirection: 'column', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>
                <ScrollArea.Root className='ScrollArea'>
                    <ScrollArea.Viewport className='Viewport'>
                        <ScrollArea.Content className='Content'>
                            <table>
                                <thead style={{position: 'sticky', top: '0'}}>
                                <tr>
                                    <th>Apellido</th>
                                    <th>Nombres</th>
                                    <th>N° Hab</th>
                                    <th>Tipo Habitación</th>
                                    <th>Fecha Inicio</th>
                                    <th>Fecha Fin</th>
                                </tr>
                                </thead>
                                <tbody>
                                {reservas.map((reserva: ReservaValues) => (
                                    <tr className={reservasSeleccionadas.findIndex(h => h.nroHabitacion == reserva.nroHabitacion && h.fechaInicio == reserva.fechaInicio )!= -1 ? 'selected' : ''}
                                        onClick={() => reservasSeleccionadas.findIndex(h => h.nroHabitacion == reserva.nroHabitacion && h.fechaInicio == reserva.fechaInicio )!= -1 ?
                                            setReservasSeleccionadas(() => {
                                                let i = reservasSeleccionadas.findIndex(h => h.nroHabitacion == reserva.nroHabitacion && h.fechaInicio == reserva.fechaInicio );
                                                return [...reservasSeleccionadas.slice(0,i),...reservasSeleccionadas.slice(i+1)];
                                            })
                                            :
                                            setReservasSeleccionadas([...reservasSeleccionadas, reserva])}>
                                        <td>{reserva.nombre}</td>
                                        <td>{reserva.apellido}</td>
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

                <div style={{display: 'flex', justifyContent: 'flex-end'}}>
                    <p className='WrapTextBox'>{reservasSeleccionadas.length > 0
                        ? 'Reservas a cancelar: ' + reservasSeleccionadas.length
                        : errorNoSeleccionado
                            ? 'Por favor seleccione una reserva'
                            : '' }
                    </p>
                    <button className='Button' onClick={() => {
                        if (reservasSeleccionadas.length > 0) {
                            cancelarReservas();
                        }else{
                            setErrorNoSeleccionado(true)
                        }
                    }}>
                        Siguiente
                    </button>
                </div>
            </div>

            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la cancelación de reservas'/>

        </>
    );
}