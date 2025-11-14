
INSERT IGNORE INTO categorias (id, nome) VALUES (1, 'Suspensão');
INSERT IGNORE INTO categorias (id, nome) VALUES (2, 'Freio');
INSERT IGNORE INTO categorias (id, nome) VALUES (3, 'Motor');
INSERT IGNORE INTO categorias (id, nome) VALUES (4, 'Lubrificantes');
INSERT IGNORE INTO categorias (id, nome) VALUES (5, 'Alinhamento/Balanceamento');

INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (1, 'Amortecedor', 1);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (2, 'Coxim', 1);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (3, 'Mola', 1);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (4, 'Batentes', 1);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (5, 'Bieleta', 1);


INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (6, 'Pastilha de Freio', 2);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (7, 'Disco de Freio', 2);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (8, 'Tambor de Freio', 2);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (9, 'Lona de Freio', 2);

INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (10, 'Juntas', 3);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (11, 'Pistões', 3);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (12, 'Bielas', 3);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (13, 'Anéis', 3);

INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (14, 'Óleo de Motor', 4);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (15, 'Óleo de Freio', 4);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (16, 'Óleo de Direção', 4);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (17, 'Filtros', 4);

INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (18, 'Alinhamento', 5);
INSERT IGNORE INTO subcategorias (id, nome, categoria_id) VALUES (19, 'Balanceamento', 5);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (1, 'Pastilha Freio Dianteiro Cobreq VW Gol G5/G6', 'Jogo de pastilhas dianteiras N-293 Cobreq', 80.50, 149.90, 15, 6);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (2, 'Pastilha Freio Dianteiro Fras-le VW Gol G5/G6', 'Jogo de pastilhas dianteiras PD/1088 Fras-le', 95.00, 179.90, 10, 6);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (3, 'Amortecedor Dianteiro Cofap GP30111 Gol G5', 'Par Amortecedor Dianteiro Turbogás (GP30111)', 320.00, 499.90, 8, 1);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (4, 'Óleo de Motor 5W40 Sintético Castrol', 'Óleo Castrol Magnatec 5W40 (1 Litro)', 45.00, 79.90, 50, 14);