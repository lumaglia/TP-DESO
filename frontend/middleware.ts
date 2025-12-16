import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { getIronSession } from "iron-session";
import { sessionOptions, SessionData } from "@/lib/session";

export async function middleware(req: NextRequest) {
    const res = NextResponse.next();

    const session = await getIronSession<SessionData>(req, res, sessionOptions);

    const { pathname } = req.nextUrl;

    const isPublicRoute =
        pathname === "/login" ||
        pathname === "/register" ||
        pathname.startsWith("/api/auth/login") ||
        pathname.startsWith("/api/auth/register") ||
        pathname.startsWith("/api/backend/") ||
        pathname.startsWith("/_next") ||
        pathname.includes(".");

    if (!session.isLoggedIn && !isPublicRoute) {
        return NextResponse.redirect(new URL("/login", req.url));
    }

    if (session.isLoggedIn && pathname === "/login") {
        return NextResponse.redirect(new URL("/", req.url));
    }

    return res;
}

export const config = {
    matcher: [
        '/((?!_next/static|_next/image|favicon.ico).*)',
    ],
};