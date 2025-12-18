import './globals.css'
import { useState, useEffect } from 'react'
import Cookies from 'js-cookie';
import Image from 'next/image'
import logout from '@/public/LogOut.svg'
import { useRouter } from "next/navigation";
import Row from "./Row";

export default function Encabezado({ titulo } : { titulo:string}){
    const [user, setUser] = useState<{ username: string; } | null>(null);
    const router = useRouter();

    useEffect(() => {
        const raw = Cookies.get("user_info");
        if (raw) {
            try {
                setUser(JSON.parse(raw));
            } catch (e) {
                console.error("Error parsing user_info cookie:", e);
                Cookies.remove("user_info");
            }
        }
    }, []);

    const handleLogout = async () => {
        try {
            const res = await fetch("/api/auth/logout", {
                method: "POST",
            });

            if (res.ok) {
                Cookies.remove("user_info");
                setUser(null);
                router.push("/login");
            }
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
        }
    };

    return (
        <header>
            {user !== null
                ?
                <div style={{ display: 'flex', alignItems: 'center', position: 'relative' }}>
                    <h2 style={{ position: 'absolute', left: '50%', transform: 'translateX(-50%)' }}> {titulo} </h2>
                    <div className='row' style={{ alignItems: 'center' }}>
                        <h4 style={{ marginLeft: 'auto', marginRight: '10px' }}>{user!.username}</h4>
                        <button className='LogoutButton' onClick={handleLogout}>
                            <Image src={logout} width={25} height={25} alt='Logout' style={{ filter: 'invert(1) opacity(0.8)' }} />
                        </button>
                    </div>
                </div>

                :
                <Row>
                    <h2 style={{ textAlign: 'center', margin: '15px 0' }}> {titulo} </h2>
                </Row>
            }
        </header>
    );
}