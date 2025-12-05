'use client';
import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { useRouter } from 'next/navigation'
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import Campo from '../../Campo.tsx'
import Row from '../../Row'
import Encabezado from '../../Encabezado.tsx'
import { AlertaCancelar } from '../../Alertas.tsx'
import '../../globals.css'
import '../../Huesped/Alta/AltaHuesped.css'
import { comboValues, fieldTypes } from '../../../public/constants.ts'
import '../../Huesped/Buscar/Buscar.css'
import './OcuparHabitacion.css'

type HuespedValues = {
    nombre: string;
    apellido: string;
    tipoDoc: string;
    nroDoc: string;
}
enum EstadoPantalla {
    "Habitacion",
    "Huesped",
    "MenuFin"
}

export default function BuscarHuesped() {
    const { register, formState, handleSubmit } = useForm();
    const router = useRouter();
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ alertaHuespedNoEncontradoOpen, setAlertaHuespedNoEncontradoOpen] = useState(false);
    const [ huespedes , setHuespedes ] = useState<Array<HuespedValues>>([])
    const [ errorNoSeleccionado, setErrorNoSeleccionado] = useState(false)
    const [ selectedHuespedes, setSelectedHuespedes ] = useState<Array<HuespedValues>>([]);
    const [ pantalla, setPantalla ] = useState(EstadoPantalla.Huesped); //USAR HABITACION CUANDO ESTE CONECTADO AL CU05
    let tipoHabitacion = "Simple Estandar", idHabitacion = "5", fechaInicio = "Jueves 04-12-2025 12:00:00", fechaFin = "Martes 09-12-2025 10:00:00";
    function onSubmit(data: any) {
        for (let key in data) {
            if(!data[key]){
                data[key] = null
            }
        }
        setAlertaHuespedNoEncontradoOpen(false)
        fetch('http://localhost:8081/Huesped/Buscar', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.status === 204) {
                setHuespedes([])
                setAlertaHuespedNoEncontradoOpen(true)
            }else if(res.ok) {
                res.json().then(data => {
                    setHuespedes(data)
                })
            }
        })
    }
    function cargarEstadia(cargarOtra:boolean){
        //POST PARA ESTADIA

        //SI SALE BIEN:
        if(cargarOtra){
            setSelectedHuespedes([]);
            setPantalla(EstadoPantalla.Habitacion);
        }else{
            router.push("/");
        }

    }
    return(
        <>
            <Encabezado titulo="Ocupar Habitación" />
            { pantalla == EstadoPantalla.Habitacion ?
                (<>

                </>)
                : pantalla == EstadoPantalla.Huesped ? (
                    <>
                        <h3 style={{textAlign:'center'}}>Ingrese datos en los campos por los que desea filtrar</h3>
                        <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                            <Row>
                                <Campo field="Nombre" register={register} errors={{}} validation={{}} placeholder="ej. JUAN"/>
                                <Campo field="Apellido" register={register} errors={{}} validation={{}} placeholder="ej. MARTINEZ"/>
                            </Row>
                            <Row>
                                <Campo field="Tipo de documento" register={register} errors={{}} validation={{}}
                                       type={fieldTypes.COMBOBOX} comboValues={comboValues['tipoDocumento']}/>
                                <Campo field="Número de documento" register={register} errors={{}} validation={{}}
                                       placeholder="ej. 11.222.333"/>
                            </Row>
                            <Row>
                                <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Cancelar</button>
                                <button type='submit' className='Button'>Buscar</button>
                            </Row>
                        </form>
                        {
                            alertaHuespedNoEncontradoOpen ? <Row><p>No se han encontrado huespedes para los criterios seleccionados.</p></Row>
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
                                                <th>Nombre</th>
                                                <th>Apellido</th>
                                                <th>Tipo de documento</th>
                                                <th>Numero de documento</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {huespedes.map((huesped: HuespedValues) => (
                                                <tr className={selectedHuespedes.findIndex(h => h.nroDoc == huesped.nroDoc && h.tipoDoc == huesped.tipoDoc )!= -1 ? 'selected' : ''}
                                                    onClick={() => selectedHuespedes.findIndex(h => h.nroDoc == huesped.nroDoc && h.tipoDoc == huesped.tipoDoc )!= -1 ?
                                                        setSelectedHuespedes(() => {
                                                            let i = selectedHuespedes.findIndex(h => h.nroDoc == huesped.nroDoc && h.tipoDoc == huesped.tipoDoc );
                                                            return [...selectedHuespedes.slice(0,i),...selectedHuespedes.slice(i+1)];
                                                        })
                                                        :
                                                        setSelectedHuespedes([...selectedHuespedes, huesped])} key={huesped.nroDoc}>
                                                    <td>{huesped.nombre}</td>
                                                    <td>{huesped.apellido}</td>
                                                    <td>{huesped.tipoDoc}</td>
                                                    <td>{huesped.nroDoc}</td>
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
                                <p className='WrapTextBox'>{selectedHuespedes.length > 0
                                    ? 'Huespedes seleccionados:' + selectedHuespedes.map((huesped: HuespedValues) => ' ' + huesped.nombre + ' ' + huesped.apellido)
                                    : errorNoSeleccionado
                                        ? 'Por favor seleccione un huesped'
                                        : '' }
                                </p>
                                <button className='Button' onClick={() => {
                                    if (selectedHuespedes.length > 0) {
                                        setPantalla(EstadoPantalla.MenuFin)
                                    }else{
                                        setErrorNoSeleccionado(true)
                                    }
                                }}>
                                    Siguiente
                                </button>
                            </div>
                        </div>
                    </>) : (
                        <>
                            <h3 style={{textAlign:'center'}}>Se cargará la habitación {tipoHabitacion} N°{idHabitacion}</h3>
                            <h3 style={{textAlign:'center'}}>Entre el {fechaInicio} y {fechaFin}</h3>
                            <h3 style={{textAlign:'center'}}>A los huespedes:</h3>
                            <div style={{display: 'flex', flexDirection: 'column', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>
                                <ScrollArea.Root className='ScrollArea'>
                                    <ScrollArea.Viewport className='Viewport'>
                                        <ScrollArea.Content className='Content'>
                                            <table>
                                                <thead style={{position: 'sticky', top: '0'}}>
                                                <tr>
                                                    <th>Nombre</th>
                                                    <th>Apellido</th>
                                                    <th>Tipo de documento</th>
                                                    <th>Numero de documento</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {selectedHuespedes.map((huesped: HuespedValues) => (
                                                    <tr
                                                        key={huesped.nroDoc}>
                                                        <td>{huesped.nombre}</td>
                                                        <td>{huesped.apellido}</td>
                                                        <td>{huesped.tipoDoc}</td>
                                                        <td>{huesped.nroDoc}</td>
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
                            <Row>
                                <button className='Button FixedLength' onClick={() => setPantalla(EstadoPantalla.Huesped)}>Seguir cargando huespedes</button>
                                <button className='Button FixedLength' onClick={() => cargarEstadia(true)}>Cargar otra habitación</button>
                                <button className='Button FixedLength' onClick={() => cargarEstadia(false)}>Confirmar y Salir</button>

                            </Row>
                        </>
                    )
            }

            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la carga de estadía'/>

        </>
    );
}