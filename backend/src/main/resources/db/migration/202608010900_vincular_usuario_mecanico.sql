ALTER TABLE mecanicos
    ADD COLUMN usuario_id BIGINT NOT NULL AFTER telefone,
    ADD CONSTRAINT uq_mecanico_usuario UNIQUE (usuario_id),
    ADD CONSTRAINT fk_mecanico_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
