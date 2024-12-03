CREATE TABLE login(
	id SERIAL PRIMARY KEY,
	username varchar(255) UNIQUE NOT NULL,
	password varchar(255) NOT NULL,
	role varchar(255)
)

CREATE TABLE users(
	id_user SERIAL PRIMARY KEY,
	nama varchar(255) NOT NULL,
	no_telepon varchar(255) NOT NULL
)


CREATE TABLE orders(
	no_pesanan SERIAL PRIMARY KEY,
	id_user int REFERENCES users(id_user)
)

ALTER SEQUENCE orders_no_pesanan_seq RESTART WITH 101;

CREATE TABLE order_items(
	id SERIAL PRIMARY KEY,
	no_pesanan INT REFERENCES orders(no_pesanan),
	menu VARCHAR(255) NOT NULL,
	jumlah INT NOT NULL
)

INSERT INTO login(username, password, role) VALUES ('admin', 'admin123', 'admin')

