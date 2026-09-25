ALTER TABLE ordens_de_servico
    ADD COLUMN cliente_id BIGINT NULL AFTER carro_id,
    ADD CONSTRAINT fk_os_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);

UPDATE ordens_de_servico os
    JOIN carros c ON c.id = os.carro_id
    SET os.cliente_id = c.cliente_id;

ALTER TABLE ordens_de_servico
    MODIFY COLUMN cliente_id BIGINT NOT NULL;

ALTER TABLE agendamentos
    ADD COLUMN cliente_id BIGINT NULL AFTER carro_id,
    ADD CONSTRAINT fk_agendamento_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);

UPDATE agendamentos a
    JOIN carros c ON c.id = a.carro_id
    SET a.cliente_id = c.cliente_id;

ALTER TABLE agendamentos
    MODIFY COLUMN cliente_id BIGINT NOT NULL;

ALTER TABLE itens_servico
    ADD COLUMN produto_id BIGINT NULL AFTER ordem_de_servico_id,
    ADD CONSTRAINT fk_item_servico_produto FOREIGN KEY (produto_id) REFERENCES produtos(id);
