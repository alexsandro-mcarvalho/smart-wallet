# Form Guidelines — Zod & React Hook Form

## Overview

Use **Zod** for schema validation and **React Hook Form** for form state management.

Keep forms simple, readable, maintainable, and type-safe.

## Zod Guidelines

- Keep schemas as simple, concise, and readable as possible.
- Prefer built-in Zod validators over custom validation logic.
- Avoid unnecessary `regex()`, `refine()`, `superRefine()`, and `transform()`.
- Use custom validation only when built-in validators cannot express the requirement.
- Keep validation messages clear and user-friendly.
- Avoid unnecessary schema abstractions and complex validation chains.
- Infer TypeScript types from schemas using `z.infer` when input and output types are identical.

## React Hook Form Guidelines

- Always use React Hook Form to manage form state.
- Always integrate Zod validation using `zodResolver`.
- Set `mode: "onTouched"` as the default validation strategy.
- Use `formState` to manage validation errors and submission state.
- Prefer `register()` for native inputs.
- Prevent duplicate submissions using `isSubmitting`.

## Example

```tsx
const loginSchema = z.object({
  email: z.email("Invalid email address."),
  password: z.string().min(1, "Password is required."),
});

type LoginFormValues = z.infer<typeof loginSchema>;

const {
  register,
  handleSubmit,
  formState: { errors, isSubmitting },
} = useForm<LoginFormValues>({
  resolver: zodResolver(loginSchema),
  mode: "onTouched"
});
```

## Core Principles

1. **Simplicity first:** Prefer straightforward schemas and form implementations.
2. **Single responsibility:** Zod validates data; React Hook Form manages form state.
3. **Minimal complexity:** Do not introduce custom validators, transformations, or abstractions without a concrete requirement.
4. **Type safety:** Derive form types from Zod schemas whenever possible.
5. **Consistency:** Follow existing form patterns throughout the project.
