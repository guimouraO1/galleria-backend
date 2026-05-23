CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(254) NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(254) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    phone VARCHAR(20),
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(254) NOT NULL,
    value DECIMAL(15,2) NOT NULL,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    reference_code VARCHAR(100),
    issued_at TIMESTAMP NOT NULL,
    description VARCHAR(254),
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_orders_client
        FOREIGN KEY (client_id)
            REFERENCES clients(id)
);

CREATE TABLE order_products (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
         CONSTRAINT fk_order_products_order
             FOREIGN KEY (order_id)
                 REFERENCES orders(id)
                 ON DELETE CASCADE,
         CONSTRAINT fk_order_products_product
             FOREIGN KEY (product_id)
                 REFERENCES products(id)
);