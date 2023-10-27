create table login (
    id int not null auto_increment,
    email varchar(255) not null,
    login varchar(255) not null,
    password varchar(255) not null,
    role varchar(45) not null,
    time timestamp not null,
    primary key (id)
) engine=InnoDB
GO

alter table login
    add constraint c unique (login)
GO