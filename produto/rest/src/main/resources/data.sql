INSERT INTO CATEGORIA(codigo, nome) VALUES('9', 'Gamer');
INSERT INTO CATEGORIA(codigo, nome) VALUES('8', 'Celulares');

INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, categoria_entity_id) VALUES ('100', 'COD', 'Jogo de FPS', 250, 10, 1);
INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, categoria_entity_id) VALUES ('150', 'COD2', 'Jogo de FPS', 350, 10, 1);
INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, is_Disponivel, categoria_entity_id) VALUES ('200', 'GOD OF WAR','deus da guerra', 150, 0, false, 1);
INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, is_Disponivel, categoria_entity_id) VALUES ('122', 'FONE','fone bluetooth', 100, 3, true, 2);
INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, is_Disponivel, categoria_entity_id) VALUES ('55', 'IPHONE','Iphone 13 com carregador', 2500, 5, true, 2);
INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, is_Disponivel, categoria_entity_id) VALUES ('56', 'SAMSUNG','S22 com carregador', 2000, 0, false, 2);
INSERT INTO PRODUTO(codigo, nome, descricao, valor, quantidade, is_Disponivel, categoria_entity_id) VALUES ('57', 'XIAOMI','Celular com fone e carregador', 900, 8, true, 2);