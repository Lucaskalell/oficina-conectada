# Oficina Conectada · backend

API REST em Spring Boot. Visão geral, módulos e instruções completas estão no [README principal](../README.md).

## Configuração

As variáveis podem ficar em um arquivo `.env` nesta pasta (veja `.env.example`).

| Variável | Obrigatória | Uso |
|---|---|---|
| `DB_URL`, `DB_USER`, `DB_PASSWORD` | sim | Conexão com o MySQL |
| `JWT_SECRET` | sim | Chave de assinatura dos tokens, em Base64, com pelo menos 32 bytes |
| `ADMIN_EMAIL` | não | E-mail do primeiro administrador (padrão `admin@oficina.com`) |
| `ADMIN_SENHA_INICIAL` | não | Senha inicial do administrador. Se vazia, uma senha aleatória é gerada e escrita no log |
| `EMAIL_HABILITADO` | não | `true` envia o código de redefinição por SMTP. `false` (padrão) escreve o código no log |
| `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `EMAIL_REMETENTE` | com e-mail habilitado | Servidor SMTP |

## Endpoints

| Recurso | Base | Operações |
|---|---|---|
| Autenticação | `/auth` | login, registrar (admin), solicitar e confirmar redefinição, alterar senha |
| Dashboard | `/dashboard` | resumo |
| Ordens de serviço | `/ordens` | criar, resumo, detalhe, fotos, histórico de status |
| Clientes | `/clientes` | CRUD, cadastro com veículo, listagem com veículos |
| Veículos | `/carros` | CRUD e status |
| Estoque | `/estoque` | resumo, categorias, subcategorias, produtos e busca por nome |
| Vendas | `/vendas` | listar e registrar |
| Agendamentos | `/agendamentos` | CRUD, busca por período e troca de status |
| Mecânicos | `/mecanicos` | CRUD e desativação |

## Pacotes

```
io.github.lucaskalell.oficinaconectada
├── config/       segurança, CORS e criação do administrador inicial
├── controllers/  endpoints REST
├── dto/          objetos de entrada e saída da API
├── entity/       entidades JPA
├── exception/    exceções de negócio e tratamento global de erros
├── mapper/       conversão entre entidades e DTOs
├── repository/   acesso a dados
├── security/     filtro e serviço de JWT
├── service/      regras de negócio
└── status/       enums de domínio
```

As migrations ficam em `src/main/resources/db/migration`.

## Comandos

```bash
./mvnw spring-boot:run
./mvnw verify
```
