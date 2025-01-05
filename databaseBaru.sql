CREATE TABLE login(
	id SERIAL PRIMARY KEY,
	username varchar(255) UNIQUE NOT NULL,
	password varchar(255) NOT NULL,
	role varchar(255)
)

INSERT INTO login(username, password, role) VALUES ('admin', 'admin123', 'admin')

CREATE TABLE users(
	id_user SERIAL PRIMARY KEY,
	nama varchar(255) NOT NULL,
	no_telepon varchar(255) NOT NULL
)

INSERT INTO users(nama, no_telepon) VALUES ('coba', '08123456789')

CREATE TABLE orders(
	no_pesanan SERIAL PRIMARY KEY,
	id_user int REFERENCES users(id_user),
	menu varchar(255) NOT NULL,
	jumlah int NOT NULL
)

ALTER SEQUENCE orders_no_pesanan_seq RESTART WITH 101;

INSERT INTO orders(id_user, menu, jumlah) VALUES (1, 'Ayam', 1)
