
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS inventories;

CREATE TABLE users(
	id_user SERIAL PRIMARY KEY,
	nama VARCHAR(255) NOT NULL,
	passwords VARCHAR(255) NOT NULL,
	no_telepon varchar(255) NOT NULL,
	peran VARCHAR(255) NOT NULL
);

INSERT INTO users(nama, passwords, no_telepon, peran) VALUES 
('cust1', 'cust1', '098765432111', 'customer'),
('admin1', 'admin1', '123456789000', 'admin');

CREATE TABLE orders(
	no_pesanan SERIAL PRIMARY KEY,
	id_user int REFERENCES users(id_user),
	menu varchar(255) NOT NULL,
	jumlah int NOT NULL
);

ALTER SEQUENCE orders_no_pesanan_seq RESTART WITH 101;

CREATE TABLE inventories(
	id_menu SERIAL PRIMARY KEY,
	nama VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	price DECIMAL(10, 2) NOT NULL,
	stock INT NOT NULL
);

INSERT INTO inventories (nama, description, price, stock) VALUES
('Nasi Goreng', 'Nasi goreng dengan bumbu spesial dan tambahan telur mata sapi.', 20000.00, 20),
('Ayam Bakar', 'Ayam bakar dengan bumbu rempah khas dan sambal pedas.', 25000.00, 30),
('Soto Ayam', 'Soto ayam dengan kuah gurih dan pelengkap seperti telur dan perkedel.', 18000.00, 40),
('Ayam Goreng', 'Ayam goreng renyah dengan bumbu tradisional yang meresap hingga ke dalam, disajikan dengan sambal dan lalapan segar.', 18000.00, 40),
('Teh Tarik', 'Minuman penghilang dahaga saat haus', 10000.00, 80);