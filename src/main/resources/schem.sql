CREATE TABLE orders
(
    order_id     BINARY(16) PRIMARY KEY,
    phone_number VARCHAR(20)  NOT NULL,
    address      varchar(200) NOT NULL,
    order_status varchar(50)  NOT NULL,
    created_at   datetime(6)  NOT NULL,
    updated_at   datetime(6) DEFAULT NULL
);

CREATE TABLE album
(
    album_id   BINARY(16) PRIMARY KEY,
    celebrity  VARCHAR(50) NOT NULL,
    album_name VARCHAR(30) NOT NULL,
    price      bigint      NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL
);

CREATE TABLE order_items
(
    sequence   bigint PRIMARY KEY AUTO_INCREMENT,
    order_id   BINARY(16)  NOT NULL,
    album_id   BINARY(16)  NOT NULL,
    celebrity   VARCHAR(50) NOT NULL,
    album_name VARCHAR(30) NOT NULL,
    price      mediumint   NOT NULL,
    quantity   smallint    NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    FOREIGN KEY (album_id) REFERENCES album (album_id)
);