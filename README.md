# Smart Wallet

## Descrição

O **Smart Wallet** é uma aplicação backend para gerenciamento de uma carteira de investimentos, permitindo registrar operações de compra de ativos, como **ações e criptomoedas**, e acompanhar a evolução da carteira ao longo do tempo.

Ao realizar uma transação compra/venda, são registradas informações como o ativo, data da transação, quantidade, tipo de ativo e valor total da operação. Essas informações são persistidas no banco de dados utilizando uma abordagem baseada em **Ledger**, garantindo a rastreabilidade e a consistência do histórico de transações.

Além do histórico de transações, a aplicação mantém uma estrutura com os holdings de cada usuário atualizada de forma transacional em cada nova transação. Essa abordagem permite obter de forma rápida os ativos que pertencem ao usuário pra criação das dashboard por exemplo sem precisar reconstruir o estado atual da carteira a partir de todas as transações a cada consulta, proporcionando maior eficiência nas leituras.

### Valorização dos ativos

Para apresentar uma estimativa atualizada do valor da carteira, a aplicação possui **Schedulers** responsáveis por consultar periodicamente APIs externas de cotação. A frequência das consultas pode variar de acordo com o tipo de ativo devido a limitação de chamadas de API e variações de preço.

As cotações obtidas são armazenadas no **Redis**, utilizado como cache, permitindo que as consultas à carteira utilizem preços recentes sem a necessidade de realizar uma nova requisição à API externa a cada consulta do usuário.

Por exemplo, considerando que um usuário compre **1 Bitcoin por R$ 100.000**:

- Valor da compra: R$ 100.000
- Quantidade: 1 BTC
- Cotação atual no momento da compra: R$ 100.000

Após 30 minutos, caso o Bitcoin esteja cotado a **R$ 120.000**, o valor atual estimado da posição será:

- Quantidade: 1 BTC
- Cotação atual: R$ 120.000
- Valor atual da posição: R$ 120.000
- Lucro: R$ 20.000

Dessa forma, o sistema consegue diferenciar o **valor investido originalmente** do **valor atual da posição**, permitindo acompanhar a valorização ou desvalorização dos ativos.

Além do valor aproximado atual da carteira junto com lucro/prejuízo, a aplicação também disponibiliza informações como:

- **Histórico de transações**
- **Lista de ativos disponíveis**
- **Preço médio dos ativos**