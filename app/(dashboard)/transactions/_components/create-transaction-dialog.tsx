"use client";

import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { format } from "date-fns";
import { ptBR } from "date-fns/locale";
import { ArrowLeftRight, CalendarIcon } from "lucide-react";

import Button from "@/components/button";
import FieldErrorMessage from "@/components/field-error-message";
import FormField from "@/components/form-field";
import Input from "@/components/input";
import CurrencyInput from "@/components/currency-input";
import { Calendar } from "@/components/ui/calendar";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import {
  createTransactionSchema,
  type CreateTransactionFormValues,
  type CreateTransactionFormData,
} from "@/schemas/transaction.schema";
import AssetInput from "./asset-input";

const transactionTypes = createTransactionSchema.in.shape.type.options.map((value) => ({
  value,
  label: value === "BUY" ? "Compra" : "Venda",
}));

function getTransactionDefaultValues(): CreateTransactionFormValues {
  const now = new Date();

  return {
    assetId: 0,
    quantity: "",
    price: "",
    type: "BUY",
    transactionDate: now,
    transactionTime: format(now, "HH:mm"),
  };
}

export default function CreateTransactionDialog() {
  const [open, setOpen] = useState(false);
  const [calendarOpen, setCalendarOpen] = useState(false);
  const {
    control,
    handleSubmit,
    reset,
    formState: { isSubmitting, isValid },
  } = useForm<CreateTransactionFormValues, unknown, CreateTransactionFormData>({
    resolver: zodResolver(createTransactionSchema),
    defaultValues: getTransactionDefaultValues(),
    mode: "onTouched",
  });

  function handleOpenChange(nextOpen: boolean) {
    if (nextOpen) {
      reset(getTransactionDefaultValues());
    }
    setCalendarOpen(false);
    setOpen(nextOpen);
  }

  function onSubmit(data: CreateTransactionFormData) {
    console.log("Nova transação:", data);
    handleOpenChange(false);
  }

  return (
    <Dialog open={open} onOpenChange={handleOpenChange}>
      <DialogTrigger render={<Button size="sm" />}>
        <span className="flex items-center gap-2">
          <ArrowLeftRight size={20} className="shrink-0 text-text-1" aria-hidden="true" />
          Nova transação
        </span>
      </DialogTrigger>
      <DialogContent className="max-h-[calc(100dvh-2rem)] overflow-y-auto sm:max-w-lg">
        <DialogHeader>
          <DialogTitle>Nova transação</DialogTitle>
          <DialogDescription>Informe os dados da compra ou venda do ativo.</DialogDescription>
        </DialogHeader>

        <form className="flex flex-col gap-5" onSubmit={handleSubmit(onSubmit)} noValidate>
          <Controller
            name="assetId"
            control={control}
            render={({ field, fieldState }) => (
              <FormField label="Ativo" htmlFor="transaction-asset">
                <AssetInput
                  {...field}
                  id="transaction-asset"
                  aria-invalid={fieldState.invalid}
                  aria-describedby="transaction-asset-error"
                />
                <div id="transaction-asset-error" role="alert">
                  <FieldErrorMessage message={fieldState.error?.message} />
                </div>
              </FormField>
            )}
          />

          <div className="grid gap-4 sm:grid-cols-2">
            <Controller
              name="quantity"
              control={control}
              render={({ field, fieldState }) => (
                <FormField label="Quantidade" htmlFor="transaction-quantity">
                  <Input
                    {...field}
                    id="transaction-quantity"
                    type="text"
                    inputMode="decimal"
                    placeholder="0.00000000"
                    autoComplete="off"
                    aria-invalid={fieldState.invalid}
                    aria-describedby="transaction-quantity-error"
                    onChange={(event) => {
                      const value = event.target.value;
                      if (/^\d*(?:\.\d*)?$/.test(value)) {
                        field.onChange(value);
                      }
                    }}
                  />
                  <div id="transaction-quantity-error" role="alert">
                    <FieldErrorMessage message={fieldState.error?.message} />
                  </div>
                </FormField>
              )}
            />
            <Controller
              name="price"
              control={control}
              render={({ field, fieldState }) => (
                <FormField label="Preço" htmlFor="transaction-price">
                  <CurrencyInput
                    {...field}
                    id="transaction-price"
                    placeholder="R$ 0,00"
                    autoComplete="off"
                    aria-invalid={fieldState.invalid}
                    aria-describedby="transaction-price-error"
                    onValueChange={field.onChange}
                  />
                  <div id="transaction-price-error" role="alert">
                    <FieldErrorMessage message={fieldState.error?.message} />
                  </div>
                </FormField>
              )}
            />
          </div>

          <Controller
            name="type"
            control={control}
            render={({ field, fieldState }) => (
              <FormField label="Tipo" htmlFor="transaction-type">
                <Select name={field.name} value={field.value} onValueChange={field.onChange} items={transactionTypes}>
                  <SelectTrigger
                    id="transaction-type"
                    ref={field.ref}
                    onBlur={field.onBlur}
                    aria-invalid={fieldState.invalid}
                    aria-describedby="transaction-type-error"
                    className="w-full"
                  >
                    <SelectValue placeholder="Selecione o tipo" />
                  </SelectTrigger>
                  <SelectContent>
                    {transactionTypes.map((type) => (
                      <SelectItem key={type.value} value={type.value}>
                        {type.label}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                <div id="transaction-type-error" role="alert">
                  <FieldErrorMessage message={fieldState.error?.message} />
                </div>
              </FormField>
            )}
          />

          <fieldset className="flex min-w-0 flex-col gap-3">
            <legend className="mb-3 text-sm font-medium text-text-1">Data de transação</legend>
            <div className="grid gap-4 sm:grid-cols-2">
              <Controller
                name="transactionDate"
                control={control}
                render={({ field, fieldState }) => (
                  <FormField label="Data" htmlFor="transaction-date">
                    <Popover open={calendarOpen} onOpenChange={setCalendarOpen}>
                      <PopoverTrigger
                        render={<Button variant="secondary" />}
                        id="transaction-date"
                        type="button"
                        ref={field.ref}
                        onBlur={field.onBlur}
                        aria-invalid={fieldState.invalid}
                        aria-describedby="transaction-date-error"
                        className="flex min-h-14 items-center justify-between gap-2 text-left font-normal"
                      >
                        {format(field.value, "dd/MM/yyyy", { locale: ptBR })}
                        <CalendarIcon className="size-4" aria-hidden="true" />
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0" align="start">
                        <Calendar
                          mode="single"
                          required
                          selected={field.value}
                          defaultMonth={field.value}
                          locale={ptBR}
                          onSelect={(date) => {
                            field.onChange(date);
                            field.onBlur();
                            setCalendarOpen(false);
                          }}
                        />
                      </PopoverContent>
                    </Popover>
                    <div id="transaction-date-error" role="alert">
                      <FieldErrorMessage message={fieldState.error?.message} />
                    </div>
                  </FormField>
                )}
              />
              <Controller
                name="transactionTime"
                control={control}
                render={({ field, fieldState }) => (
                  <FormField label="Horário" htmlFor="transaction-time">
                    <Input
                      {...field}
                      id="transaction-time"
                      type="text"
                      inputMode="numeric"
                      maxLength={5}
                      placeholder="HH:mm"
                      autoComplete="off"
                      aria-invalid={fieldState.invalid}
                      aria-describedby="transaction-time-hint transaction-time-error"
                      onChange={(event) => {
                        const digits = event.target.value.replace(/\D/g, "").slice(0, 4);
                        field.onChange(digits.length > 2 ? `${digits.slice(0, 2)}:${digits.slice(2)}` : digits);
                      }}
                    />
                    <p id="transaction-time-hint" className="text-xs text-muted-foreground">
                      Digite quatro números para horas e minutos.
                    </p>
                    <div id="transaction-time-error" role="alert">
                      <FieldErrorMessage message={fieldState.error?.message} />
                    </div>
                  </FormField>
                )}
              />
            </div>
          </fieldset>

          <DialogFooter>
            <DialogClose render={<Button type="button" variant="secondary" size="sm" />}>Cancelar</DialogClose>
            <Button type="submit" size="sm" disabled={!isValid || isSubmitting}>
              Cadastrar transação
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
