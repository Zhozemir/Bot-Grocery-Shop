CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    location_x INT NOT NULL,
    location_y INT NOT NULL
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(10) NOT NULL
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_item_order FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_product FOREIGN KEY(product_id) REFERENCES products(id)
);

CREATE TABLE routes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    seq INT NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    CONSTRAINT fk_route_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);