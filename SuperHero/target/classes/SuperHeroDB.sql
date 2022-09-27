DROP DATABASE IF EXISTS superHeroDB;
CREATE DATABASE superHeroDB;

USE superHeroDB;



CREATE TABLE hero(
id Int PRIMARY KEY auto_increment,
heroName varchar(30) NOT NULL,
heroDescription VARCHAR(300),
super_power varchar(50)
);

CREATE TABLE location(
id INT PRIMARY KEY auto_increment,
name VARCHAR(50),
address VARCHAR(150) not null,
description VARCHAR(150),
latitude varchar(20) not null,
longitude varChar(20) not null
);

CREATE TABLE orginization(
id INT PRIMARY KEY auto_increment,
name VARCHAR(100) not null,
address VARCHAR(200) not null,
description VARCHAR(100),
contactInfo varchar(50),
allignment varChar(4)
);

CREATE TABLE sighting(
id int Primary KEY AUTO_INCREMENT,
hero INT NOT NULL,
location INT NOT NULL,
date DATETIME NOT NULL,
constraint FOREIGN KEY (location) references location(id),
constraint FOREIGN KEY (hero) references hero(id)
);


CREATE TABLE member(
    hero INT NOT NULL,
    orginization INT NOT NULL,
    constraint pk_member primary key (hero, orginization),
    constraint foreign key (hero) references hero(id),
    constraint foreign key (orginization) references orginization(id)
);
select * from location;
select * from hero;

