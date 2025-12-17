import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export const sessionOptions = {
    password: process.env.SESSION_PASSWORD_SECRETO || "CLAVE_SECRETA_SOLO_PARA_DESARROLLO",
    cookieName: "auth_cookie",
    cookieOptions: {
        secure: process.env.NODE_ENV === "production", // true en prod (https), false en local
        httpOnly: true, // ¡Vital! JS no puede leerla (seguridad máxima)
    },
};

export interface SessionData {
    accessToken?: string;
    refreshToken?: string;
    user?: { username: string };
    isLoggedIn: boolean;
}

export async function getSession() {
    const cookieStore = await cookies();
    return getIronSession<SessionData>(cookieStore, sessionOptions);
}