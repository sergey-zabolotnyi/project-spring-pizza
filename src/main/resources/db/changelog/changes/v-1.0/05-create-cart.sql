create table cart (
    id int not null auto_increment,
    dish_id int not null,
    login_id int not null,
    primary key (id)
) engine=InnoDB
GO
