import { z } from "zod";

export const registerSchema = z.object({
  username: z.string().min(3, "Username deve ter 3 ou mais caracteres"),
  email: z.email("Email inválido"),
  password: z.string().min(6, "Senha deve ter 6 ou mais caracteres"),
});

export type RegisterRequest = z.infer<typeof registerSchema>;

export type RegisterResponse = {
  id: number;
  username: string;
  email: string;
  createdAt: string;
};

export const loginSchema = z.object({
  email: z.email("Email inválido"),
  password: z.string().min(6, "Senha deve ter 6 ou mais caracteres"),
});

export type LoginRequest = z.infer<typeof loginSchema>;
