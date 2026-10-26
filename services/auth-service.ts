import { apiFetch } from "@/lib/utils/api-request";
import { LoginRequest, RegisterRequest, RegisterResponse } from "@/types/auth";

export async function registerUser(data: RegisterRequest): Promise<RegisterResponse> {
  return apiFetch<RegisterResponse>("/api/v1/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });
}

export async function login(data: LoginRequest): Promise<void> {
  return apiFetch<void>("/api/v1/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify(data),
    noContent: true,
  });
}
