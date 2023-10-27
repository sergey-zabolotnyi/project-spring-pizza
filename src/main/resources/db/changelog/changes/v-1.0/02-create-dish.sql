create table dish (
    id int not null auto_increment,
    name_en varchar(45) not null,
    name_ru varchar(45) not null,
    price decimal(9,2) not null,
    category_id int not null,
    time timestamp not null,
    primary key (id)
) engine=InnoDB
GO

alter table dish
    add constraint fk_dish_category
        foreign key (category_id)
            references category (id)
GO