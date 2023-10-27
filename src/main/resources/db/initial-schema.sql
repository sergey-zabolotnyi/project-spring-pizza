create table category (
                          id int not null auto_increment,
                          category_en varchar(255) not null,
                          category_ru varchar(255) not null,
                          primary key (id)
);

create table dish (
                      id int not null auto_increment,
                      name_en varchar(45) not null,
                      name_ru varchar(45) not null,
                      price decimal(9,2) not null,
                      category_id int not null,
                      time timestamp not null,
                      primary key (id),
                      key fk_dish_category_idx (category_id),
                      constraint fk_dish_category foreign key (category_id) references category (id)
);

create table login (
                       id int not null auto_increment,
                       email varchar(255) not null,
                       login varchar(255) not null,
                       password varchar(255) not null,
                       role varchar(45) not null,
                       time timestamp not null,
                       primary key (id),
                       unique key login_unique (login)
);

create table orders (
                        id int not null auto_increment,
                        login_id int not null ,
                        total_price decimal(9,2) not null,
                        status varchar(45) not null,
                        time timestamp not null,
                        primary key (id),
                        key fk_order_login_idx (login_id),
                        constraint fk_order_login foreign key (login_id) references login (id)
);

create table cart (
                      id int not null auto_increment,
                      dish_id int not null,
                      login_id int not null,
                      primary key (id),
                      key fk_cart_to_dish_idx (dish_id),
                      key fk_cart_to_login_idx (login_id),
                      constraint fk_cart_to_dish foreign key (dish_id) references dish (id),
                      constraint fk_cart_to_login foreign key (login_id) references login (id)
);