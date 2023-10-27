alter table cart
    add constraint fk_cart_to_dish
        foreign key (dish_id)
            references dish (id)
GO

alter table cart
    add constraint fk_cart_to_login
        foreign key (login_id)
            references login (id)
GO