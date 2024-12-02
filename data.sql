CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');


CREATE TABLE orders (
    id SERIAL PRIMARY KEY,           -- ID unik untuk pesanan
    nama VARCHAR(255) NOT NULL,      -- Nama pemesan
    phone VARCHAR(20) NOT NULL,      -- Nomor telepon pemesan
    order_date TIME DEFAULT CURRENT_TIMESTAMP -- Waktu pesanan
);

CREATE TABLE order_details (
    id SERIAL PRIMARY KEY,           -- ID unik untuk detail pesanan
    order_id INT NOT NULL,           -- ID pesanan (foreign key ke tabel `orders`)
    menu VARCHAR(100) NOT NULL,      -- Nama menu yang dipesan
    jumlah INT NOT NULL,             -- Jumlah pesanan menu
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE -- Hubungan dengan tabel `orders`
);

CREATE VIEW order_summary AS
SELECT
    o.id AS idPesanan,
    od.menu,
    o.nama AS namaPemesan,
    o.phone AS noTelepon,
    o.order_date
FROM
    orders o
JOIN
    order_details od ON o.id = od.order_id;

