import { AlertDialog } from '@base-ui-components/react/alert-dialog';
import { useRouter } from 'next/navigation'
import Image from 'next/image'
import Link from 'next/link'
import warningIcon from '../public/warning.png'
import './AlertaCancelar.css';

export function AlertaCancelar({open, setOpen, text} : {open: boolean, setOpen: (open: boolean, e: any) => void, text: string}) {
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            {/*<AlertDialog.Trigger data-color="red" className={'Button'}>*/}
            {/*    Discard draft*/}
            {/*</AlertDialog.Trigger>*/}
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'}>
                    <AlertDialog.Title className={'Title'}>¿Desea cancelar {text}?</AlertDialog.Title>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color="white">No</AlertDialog.Close>
                        <Link href={'/'} className='PopupButton' style={{textDecoration: 'none'}}>
                            Si
                        </Link>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}

export function AlertaDocumento({open, setOpen, data} : {open: boolean, setOpen: (open: boolean, e: any) => void, data: any}) {
    const router = useRouter();
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            {/*<AlertDialog.Trigger data-color="red" className={'Button'}>*/}
            {/*    Discard draft*/}
            {/*</AlertDialog.Trigger>*/}
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'} outline-color='yellow'>
                    <div style={{display: 'flex', alignItems: 'flex-start', margin: '0', padding: '0'}}>
                        <Image src={warningIcon} width={28} height={28} alt="warning" />
                        <AlertDialog.Title className={'Title'}>
                            Cuidado
                        </AlertDialog.Title>
                    </div>
                    <AlertDialog.Description className={'Description'}>
                        El tipo y número de documento ya existen en el sistema
                    </AlertDialog.Description>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color='white'>Volver y Corregir</AlertDialog.Close>
                        <AlertDialog.Close className={'PopupButton'} onClick={() => {
                            data.tipoDocViejo = data.tipoDoc;
                            data.nroDocViejo = data.nroDoc;
                            fetch('http://localhost:8081/Huesped/Modificar', {
                                method: 'PUT',
                                body: JSON.stringify(data),
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json'
                                }
                            }).then(res => {
                                if(res.ok){
                                    router.push(`/Huesped/Alta/success?huesped=${encodeURIComponent(data.nombre+' '+data.apellido)}`)
                                }
                            })
                        }}>
                            Cargar Igualmente
                        </AlertDialog.Close>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}

export function AlertaDocumentoModificar({open, setOpen, data, tipoDoc, nroDoc, setErrorOpen} : {open: boolean, setOpen: (open: boolean, e: any) => void, data: any, tipoDoc: string, nroDoc: string, setErrorOpen: React.Dispatch<React.SetStateAction<boolean>>}) {
    const router = useRouter();
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            {/*<AlertDialog.Trigger data-color="red" className={'Button'}>*/}
            {/*    Discard draft*/}
            {/*</AlertDialog.Trigger>*/}
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'} outline-color='yellow'>
                    <div style={{display: 'flex', alignItems: 'flex-start', margin: '0', padding: '0'}}>
                        <Image src={warningIcon} width={28} height={28} alt="warning" />
                        <AlertDialog.Title className={'Title'}>
                            Cuidado
                        </AlertDialog.Title>
                    </div>
                    <AlertDialog.Description className={'Description'}>
                        El tipo y número de documento ya existen en el sistema
                    </AlertDialog.Description>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color='white'>Volver y Corregir</AlertDialog.Close>
                        <AlertDialog.Close className={'PopupButton'} onClick={() => {
                            console.log(tipoDoc);
                            console.log(nroDoc);
                            console.log(data);
                            data.tipoDocViejo = tipoDoc;
                            data.nroDocViejo = nroDoc;
                            fetch('http://localhost:8081/Huesped/Modificar/Override', {
                                method: 'PUT',
                                body: JSON.stringify(data),
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json'
                                }
                            }).then(res => {
                                if(res.ok){
                                    router.push(`/Huesped/Modificar/success?huesped=${encodeURIComponent(data.nombre+' '+data.apellido)}`)
                                }else{
                                    setErrorOpen(true);
                                }
                            })
                        }}>
                            Cargar Igualmente
                        </AlertDialog.Close>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}

export function AlertaHuespedNoEncontrado({open, setOpen} : {open: boolean, setOpen: (open: boolean, e: any) => void}) {
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'}>
                    <AlertDialog.Title className={'Title'}>No se encontraron huespedes</AlertDialog.Title>
                    <AlertDialog.Description className={'Description'}>
                        ¿Desea dar de alta un huesped?
                    </AlertDialog.Description>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color="white">No</AlertDialog.Close>
                        <Link href={'/Huesped/Alta'} className='PopupButton' style={{textDecoration: 'none'}}>
                            Si
                        </Link>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}

export function ErrorSobreescritoHospedado({open, setOpen} : {open: boolean, setOpen: (open: boolean, e: any) => void}) {
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'} outline-color='red'>
                    <AlertDialog.Title className={'Title'}>Error: Huesped ya hospedado.</AlertDialog.Title>
                    <AlertDialog.Description className={'Description'}>
                        El documento ingresado corresponde o el actual del huesped modificado ya se ha hospedado en el hotel y no puede ser sobreescrito.
                    </AlertDialog.Description>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color="white">Aceptar</AlertDialog.Close>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}

export function ErrorModificadoHospedado({open, setOpen} : {open: boolean, setOpen: (open: boolean, e: any) => void}) {
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'} outline-color='red'>
                    <AlertDialog.Title className={'Title'}>Error: Huesped ya hospedado.</AlertDialog.Title>
                    <AlertDialog.Description className={'Description'}>
                        El huesped siendo modificado ya se ha hospedado en el hotel, su documento no puede alterarse.
                    </AlertDialog.Description>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color="white">Aceptar</AlertDialog.Close>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}

export function AlertaReserva({open, setOpen, data, setPantalla} : {open: boolean, setOpen: (open: boolean, e: any) => void, data: Array<any>, setPantalla: Function}) {
    const router = useRouter();
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'} outline-color='yellow'>
                    <div style={{display: 'flex', alignItems: 'flex-start', margin: '0', padding: '0'}}>
                        <Image src={warningIcon} width={28} height={28} alt="warning" />
                        <AlertDialog.Title className={'Title'}>
                            Cuidado
                        </AlertDialog.Title>
                    </div>
                    <AlertDialog.Description className={'Description'}>
                        Se ha seleccionado un rango de fechas sobre las reservas:
                        {data.map(c => {
                            return <>
                                <p>{new Date(new Date(c.reserva.fechaInicio).getTime() + 24*3600000).toLocaleDateString('en-GB')} - {new Date(new Date(c.reserva.fechaFin).getTime() + 24*3600000).toLocaleDateString('en-GB')} a nombre de {c.reserva.nombre} {c.reserva.apellido}</p>
                                <br />
                            </>
                        })}
                    </AlertDialog.Description>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'PopupButton'} data-color='white' onClick={() => {
                        }}>Volver y Cambiar</AlertDialog.Close>
                        <AlertDialog.Close className={'PopupButton'} onClick={() => {
                            setPantalla(1)
                        }}>
                            Ocupar Igualmente
                        </AlertDialog.Close>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}