# oficina-conectada

API RESTful para gestão de oficinas mecânicas, desenvolvida com **Spring Boot**. O sistema foca na experiência do cliente e na gestão eficiente de ordens de serviço, estoque e vendas.

##  Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Security** (Autenticação e Autorização)
- **JWT (JSON Web Token)**
- **Spring Data JPA** (Persistência de dados)
- **Lombok** (Redução de boilerplate code)
- **Maven** (Gerenciamento de dependências)

##  Funcionalidades

###  Autenticação e Segurança
- Registro de novos usuários.
- Login via e-mail e senha retornando Token JWT.
- Proteção de rotas via filtro de autenticação (`Bearer Token`).

###  Gestão de Clientes e Veículos
- Cadastro completo de clientes (Nome, CPF, Telefone, Email).
- Cadastro de veículos vinculados ao cliente (Placa, Modelo, Marca, Ano, Cor).
- Possibilidade de criar Cliente e Carro em uma única requisição.

###  Controle de Estoque
- Organização hierárquica: **Categorias > Subcategorias > Produtos**.
- Controle de quantidade em estoque, preço de custo e preço de venda.
- Alertas de baixo estoque (via endpoint de resumo).

### 🛠 Ordens de Serviço (OS)
- Abertura de OS vinculando Cliente e Carro.
- Adição de itens de serviço (peças e mão de obra).
- Registro de defeitos e descrição do serviço.
- Upload de referências de fotos (caminho/legenda).
- Controle de Status: *EM_ANDAMENTO, AGUARDANDO_PECA, FINALIZADO, etc.*

###  Vendas
- Realização de vendas de produtos.
- Baixa automática no estoque ao concretizar a venda.

###  Dashboard
- Resumo financeiro (Faturamento mensal).
- Contagem de ordens abertas.
- Produtos com baixo estoque.
- Gráfico de vendas semanais.

## endpoints da API

### Autenticação (`/auth`)
- `POST /auth/register`: Registrar novo usuário.
- `POST /auth/login`: Autenticar e receber token.

### Clientes (`/clientes`)
- `GET /clientes`: Listar todos.
- `POST /clientes`: Criar cliente.
- `POST /clientes/completo`: Criar cliente já com carro vinculado.
- `GET /clientes/{id}/completo`: Buscar cliente com lista de carros.
- `PUT /clientes/{id}`: Atualizar dados.
- `DELETE /clientes/{id}`: Remover cliente.

### Estoque (`/estoque`)
- `GET /estoque/resumo`: Dados gerais do estoque (Total peças, itens baixo estoque, etc).
- `GET /estoque/categorias`: Listar todas as categorias.
- `GET /estoque/categorias/{id}/subCategorias`: Listar subcategorias de uma categoria.
- `GET /estoque/subCategorias/{id}/produtos`: Listar produtos de uma subcategoria.
- `GET /estoque/produtos/{id}`: Buscar dados detalhados de um produto.
- `GET /estoque/produtos/buscar?nome=...`: Buscar produtos por nome.
- `POST /estoque/categorias`: Criar categoria.
- `POST /estoque/categorias/{id}/subcategorias`: Criar subcategoria.
- `POST /estoque/subcategorias/{id}/produtos`: Criar produto.
- `PUT /estoque/produtos/{id}`: Atualizar dados do produto.
- `DELETE /estoque/produtos/{id}`: Remover produto.

### Ordens de Serviço (`/ordens`)
- `GET /ordens/resumo`: Lista resumida das OS com itens e fotos.
- `POST /ordens/criarOrdemDeServico`: Criar nova OS.

### Vendas (`/vendas`)
- `GET /vendas`: Histórico de vendas.
- `POST /vendas`: Realizar nova venda (valida e desconta estoque).

### Dashboard (`/dashboard`)
- `GET /dashboard/resumo`: Métricas para tela inicial.

##  Configuração e Execução

1. Clone o repositório.
2. Configure o banco de dados no `application.properties` (se necessário).
3. Execute o projeto via Maven:

```bash
mvn spring-boot:run
```

##  Configuração de Segurança

Por padrão, as seguintes rotas são públicas (não exigem token):
- `/auth/**`
- `/estoque/**`

Todas as outras rotas exigem o cabeçalho:
`Authorization: Bearer <seu_token_jwt>`

##  Estrutura do Projeto

- `config`: Configurações de Segurança e Web (CORS).
- `controllers`: Endpoints da API.
- `dto`: Objetos de transferência de dados (Request/Response).
- `entity`: Entidades do banco de dados (ORM).
- `repository`: Interfaces de acesso a dados.
- `service`: Regras de negócio.
- `security`: Filtros e lógica de JWT.
