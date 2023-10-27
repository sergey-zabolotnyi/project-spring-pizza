create table category (
    id int not null auto_increment,
    category_en varchar(255) not null,
    category_ru varchar(255) not null,
    primary key (id)
) engine=InnoDB
GO