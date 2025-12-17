import { getSession } from "@/lib/session";
import { NextResponse } from "next/server";
import { cookies } from "next/headers";

export async function POST(request: Request) {
    const body = await request.json();

    const res = await fetch("http://localhost:8081/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    });

    if (!res.ok) return NextResponse.json({ error: "Error login" }, { status: 401 });

    const tokens = await res.json();

    const session = await getSession();
    session.isLoggedIn = true;
    session.accessToken = tokens.accessToken;
    session.refreshToken = tokens.refreshToken;
    session.user = { username: body.usuario };
    await session.save();

    const cookieStore = await cookies();

    console.log(body)
    console.log(body.usuario)

    cookieStore.set("user_info", JSON.stringify({ username: body.usuario }), {
        secure: process.env.NODE_ENV === "production",
        httpOnly: false,
        maxAge: 60 * 60 * 24 * 30,
        path: "/",
    });

    return NextResponse.json({ ok: true });
}