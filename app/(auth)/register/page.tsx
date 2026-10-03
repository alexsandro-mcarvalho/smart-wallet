"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

import FormField from "@/components/form-field";
import AuthRedirect from "../_components/auth-redirect";
import AuthTitle from "../_components/auth-title";
import Input from "@/components/input";
import Button from "@/components/button";
import { RegisterRequest, registerSchema } from "@/types/auth";
import FieldErrorMessage from "@/components/field-error-message";
import { registerUser } from "@/services/auth-service";
import { useState } from "react";
import { ApiError } from "@/lib/api-error";

export default function RegisterPage() {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid, isSubmitting },
  } = useForm<RegisterRequest>({
    resolver: zodResolver(registerSchema),
    mode: "onTouched",
  });
  const [errorMessage, setErrorMessage] = useState<string | undefined>(undefined);

  const onSubmit = async (data: RegisterRequest) => {
    setErrorMessage(undefined);
    try {
      await registerUser(data);
    } catch (error: unknown) {
      if (error instanceof ApiError) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("Ocorreu um erro inesperado. Por favor, tente novamente.");
      }
    }
  };

  return (
    <div className="max-w-xl">
      <AuthTitle title="Create new account" description="Welcome back! Please enter your details" />
      <form className="flex flex-col gap-4" onSubmit={handleSubmit(onSubmit)}>
        <FormField label="Username" htmlFor="username">
          <Input
            placeholder="user"
            id="username"
            {...register("username")}
          />
          <FieldErrorMessage message={errors.username?.message} />
        </FormField>

        <FormField label="Email" htmlFor="email">
          <Input
            placeholder="example@gmail.com"
            id="email"
            {...register("email")}
          />
          <FieldErrorMessage message={errors.email?.message} />
        </FormField>

        <FormField label="Senha" htmlFor="senha">
          <Input
            placeholder="Digite sua senha"
            id="senha"
            type="password"
            {...register("password")}
          />
          <FieldErrorMessage message={errors.password?.message} />
        </FormField>

        <Button type="submit" disabled={!isValid || isSubmitting}>
          Entrar
        </Button>
        <FieldErrorMessage message={errorMessage} />
      </form>
      <AuthRedirect description="Already have an account?" redirectText="Sign in" redirectUrl="/login" />
    </div>
  );
}
