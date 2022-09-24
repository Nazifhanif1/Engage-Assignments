drop database if exists superheroSightingsDB;
create database superheroSightingsDB;

use superheroSightingsDB;

create table superhero(
superheroId int primary key auto_increment,
superheroName varchar(50) not null,
description varchar(250),
superpower varchar(50) not null
);

create table organisation(
organisationId int primary key auto_increment,
organisationName varchar(50) not null,
address varchar(200) not null,
organisationDescription varchar(250)
);

create table location(
locationId int primary key auto_increment,
locationName varchar(50) not null,
description varchar(250),
address varchar(200) not null,
latitude double not null,
longitude double not null
);

CREATE TABLE sighting(
    sightingId int primary key auto_increment,
	heroId INT NOT NULL,
    locId INT NOT NULL,
    sightingDate DATETIME NOT NULL,
    FOREIGN KEY (heroId) REFERENCES superhero(superheroId),
    FOREIGN KEY (locId) REFERENCES location(locationId)
);

create table super_organisation(
heroId int not null,
orgId int not null,
primary key(heroId, orgId),
foreign key(heroId) references superhero(superheroId),
foreign key(orgId) references organisation(organisationId)
);