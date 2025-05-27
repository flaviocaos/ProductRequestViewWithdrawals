
# ProductRequestViewWithdrawals

## 📦 Descrição

`ProductRequestViewWithdrawals` é uma ação do módulo de requisições de produtos no sistema **Firsti ERP**. Este recurso permite exibir uma lista das **retiradas de produtos (WarehouseWithdrawals)** vinculadas a uma requisição específica, disponível apenas quando o status da requisição está como **PROCESSING** ou **PROCESSED**.

## 🔧 Funcionalidades

- Listagem de retiradas vinculadas à requisição de produto.
- Visualização de detalhes: data de disponibilidade, divisão, colaborador, tipo de produto, modelo, quantidade e status.
- Exibição de imagem do produto associado.
- Filtro por status da retirada.
- Ordenação por data de disponibilidade (mais recentes primeiro).
- Abertura de detalhes da retirada em janela popup (`WarehouseWithdrawalView`).
- Suporte à seleção múltipla.

## 🏗️ Tecnologias Utilizadas

- Java
- Jakarta Persistence API (JPA)
- Framework interno Firsti ERP
- Integrações com:
  - Módulo de Estoque (WarehouseWithdrawal)
  - Módulo Organizacional
  - Módulo de Produtos

## 🚀 Instalação e Execução

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

2. Navegue até o módulo:

```bash
cd seu-repositorio/src/main/java/br/com/firsti/packages/stock/modules/productRequest/actions
```

3. Compile o projeto:

```bash
mvn clean install
```

4. Execute no ambiente do Firsti ERP.

## 📄 Licença

Este projeto é proprietário da **Firsti Tecnologia**. Uso restrito conforme contrato de licença.

## 📞 Contato

- Empresa: Firsti Tecnologia
- Site: [www.firsti.com.br](https://www.firsti.com.br)
- E-mail: suporte@firsti.com.br
