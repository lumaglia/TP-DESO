import * as React from 'react';
import { AlertDialog } from '@base-ui-components/react/alert-dialog';
import './AlertaCancelar.css';

export default function AlertaCancelar({open, setOpen} : {open: boolean, setOpen: (open: boolean, e: any) => void}) {
    return (
        <AlertDialog.Root open={open} onOpenChange={setOpen}>
            {/*<AlertDialog.Trigger data-color="red" className={'Button'}>*/}
            {/*    Discard draft*/}
            {/*</AlertDialog.Trigger>*/}
            <AlertDialog.Portal>
                <AlertDialog.Backdrop className={'Backdrop'} />
                <AlertDialog.Popup className={'Popup'}>
                    <AlertDialog.Title className={'Title'}>¿Desea cancelar el alta de huesped?</AlertDialog.Title>
                    <div className={'Actions'}>
                        <AlertDialog.Close className={'Button'}>No</AlertDialog.Close>
                        <AlertDialog.Close data-color="red" className={'Button'} onClick={() => window.location.reload()}>
                            Si
                        </AlertDialog.Close>
                    </div>
                </AlertDialog.Popup>
            </AlertDialog.Portal>
        </AlertDialog.Root>
    );
}