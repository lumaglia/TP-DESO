import { getSession } from "@/lib/session";
import { NextResponse } from "next/server";
import { cookies } from "next/headers";

const SPRING_REGISTER_URL = "http://localhost:8081/auth/register";

export async function POST(request: Request) {
    try {
        const body = await request.json();

        const springResponse = await fetch(SPRING_REGISTER_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                usuario: body.usuario,
                contrasenna: body.contrasenna,
            }),
        });

        if (springResponse.ok) {
            const data = await springResponse.json();

            const { accessToken, refreshToken } = data;

            if (!accessToken || !refreshToken) {
                return NextResponse.json(
                    { message: "Registro exitoso, pero el servidor no devolvió los tokens." },
                    { status: 500 }
                );
            }

            const session = await getSession();

            session.isLoggedIn = true;
            session.accessToken = accessToken;
            session.refreshToken = refreshToken;
            session.user = body.usuario;

            await session.save();
            const cookieStore = await cookies();

            cookieStore.set("user_info", JSON.stringify({ username: body.usuario }), {
                secure: process.env.NODE_ENV === "production",
                httpOnly: false,
                maxAge: 60 * 60 * 24 * 30,
                path: "/",
                sameSite: "lax"
            });

            return NextResponse.json(data, { status: 201 });

        } else {
            const responseText = await springResponse.text();
            const errorData = responseText ? JSON.parse(responseText) : { message: 'Error de conflicto (409) sin detalle.' };
            return NextResponse.json(errorData, { status: springResponse.status });
        }

    } catch (error) {
        console.error("Error en la ruta /api/register:", error);
        return NextResponse.json(
            { message: "Fallo interno al procesar el registro." },
            { status: 500 }
        );
    }
}