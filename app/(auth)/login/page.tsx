"use client";

import AuthRedirect from "../_components/auth-redirect";
import AuthTitle from "../_components/auth-title";
import Input from "@/components/input";
import FormField from "@/components/form-field";
import Button from "@/components/button";
import { LoginRequest, loginSchema } from "@/types/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { login } from "@/services/auth-service";
import { useState } from "react";
import { ApiError } from "@/lib/errors/api-error";
import { useRouter } from "next/navigation";
import FieldErrorMessage from "@/components/field-error-message";

export default function LoginPage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors, isValid, isSubmitting },
  } = useForm<LoginRequest>({
    resolver: zodResolver(loginSchema),
    mode: "onTouched",
  });

  const [errorMessage, setErrorMessage] = useState<string | undefined>(undefined);

  const onSubmit = async (data: LoginRequest) => {
    setErrorMessage(undefined);
    try {
      await login(data);
      router.push("/");
    } catch (error) {
      if (error instanceof ApiError) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("Ocorreu um erro inesperado. Por favor, tente novamente.");
      }
    }
  };

  return (
    <div className="max-w-xl">
      <AuthTitle title="Welcome back" description="Welcome back! Please enter your details" />

      <form className="flex flex-col gap-4" onSubmit={handleSubmit(onSubmit)}>
        <FormField label="Email" htmlFor="email">
          <Input placeholder="example@gmail.com" id="email" {...register("email")} />
          <FieldErrorMessage message={errors.email?.message} />
        </FormField>

        <FormField label="Senha" htmlFor="senha">
          <Input placeholder="Digite sua senha" id="senha" type="password" {...register("password")} />
          <FieldErrorMessage message={errors.password?.message} />
        </FormField>

        <Button disabled={!isValid || isSubmitting}>Entrar</Button>

        <FieldErrorMessage message={errorMessage} />
      </form>

      <AuthRedirect description="Don't have an account?" redirectText="Sign up for free" redirectUrl="/register" />
    </div>
  );
}
