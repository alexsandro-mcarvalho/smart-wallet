import { z } from "zod";

export const moneySchema = (description = "Informe um valor monetário válido") =>
  z.string().regex(/^\d+(\.\d{1,2})?$/, description);
