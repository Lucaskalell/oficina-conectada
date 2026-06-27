CREATE TABLE usuarios (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    senha      VARCHAR(255) NOT NULL,
    role       ENUM('ADMIN','ATENDENTE','MECANICO') NOT NULL DEFAULT 'ATENDENTE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL
);

CREATE TABLE tokens_redefinicao_senha (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    token      VARCHAR(255) NOT NULL UNIQUE,
    expires_at DATETIME NOT NULL,
    used       BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_token_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE clientes (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NULL,
    telefone   VARCHAR(20) NULL,
    cpf        VARCHAR(14) NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL
);

CREATE TABLE mecanicos (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100) NULL,
    telefone     VARCHAR(20) NULL,
    ativo        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE carros (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    modelo     VARCHAR(100) NOT NULL,
    marca      VARCHAR(100) NULL,
    placa      VARCHAR(8) NOT NULL UNIQUE,
    ano        INT NULL,
    cor        VARCHAR(50) NULL,
    cliente_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_carro_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE categorias (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE subcategorias (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    categoria_id BIGINT NOT NULL,
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_subcategoria_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT uq_subcategoria_nome_categoria UNIQUE (nome, categoria_id)
);

CREATE TABLE produtos (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome                 VARCHAR(150) NOT NULL,
    descricao            TEXT NULL,
    preco_custo          DECIMAL(10,2) NOT NULL,
    preco_venda          DECIMAL(10,2) NOT NULL,
    quantidade_em_estoque INT NOT NULL DEFAULT 0,
    quantidade_minima    INT NOT NULL DEFAULT 0,
    subcategoria_id      BIGINT NOT NULL,
    created_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at           DATETIME NULL,
    CONSTRAINT fk_produto_subcategoria FOREIGN KEY (subcategoria_id) REFERENCES subcategorias(id)
);

CREATE TABLE ordens_de_servico (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    defeito            VARCHAR(255) NOT NULL,
    descricao_servico  VARCHAR(500) NULL,
    status             ENUM('NAO_INICIADO','EM_ANDAMENTO','AGUARDANDO_PECA','AGUARDANDO_RETIRADA','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'NAO_INICIADO',
    prioridade         ENUM('BAIXA','MEDIA','ALTA','URGENTE') NULL,
    valor_total        DECIMAL(10,2) NULL,
    data_entrada       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_saida         DATETIME NULL,
    carro_id           BIGINT NOT NULL,
    mecanico_id        BIGINT NULL,
    created_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_os_carro    FOREIGN KEY (carro_id)    REFERENCES carros(id),
    CONSTRAINT fk_os_mecanico FOREIGN KEY (mecanico_id) REFERENCES mecanicos(id)
);

CREATE TABLE itens_servico (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao            VARCHAR(255) NOT NULL,
    quantidade           DECIMAL(10,2) NOT NULL,
    valor_unitario       DECIMAL(10,2) NOT NULL,
    ordem_de_servico_id  BIGINT NOT NULL,
    created_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_item_servico_os FOREIGN KEY (ordem_de_servico_id) REFERENCES ordens_de_servico(id)
);

CREATE TABLE fotos_ordem_de_servico (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    caminho_foto        VARCHAR(500) NOT NULL,
    legenda             VARCHAR(255) NULL,
    ordem_de_servico_id BIGINT NOT NULL,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_foto_os FOREIGN KEY (ordem_de_servico_id) REFERENCES ordens_de_servico(id)
);

CREATE TABLE agendamentos (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_hora            DATETIME NOT NULL,
    descricao_servico    VARCHAR(255) NOT NULL,
    status               ENUM('AGENDADO','CONCLUIDO','CANCELADO') NOT NULL DEFAULT 'AGENDADO',
    carro_id             BIGINT NOT NULL,
    mecanico_id          BIGINT NULL,
    ordem_de_servico_id  BIGINT NULL,
    created_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_agendamento_carro    FOREIGN KEY (carro_id)             REFERENCES carros(id),
    CONSTRAINT fk_agendamento_mecanico FOREIGN KEY (mecanico_id)          REFERENCES mecanicos(id),
    CONSTRAINT fk_agendamento_os       FOREIGN KEY (ordem_de_servico_id)  REFERENCES ordens_de_servico(id)
);

CREATE TABLE vendas (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor_total DECIMAL(10,2) NOT NULL,
    cliente_id  BIGINT NULL,
    usuario_id  BIGINT NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_venda_cliente  FOREIGN KEY (cliente_id)  REFERENCES clientes(id),
    CONSTRAINT fk_venda_usuario  FOREIGN KEY (usuario_id)  REFERENCES usuarios(id)
);

CREATE TABLE itens_venda (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade     INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    produto_id     BIGINT NOT NULL,
    venda_id       BIGINT NOT NULL,
    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_item_venda_produto FOREIGN KEY (produto_id) REFERENCES produtos(id),
    CONSTRAINT fk_item_venda_venda   FOREIGN KEY (venda_id)   REFERENCES vendas(id)
);
