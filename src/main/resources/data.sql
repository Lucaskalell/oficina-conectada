
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

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (5, 'Coxim do Amortecedor Dianteiro Axios Gol G5', 'Kit Coxim e Rolamento do Amortecedor Dianteiro (044.1456)', 60.00, 119.90, 20, 2);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (6, 'Mola Helicoidal Traseira Fabrini Gol G5', 'Par de Molas Traseiras (VW0268)', 150.00, 279.90, 12, 3);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (7, 'Kit Batente e Coifa Amortecedor Dianteiro Gol G5', 'Kit com 2 Batentes e 2 Coifas para amortecedor dianteiro', 35.00, 69.90, 30, 4);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (8, 'Bieleta da Barra Estabilizadora Nakata Gol G5', 'Bieleta Dianteira (N 93013)', 40.00, 75.00, 25, 5);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (9, 'Disco de Freio Dianteiro Fremax VW Gol G5', 'Par de Discos de Freio Ventilado Dianteiro (BD4733)', 180.00, 329.90, 10, 7);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (10, 'Tambor de Freio Traseiro Fremax Gol G5', 'Par de Tambores de Freio Traseiro (BD4744)', 160.00, 299.90, 8, 8);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (11, 'Lona de Freio Traseira Fras-le Gol G5', 'Jogo de Lonas de Freio com Haste (VW/252-CPA)', 70.00, 139.90, 15, 9);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (12, 'Jogo de Juntas do Motor Sabó Gol 1.6', 'Jogo de Juntas Completo com Retentores (79522FLEX)', 250.00, 450.00, 5, 10);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (13, 'Jogo de Pistões com Anéis Metal Leve Gol 1.6', 'Jogo de Pistões e Anéis para 4 cilindros (PA9123)', 400.00, 750.00, 4, 11);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (14, 'Jogo de Bielas Motor AP 1.6', 'Jogo com 4 bielas forjadas para motor AP', 800.00, 1500.00, 2, 12);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (15, 'Jogo de Anéis de Segmento Mahle Gol 1.6', 'Jogo de anéis para retífica de motor (A25120)', 120.00, 220.00, 10, 13);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (16, 'Óleo de Freio DOT 4 Varga', 'Fluido de Freio DOT 4 (500ml)', 15.00, 29.90, 40, 15);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (17, 'Óleo de Direção Hidráulica ATF Tutela', 'Fluido para Direção Hidráulica (1 Litro)', 30.00, 59.90, 30, 16);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (18, 'Filtro de Ar do Motor Mann-Filter Gol G5', 'Filtro de Ar do Motor (C 2969)', 25.00, 49.90, 50, 17);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (19, 'Serviço de Alinhamento 3D', 'Alinhamento computadorizado 3D para veículos de passeio', 50.00, 100.00, 999, 18);

INSERT IGNORE INTO produtos (id, nome, descricao, preco_custo, preco_venda, quantidade_em_estoque, subcategoria_id)
VALUES (20, 'Serviço de Balanceamento', 'Balanceamento de rodas com chumbo adesivado', 15.00, 30.00, 999, 19);


INSERT IGNORE INTO clientes (id, nome, email, telefone)
VALUES (1, 'Giyu Tomioka', 'giyu@email.com', '41999999999');

INSERT IGNORE INTO carros (id, modelo, placa, ano, marca, cliente_id)
VALUES (1, 'Lancer Evolution', 'ABC-1234', 2015, 'Mitsubishi', 1);

INSERT IGNORE INTO ordens_de_servico (id,status,defeito,descricao_servico,data_entrada,data_saida,valor_total,cliente_id,carro_id)
VALUES ( 1,'EM_ANDAMENTO','Pneu Furado','Troca de pneu dianteiro','2025-01-10 10:00:00',NULL,300.00,1,1);



INSERT IGNORE INTO clientes (id, nome, email, telefone)
VALUES (2, 'Tanjiro Kamado', 'kamadinho@gmail.com', '41988888888');


INSERT IGNORE INTO carros (id, modelo, placa, ano, marca, cliente_id)
VALUES (2, 'Gtr-35', 'ABC-9999', 2015, 'Nissan', 2);


INSERT IGNORE INTO ordens_de_servico (id,status,defeito,descricao_servico,data_entrada,data_saida,valor_total,cliente_id,carro_id)
VALUES ( 2,'FINALIZADO','Pastilha De Freio ','Troca do jogo de pastilha D,T','2025-01-11 10:00:00','2025-01-11 12:00:00',900.00,2,2);
