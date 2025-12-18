import { getSession } from "@/lib/session";
import { NextResponse } from "next/server";
import Cookies from 'js-cookie';

const SPRING_API_URL = "http://localhost:8081";

interface RouteContext {
    params: Promise<{ path: string[] }>;
}

async function handler(req: Request, context: RouteContext) {

    const { path: pathArray } = await context.params;

    const session = await getSession();

    if (!session.isLoggedIn || !session.accessToken) {
        return NextResponse.json({ error: "No autorizado" }, { status: 401 });
    }

    const path = pathArray.join("/");
    const url = `${SPRING_API_URL}/${path}`;

    const body = req.method !== "GET" && req.method !== "HEAD" ? await req.json() : undefined;

    const options: RequestInit = {
        method: req.method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${session.accessToken}`,
        },
        body: body ? JSON.stringify(body) : undefined,
    };

    let response = await fetch(url, options);

    if (response.status === 401) {
        console.log("Token expirado. Intentando refrescar...");
        const refreshRes = await fetch(`${SPRING_API_URL}/auth/refresh`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ refreshToken: session.refreshToken })
        });

        if (refreshRes.ok) {
            const newTokens = await refreshRes.json();

            session.accessToken = newTokens.accessToken;
            await session.save();

            options.headers = {
                ...options.headers,
                "Authorization": `Bearer ${newTokens.accessToken}`
            };
            response = await fetch(url, options);
        } else {
            session.destroy();
            Cookies.remove("user_info");
            return NextResponse.json({ error: "Sesión expirada" }, { status: 401 });
        }
    }

    const text = await response.text();
    let data;
    try {
        data = text ? JSON.parse(text) : {};
    } catch (e) {
        data = { message: text };
    }

    return NextResponse.json(data, { status: response.status });
}

export { handler as GET, handler as POST, handler as PUT, handler as PATCH, handler as DELETE };