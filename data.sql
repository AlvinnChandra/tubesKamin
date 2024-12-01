CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');
INSERT INTO users (username, password, role) VALUES ('pemilik', 'pemilik123', 'pemilik');


CREATE TABLE pegawai (
    id SERIAL PRIMARY KEY, 
    nama VARCHAR(100) NOT NULL,
    posisi VARCHAR(100) NOT NULL
);

CREATE TABLE mesin (
    id SERIAL PRIMARY KEY,
    merek VARCHAR(255) UNIQUE NOT NULL,
    kapasitas INT NOT NULL,
    tarif INT NOT NULL
);

INSERT INTO mesin (merek, kapasitas, tarif) VALUES ('LG', '20', '15000');
INSERT INTO mesin (merek, kapasitas, tarif) VALUES ('Panasonic', '10', '10000');


-- Membuat tabel kecamatan
CREATE TABLE kecamatan (
    id SERIAL PRIMARY KEY,
    nama_kecamatan VARCHAR(100) NOT NULL
);

-- Membuat tabel kelurahan
CREATE TABLE kelurahan (
    id SERIAL PRIMARY KEY,
    nama_kelurahan VARCHAR(100) NOT NULL,
    kecamatan_id INT REFERENCES kecamatan(id) ON DELETE CASCADE
);

-- Menambahkan data kecamatan
INSERT INTO kecamatan (nama_kecamatan)
VALUES
('Andir'),
('Astanaanyar'),
('Antapani'),
('Arcamanik'),
('Babakan Ciparay'),
('Bandung Kidul'),
('Bandung Kulon'),
('Bandung Wetan'),
('Batununggal'),
('Bojongloa Kaler'),
('Bojongloa Kidul'),
('Buahbatu'),
('Cibeunying Kaler'),
('Cibeunying Kidul'),
('Cibiru'),
('Cicendo'),
('Cidadap'),
('Cinambo'),
('Coblong'),
('Gedebage'),
('Kiaracondong'),
('Lengkong'),
('Mandalajati'),
('Panyileukan'),
('Rancasari'),
('Regol'),
('Sukajadi'),
('Sukasari'),
('Sumur Bandung'),
('Ujung Berung');

-- Menambahkan data kelurahan
INSERT INTO kelurahan (nama_kelurahan, kecamatan_id)
VALUES
-- Kecamatan Andir
('Campaka', 1),
('Ciroyom', 1),
('Dunguscariang', 1),
('Garuda', 1),
('Kebon Jeruk', 1),
('Maleber', 1),

-- Kecamatan Astanaanyar
('Cibadak', 2),
('Karanganyar', 2),
('Karasak', 2),
('Nyengseret', 2),
('Panjunan', 2),
('Pelindunghewan', 2),

-- Kecamatan Antapani
('Antapani Kidul', 3),
('Antapani Wetan', 3),
('Antapani Tengah', 3),
('Cisaranten Kulon', 3),

-- Kecamatan Arcamanik
('Cisaranten Bina Harapan', 4), 
('Cisaranten Endah', 4),
('Cisaranten Kulon', 4),
('Sukamiskin', 4),

-- Kecamatan Babakan Ciparay
('Babakan', 5),
('Babakan Ciparay', 5),
('Margahayu Utara', 5),
('Sukahaji', 5),
('Cirangrang', 5),
('Margasuka', 5),

-- Kecamatan Bandung Kidul
('Batununggal', 6),
('Kujangsari', 6),
('Mengger', 6),
('Wates', 6),

--Kecamatan Bandung Kulon
('Caringin', 7),
('Cibuntu', 7),
('Cigondewah Kaler', 7),
('Cigondewah Kidul', 7),
('Cigondewah Rahayu', 7),
('Cijerah', 7),
('Gempolsari', 7),
('Warungmuncang', 7),

-- Kecamatan Bandung Wetan
('Cihapit', 8),
('Citarum', 8),
('Tamansari', 8),

-- Kecamatan Batununggal
('Binong', 9),
('Cibangkong', 9),
('Gumuruh', 9),
('Kacapiring', 9),
('Kebongedang', 9),
('Kebonwaru', 9),
('Maleer', 9),
('Samoja', 9),

