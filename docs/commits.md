## Git Commit Guidelines

- Follow the **Conventional Commits** specification.
- Write all commit messages in **Portuguese** using only **ASCII characters**.
- Keep commit messages concise, clear, and descriptive.
- Split changes into **small, atomic commits**, each representing a single logical change.
- Avoid combining unrelated changes into the same commit.
- Write descriptions in the imperative mood.
- Stage only files relevant to the current logical change.

### Allowed Commit Types

- `feat`: Introduce a new feature.
- `fix`: Fix a bug.
- `refactor`: Restructure code without changing behavior.
- `style`: Make formatting changes without affecting behavior.
- `test`: Add or update tests.
- `docs`: Update documentation.
- `chore`: Perform maintenance tasks.
- `perf`: Improve performance.
- `build`: Modify dependencies or build configuration.
- `ci`: Update CI/CD configuration.

### Examples

- feat: adiciona formulario de criacao de transacoes
- fix: corrige validacao de quantidade negativa
- refactor: extrai logica de formatacao de valores
- style: padroniza formatacao dos componentes
- test: adiciona testes de validacao de transacoes
- docs: atualiza instrucoes de configuracao do projeto
- chore: atualiza dependencias do projeto
- perf: otimiza consulta de precos no cache
