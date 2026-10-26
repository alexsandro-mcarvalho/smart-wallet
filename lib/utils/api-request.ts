import { ErrorResponse } from "@/types/error";
import { ApiError } from "../errors/api-error";

const API_URL = process.env.NEXT_PUBLIC_SMART_WALLET_API_URL;

type ApiFetchOptions = RequestInit & {
  noContent?: boolean;
};

export async function apiFetch<T>(path: string, fetchOptions: ApiFetchOptions): Promise<T> {
  const { noContent, ...options } = fetchOptions;
  try {
    const response = await fetch(`${API_URL}${path}`, {
      ...options,
      signal: AbortSignal.timeout(5000),
    });

    if (!response.ok) {
      const body = (await response.json()) as ErrorResponse;
      throw new ApiError(body.message);
    }
    if (noContent) {
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
