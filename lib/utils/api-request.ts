import { ErrorResponse } from "@/types/error";
import { ApiError } from "../errors/api-error";

const API_URL = process.env.NEXT_PUBLIC_SMART_WALLET_API_URL;

export async function apiFetch<T>(path: string, options: RequestInit): Promise<T> {
  try {
    const response = await fetch(`${API_URL}${path}`, {
      ...options,
      signal: AbortSignal.timeout(5000),
    });

    if (!response.ok) {
      const body = (await response.json()) as ErrorResponse;
      throw new ApiError(body.message);
    }
    if (response.status === 204 || response.status === 304) {
      return {} as T;
    }

    return response.json();
  } catch (error) {
    if (error instanceof ApiError) {
      throw error;
    }

    throw new ApiError("Falha ao buscar dados da API. Por favor, tente novamente mais tarde.");
  }
}
