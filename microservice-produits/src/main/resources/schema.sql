CREATE TABLE product
(
    id          INT PRIMARY KEY,
    titre       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image       VARCHAR(255) NOT NULL,
    prix        INT          NOT NULL
);