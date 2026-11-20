
## Project Overview

This project is a web application built with Next.js, React, and TypeScript.
The goal is to maintain a clean, scalable, and maintainable codebase while following modern React and Next.js best practices.
The project should use SSG strategy to generate static files at build time and for dynamic content should use CSR to fetch data in cliente side

## Tech Stack

- Next.js (App Router)
- React
- TypeScript
- Tailwind CSS
- shadcn/ui
- React Hook Form + Zod
- SWR for data fetching and caching

## Project Structure

- `src/app/` — Routes, layouts, pages and private componentes that belongs just to that context.
- `src/components/` — Shared and reusable components.
- `src/hooks/` — Reusable custom React hooks.
- `src/lib/` — Utilities, helpers, and shared configurations.
- `src/services/` — API clients and HTTP-related logic.
- `src/schemas/` - Zod schemas
- `src/types/` — Shared TypeScript types and interfaces.


Follow the existing project structure. Do not introduce new directories or architectural patterns without a clear reason.

<!-- BEGIN:nextjs-agent-rules -->
## This is NOT the Next.js you know
This version has breaking changes — APIs, conventions, and file structure may all differ from your training data. Read the relevant guide in `node_modules/next/dist/docs/` (resolved from this file's directory; in monorepos the `next` package may not be visible from the repo root) before writing any code. Heed deprecation notices.
This block is written and re-added by `next dev` — verify at `node_modules/next/dist/server/lib/generate-agent-files.js`. Removing it from a diff only re-creates the uncommitted change; committing it with your work keeps the tree clean.
<!-- END:nextjs-agent-rules -->


## Coding Guidelines

- Use TypeScript with strict typing.
- Avoid `any` unless absolutely necessary.
- Prefer readable and maintainable code over clever abstractions.
- Use descriptive names for variables, functions, and components.
- Keep components small and focused on a single responsibility.
- Avoid unnecessary abstractions and premature optimization.
- Prefer existing utilities and components over creating duplicates.
- Follow the existing formatting and naming conventions.


## Styling conventions
- Reference `docs/styling.md` for detailed styling conventions


## Forms conventions
- Reference `docs/forms.md` for detailed Forms conventions

## Commits convetions
- Reference `docs/commits.md` for detailed Commits conventions
