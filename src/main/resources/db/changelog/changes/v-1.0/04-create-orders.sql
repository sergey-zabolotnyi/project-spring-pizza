create table orders (
    id int not null auto_increment,
    login_id int not null ,
    total_price decimal(9,2) not null,
    status varchar(45) not null,
    time timestamp not null,
    primary key (id)
) engine=InnoDB
GO

alter table orders
    add constraint fk_order_login
        foreign key (login_id)
            references login (id)
GO