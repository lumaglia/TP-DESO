"use client";
import { useRouter } from "next/navigation";

// Ajusta esto según el nombre de carpeta que elegiste (backend, proxy, v1, etc.)
const PROXY_PREFIX = "/api/backend";

export function useFetch() {
    const router = useRouter();

    /**
     * Wrapper sobre fetch.
     * @param path - La ruta del endpoint de Spring (ej: "/huespedes/1")
     * @param options - Opciones estándar de fetch (method, body, etc.)
     */
    const fetchApi = async (path: string, options: RequestInit = {}) => {

        // 1. Construimos la URL completa hacia nuestro Proxy
        // Nos aseguramos de que el path no tenga "/" al inicio para evitar dobles barras
        const cleanPath = path.startsWith("/") ? path.slice(1) : path;
        const url = `${PROXY_PREFIX}/${cleanPath}`;

        try {
            // 2. Hacemos la petición al Proxy de Next.js
            const response = await fetch(url, options);

            // 3. LA LÓGICA DE REDIRECCIÓN 🚨
            // Si el Proxy devuelve 401, significa que incluso el intento de refresh falló.
            if (response.status === 401) {
                console.warn("Sesión finalizada. Redirigiendo al login...");
                router.push("/login");
                return null; // Retornamos null para que el componente sepa que falló
            }

            return response;

        } catch (error) {
            console.error("Error de red o del servidor:", error);
            throw error;
        }
    };

    return fetchApi;
}