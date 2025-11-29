'use client';
import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { useRouter } from 'next/navigation'
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import Campo from '../../Campo.tsx'
import Row from '../../Row'
import Encabezado from '../../Encabezado.tsx'
import { AlertaCancelar, AlertaHuespedNoEncontrado } from '../../Alertas.tsx'
import '../Alta/AltaHuesped.css'
import { comboValues, fieldTypes } from '../../../public/constants.ts'
import './Buscar.css'

export default function BuscarHuesped() {
    const { register, formState, handleSubmit } = useForm();
    const router = useRouter();
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ alertaHuespedNoEncontradoOpen, setAlertaHuespedNoEncontradoOpen] = useState(false);
    const [ huespedes , setHuespedes ] = useState([])
    const [ errorNoSeleccionado, setErrorNoSeleccionado] = useState(false)
    const [ selectedHuesped, setSelectedHuesped ] = useState<{
        nombre: string;
        apellido: string;
        tipoDoc: string;
        nroDoc: string;
    } | null>(null)
    function onSubmit(data: any) {
        for (let key in data) {
            if(!data[key]){
                data[key] = null
            }
        }
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
                setSelectedHuesped(null)
                setAlertaHuespedNoEncontradoOpen(true)
            }else if(res.ok) {
                res.json().then(data => {
                    setHuespedes(data)
                    setSelectedHuesped(null)
                })
            }
        })
    }
    return(
        <>
            <Encabezado titulo="Buscar Huesped" />
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
            <div style={{display: 'flex', flexDirection: 'column', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>
                <ScrollArea.Root className='ScrollArea'>
                <ScrollArea.Viewport className='Viewport'>
                    <ScrollArea.Content className='Content'>
                        <table className='TablaBuscar'>
                            <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Tipo de documento</th>
                                <th>Numero de documento</th>
                            </tr>
                            </thead>
                            <tbody>
                            {huespedes.map((huesped: any) => (
                                <tr className={selectedHuesped === huesped ? 'selected' : ''}
                                    onClick={() => setSelectedHuesped(huesped)} key={huesped.nroDoc}>
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
                    <p>{selectedHuesped
                        ? 'Huesped seleccionado: ' + selectedHuesped.nombre + ' ' + selectedHuesped.apellido
                        : errorNoSeleccionado
                            ? 'Por favor seleccione un huesped'
                            : '' }
                    </p>
                    <button className='Button' onClick={() => {
                        if (selectedHuesped) {
                            router.push('/Huesped/Modificar')
                        }else{
                            setErrorNoSeleccionado(true)
                        }
                    }}>
                        Siguiente
                    </button>
                </div>
            </div>

            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la busqueda de huespedes'/>
            <AlertaHuespedNoEncontrado open={alertaHuespedNoEncontradoOpen} setOpen={setAlertaHuespedNoEncontradoOpen} />

        </>
    );
}