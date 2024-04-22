
-------------------- TB_CATEGORY -------------------------
INSERT INTO tb_category (category_name) values ('Romance');
INSERT INTO tb_category (category_name) values ('Drama');
INSERT INTO tb_category (category_name) values ('Ficção Científica');

---------------------- TB_USER ---------------------------
INSERT INTO tb_users (name, password) VALUES ('João Silva', 'senha123');
INSERT INTO tb_users (name, password) VALUES ('Maria Oliveira', 'senha456');
INSERT INTO tb_users (name, password) VALUES ('Carlos Pereira', 'senha789');

---------------------- TB_PRODUCT---------------------------
INSERT INTO tb_products (name, description, price, url_image, category_id) VALUES ('1984', 'A dystopian novel by George Orwell.', 20.90, 'https://example.com/1984.jpg', 2);
INSERT INTO tb_products (name, description, price, url_image, category_id) VALUES ('Dom Casmurro', 'A classic of Brazilian literature by Machado de Assis.', 15.50, 'https://example.com/domcasmurro.jpg', 1);
INSERT INTO tb_products (name, description, price, url_image, category_id) VALUES ('The Giver', 'Talks about feelings and society organization', 15.50, 'https://example.com/domcasmurro.jpg', 1);