-- Kecamatan Bojongloa Kaler
('Babakan Asih', 10),
('Babakan Tarogong', 10),
('Jamika', 10),
('Kopo', 10),
('Suka Asih', 10),

-- Kecamatan Bojongloa Kidul
('Cibaduyut', 11),
('Cibaduyut Kidul', 11),
('Cibaduyut Wetan', 11),
('Kebon Lega', 11),
('Mekarwangi', 11),
('Situsaeur', 11),

-- Kecamatan Buahbatu
('Cijawura', 12),
('Jatisari', 12),
('Margasari', 12),
('Sekejati', 12),

-- Kecamatan Cibeunying Kaler
('Cigadung', 13),
('Cihaurgeulis', 13),
('Neglasari', 13),
('Sukaluyu', 13),

-- Kecamatan Cibeunying Kidul
('Cicadas', 14),
('Cikutra', 14),
('Padasuka', 14),
('Pasirlayung', 14),
('Sukamaju', 14),
('Sukapada', 14),

-- Kecamatan Cibiru
('Cipadung', 15),
('Cisurupan', 15),
('Palasari', 15),
('Pasirbiru', 15),

-- Kecamatan Cicendo
('Arjuna', 16),
('Husen Sastranegara', 16),
('Pajajaran', 16),
('Pamoyanan', 16),
('Pasirkaliki', 16),
('Sukaraja', 16),

-- Kecamatan Cidadap
('Ciumbuleuit', 17),
('Hegarmanah', 17),
('Ledeng', 17),

-- Kecamatan Cinambo
('Babakan Penghulu', 18),
('Cisaranten Wetan', 18),
('Pakemitan', 18),
('Sukamulya', 18),

-- Kecamatan Coblong
('Cipaganti', 19),
('Dago', 19),
('Lebakgede', 19),
('Lebaksiliwangi', 19),
('Sadangserang', 19),
('Sekeloa', 19),

-- Kecamatan Gedebage
('Cimincrang', 20),
('Cisaranten Kidul', 20),
('Rancabolang', 20),
('Rancanumpang', 20),

-- Kecamatan Kiaracondong
('Babakansari', 21),
('Babakansurabaya', 21),
('Cicaheum', 21),
('Kebonkangkung', 21),
('Kebunjayanti', 21),
('Sukapura', 21),

-- Kecamatan Lengkong
('Burangrang', 22),
('Cijagra', 22),
('Cikawao', 22),
('Lingkar Selatan', 22),
('Malabar', 22),
('Paledang', 22),
('Turangga', 22),

-- Kecamatan Mandalajati
('Jatihandap', 23),
('Karangpamulang', 23),
('Pasir Impun', 23),
('Sindangjaya', 23),

-- Kecamatan Panyileukan
('Cipadung Kidul', 24),
('Cipadung Kulon', 24),
('Cipadung Wetan', 24),
('Mekarmulya', 24),

-- Kecamatan Rancasari
('Cipamokolan', 25),
('Darwati', 25),
('Manjahlega', 25),
('Mekar Jaya', 25),

-- Kecamatan Regol
('Ancol', 26),
('Balonggede', 26),
('Ciateul', 26),
('Cigereleng', 26),
('Ciseureuh', 26),
('Pasirluyu', 26),
('Pungkur', 26),

-- Kecamatan Sukajadi
('Cipedes', 27),
('Pasteur', 27),
('Sukabungah', 27),
('Sukagalih', 27),
('Sukawarna', 27),

-- Kecamatan Sukasari
('Gegerkalong', 28),
('Isola', 28),
('Sarijadi', 28),
('Sukarasa', 28),

-- Kecamatan Sumur Bandung
('Babakanciamis', 29),
('Braga', 29),
('Kebonpisang', 29),
('Merdeka', 29),

-- Kecamatan Ujungberung
('Cigending', 30),
('Pasanggrahan', 30),
('Pasirendah', 30),
('Pasirjati', 30),
('Pasirwangi', 30);

