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
                            fetch('http://localhost:8081/Huesped/Alta?modify=true', {
                                method: 'POST',
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