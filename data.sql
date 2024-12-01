CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');


CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
	menu VARCHAR(100) NOT NULL,
 	jumlah INT NOT NULL,
	nama VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
