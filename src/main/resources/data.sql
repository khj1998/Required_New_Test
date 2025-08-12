INSERT INTO users (id, nickname, os, app_version, status,point, created_at, updated_at) VALUES
    (1, '테스트_유저', 'iOS', '1.0.0', 'ACTIVE',10000 ,CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


INSERT INTO products (id, name, price, stock, created_at, updated_at) VALUES
    (1, '스프링 완전 정복 책', 45000.00, 10000, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());