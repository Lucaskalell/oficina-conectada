CREATE TABLE historico_status_os (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    ordem_de_servico_id BIGINT NOT NULL,
    status_anterior     VARCHAR(30) NULL,
    status_novo         VARCHAR(30) NOT NULL,
    usuario_id          BIGINT NULL,
    observacao          VARCHAR(255) NULL,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historico_os      FOREIGN KEY (ordem_de_servico_id) REFERENCES ordens_de_servico(id),
    CONSTRAINT fk_historico_usuario FOREIGN KEY (usuario_id)          REFERENCES usuarios(id)
);
