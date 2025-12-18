// app/api/logout/route.ts
import { getSession } from "@/lib/session";
import { NextResponse } from "next/server";

export async function POST() {
    const session = await getSession();

    if (session.accessToken) {
        try {
            await fetch("http://localhost:8081/auth/logout", {
                method: "POST",
                headers: {
                    'Authorization': `Bearer ${session.accessToken}`,
                    'content-type': 'application/json',
                },
                body: JSON.stringify({
                    'refreshToken': session.refreshToken
                })
            });
        } catch (error) {
            console.error("Error avisando al backend:", error);
        }
    }

    session.destroy();

    return NextResponse.json({ ok: true });
}