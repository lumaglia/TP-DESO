'use client';

import { useState } from 'react';
import { useForm } from 'react-hook-form'
import Encabezado from "@/app/Encabezado";
import { useRouter } from 'next/navigation'
import Campo from "../Campo";
import Row from "../Row";
import { fieldTypes, validation } from "../../public/constants";
import Link from "next/link";


export default function Home() {
    const router = useRouter();
    const { register, formState, handleSubmit, watch } = useForm();
    const { errors } = formState;
    const [disabled, setDisabled] = useState(false);
    const [notOk, setNotOk] = useState(false);
    const [conflict, setConflict] = useState(false);

    function onSubmit(data: any) {
        setDisabled(true);
        fetch('api/auth/register', {
            method: 'POST',
            body: JSON.stringify({
                'usuario': data.usuario,
                'contrasenna': data.contrasenna
            })
        }).then(res => {
            setDisabled(false);
            if (res.ok) {
                router.push('/')
            } else if(res.status === 409) {
                setConflict(true);
            }else setNotOk(true);
        })
    }

    return(
        <>
            <Encabezado titulo='Registrarse' />
            <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column', marginTop:'30px'}}>
                    <Row><Campo field="Usuario" register={register} errors={errors} validation={validation['usuario']} />
                    </Row>
                    <Row>
                        <Campo field="Contraseña" register={register} errors={errors} validation={validation['contrasenna']} type={fieldTypes.PASSWORD} />
                    </Row>
                    <Row>
                        <Campo field="Introduzca de nuevo la contraseña" register={register} errors={errors} validation={{
                            required: true,
                            validate: (value: string) => { return value === watch('contrasenna') || 'Las contraseñas deben coincidir' }
                        }} type={fieldTypes.PASSWORD} />
                    </Row>
                    {
                        conflict
                        ? <p className='error'>Usuario ya existente</p>
                            : notOk && <p className='error'>Ocurrio un error con el registro, por favor contacte a soporte o intentelo mas tarde</p>
                    }
                    <Row>
                        <button className='Button' type={'submit'} style={{alignSelf: 'center', marginTop:'10px'}} disabled={disabled}>Registrar</button>
                    </Row>
                    <p>Ya tienes cuenta? <Link href={'/login'}>Iniciar Sesión</Link></p>
                </div>
            </form>
        </>
    );
}