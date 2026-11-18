"use client";

import type { ComponentProps } from "react";

import Input from "@/components/input";
import { formatCurrencyInput, parseCurrencyInput } from "@/lib/utils/currency-input";

type CurrencyInputProps = Omit<ComponentProps<typeof Input>, "value" | "onChange" | "type" | "inputMode"> & {
  value: string;
  onValueChange: (value: string) => void;
};

export default function CurrencyInput({ value, onValueChange, ...props }: CurrencyInputProps) {
  return (
    <Input
      {...props}
      type="text"
      inputMode="numeric"
      value={formatCurrencyInput(value)}
      onKeyDown={(event) => {
        if (event.ctrlKey || event.metaKey || event.altKey) return;

        if (event.key.length === 1 && !/^\d$/.test(event.key)) {
          event.preventDefault();
        }
      }}
      onPaste={(event) => {
        if (!/^\d+$/.test(event.clipboardData.getData("text"))) event.preventDefault();
      }}
      onChange={(event) => {
        const input = event.currentTarget;
        const nativeEvent = event.nativeEvent as InputEvent;

        if (nativeEvent.data && /\D/.test(nativeEvent.data)) {
          input.value = formatCurrencyInput(value);
          return;
        }

        const isClearingZero = nativeEvent.inputType?.startsWith("delete") && value === "0.00";
        const nextValue = isClearingZero ? "" : parseCurrencyInput(input.value);
        onValueChange(nextValue);
        input.value = formatCurrencyInput(nextValue);
        input.setSelectionRange(input.value.length, input.value.length);
      }}
    />
  );
}
