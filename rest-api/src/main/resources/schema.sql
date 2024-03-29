CREATE TABLE if not exists "User" (
    ID integer NOT NULL AUTO_INCREMENT,
    firstName varchar(30) NOT NULL,
    lastName varchar(30) NOT NULL,
    postalCode varchar(5) NOT NULL,
    country varchar(50) NOT NULL,
    city varchar(50)  NOT NULL,
    address varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    credits double,
    PRIMARY KEY (ID)
);

CREATE TABLE if not exists UserLogin (
    ID integer NOT NULL AUTO_INCREMENT,
    userID integer NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (userID) REFERENCES "User"(ID)
);

CREATE TABLE if not exists Color (
    ID integer NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE if not exists Category (
    ID integer NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE if not exists Product (
    ID integer NOT NULL AUTO_INCREMENT,
    colorID integer NOT NULL,
    categoryID integer NOT NULL,
    name varchar(50) NOT NULL,
    price double NOT NULL,
    description varchar(550) NOT NULL,
    sku varchar(50) NOT NULL,
    stock integer NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (colorID) REFERENCES Color(ID),
    FOREIGN KEY (categoryID) REFERENCES Category(ID)
);

CREATE TABLE if not exists ProductImage (
    ID integer NOT NULL AUTO_INCREMENT,
    productID integer NOT NULL,
    imageTitle varchar(50) NOT NULL,
    imageURL varchar(550) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (productID) REFERENCES Product(ID)
);

CREATE TABLE if not exists FavoriteProduct (
    ID integer NOT NULL AUTO_INCREMENT,
    userID integer NOT NULL,
    productID integer NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (userID) REFERENCES "User"(ID),
    FOREIGN KEY (productID) REFERENCES Product(ID)
);

CREATE TABLE if not exists Cart (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    userID integer NOT NULL,
    productID integer NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (userID) REFERENCES "User"(ID),
    FOREIGN KEY (productID) REFERENCES Product(ID)
);