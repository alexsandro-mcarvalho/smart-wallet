import { z } from "zod";

import { moneySchema } from "./common/money.schema";

export const createTransactionSchema = z
  .object({
    assetId: z.int().positive("Selecione um ativo."),
    quantity: z.string().regex(/^(?=.*[1-9])\d*(?:\.\d*)?$/, "A quantidade deve ser maior que zero."),
    price: moneySchema(),
    type: z.enum(["BUY", "SELL"], { error: "Selecione Compra ou Venda." }),
    transactionDate: z.date({ error: "Selecione uma data válida." }),
    transactionTime: z.iso.time({ precision: -1, error: "Informe um horário válido com horas, minutos" }),
  })
  .transform(({ transactionDate, transactionTime, ...data }) => {
    const [hours, minutes] = transactionTime.split(":").map(Number);
    const transactionAt = new Date(transactionDate.getTime());
    transactionAt.setHours(hours, minutes, 0, 0);

    return { ...data, transactionAt: transactionAt.toISOString() };
  });

export type CreateTransactionFormValues = z.input<typeof createTransactionSchema>;
export type CreateTransactionFormData = z.output<typeof createTransactionSchema>;
