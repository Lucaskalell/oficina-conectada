# Oficina Conectada · frontend

App Flutter para web e mobile, com gerenciamento de estado em BLoC. Consome a API do [backend](../backend). Visão geral no [README principal](../README.md).

## Rodando

```bash
flutter pub get
flutter run -d chrome
```

Por padrão o app chama `http://localhost:8080`. Para outro endereço:

```bash
flutter run --dart-define=API_URL=http://10.0.2.2:8080
```

`10.0.2.2` é o endereço do computador visto de dentro do emulador Android. Em um celular físico, use o IP do computador na rede local.

## Estrutura

```
lib/
├── core/        cliente HTTP com token JWT e constantes da API
├── models/      conversão das respostas da API
├── modules/     uma pasta por funcionalidade (page, bloc, event, state, service)
│   ├── auth/          login, primeiro acesso e recuperação de senha
│   ├── dashboard/
│   ├── ordens/
│   ├── clientes/
│   ├── veiculos/
│   ├── estoque/
│   ├── agendamento/
│   └── mecanicos/
├── shared/      componentes reutilizáveis, como o seletor de período
├── views/       shell da aplicação com menu lateral responsivo
└── widgets/     tabela, toast e cartões
```

## Verificação

```bash
flutter analyze
flutter test
```
