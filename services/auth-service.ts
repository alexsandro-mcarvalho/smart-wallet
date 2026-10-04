import { ApiError } from "@/lib/api-error";
import { LoginRequest, RegisterRequest, RegisterResponse } from "@/types/auth";
import { ErrorResponse } from "@/types/error";

const API_URL = process.env.NEXT_PUBLIC_SMART_WALLET_API_URL;

export async function registerUser(data: RegisterRequest): Promise<RegisterResponse> {
  console.log(API_URL);
  const response = await fetch(`${API_URL}/api/v1/auth/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorResponse = (await response.json()) as ErrorResponse;
    throw new ApiError(errorResponse.message);
  }

  return response.json();
}

export async function login(data: LoginRequest): Promise<void> {
  const response = await fetch(`${API_URL}/api/v1/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorResponse = (await response.json()) as ErrorResponse;
    throw new ApiError(errorResponse.message);
  }
}

