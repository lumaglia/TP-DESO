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
    const { register, formState, handleSubmit } = useForm();
    const [disabled, setDisabled] = useState(false);
    const [notFound, setNotFound] = useState(false);

    function onSubmit(data: any) {
        setDisabled(true);
        fetch('api/auth/login', {
            method: 'POST',
            body: JSON.stringify(data)
        }).then(res => {
            setDisabled(false);
            if (res.ok) {
                router.push('/')
            }else{
                setNotFound(true);
            }
        })
    }

    return(
        <>
            <Encabezado titulo='Login' />
            <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column', marginTop:'30px'}}>
                    <Row><Campo field="Usuario" register={register} errors={{}} validation={validation['requerido']} />
                    </Row>
                    <Row>
                        <Campo field="Contraseña" register={register} errors={{}} validation={validation['requerido']} type={fieldTypes.PASSWORD} />
                    </Row>
                    {
                        notFound && <p className='error'>Usuario y/o contraseña incorrectos</p>
                    }
                    <Row>
                        <button className='Button' type={'submit'} style={{alignSelf: 'center', marginTop:'10px'}} disabled={disabled}>Iniciar Sesión</button>
                    </Row>
                        <p>No tienes cuenta? <Link href={'/register'}>Registrate</Link></p>
                </div>
            </form>
        </>
    );
}