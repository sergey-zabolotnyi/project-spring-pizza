-- ТЕСТОВЫЕ ДАННЫЕ ДЛЯ ПРОЕКТА ПИЦЦА...
INSERT INTO login (login, password, email, role, time)
VALUES
    ('manager', '$2a$10$1Y1PNzXea52hQZNAW0d0FeW6Iadlf7hGWFZLzO.rFgiHuONHF/Xu.', 'cvz@ukr.net', 'ROLE_MANAGER', CURRENT_TIMESTAMP),
    ('admin', '$2a$10$mSqNtzYREM0m8qwbYyl5QOG2Z2vWHxk5NDywuLIEXVKnAGYobKKj.', 'admin@gmail.com', 'ROLE_MANAGER', CURRENT_TIMESTAMP),
    ('root', '$2a$10$mSqNtzYREM0m8qwbYyl5QOG2Z2vWHxk5NDywuLIEXVKnAGYobKKj.', 'root@root.ru', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP),
    ('alona', '$2a$10$q1O1K.mLbATHzkK1192lae4rQb.TtVWu/GHQ.dtPa8dmjK4blFMDi', 'alona@ukr.net', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP),
    ('user', '$2a$10$EcEIw7f.Wmiw8lpHbiFTQu76O8yc4SltaXjfBsiNJLGhKT7vhBWKm', 'user@mail.ru', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);

INSERT INTO category (category_en, category_ru)
VALUES
    ('Pizzas', 'Пиццы'),
    ('Snacks', 'Закуски'),
    ('Salads', 'Салаты'),
    ('Dessert', 'Десерты'),
    ('Soft drinks', 'Безалкогольные напитки'),
    ('Alcoholic drinks', 'Алкогольные напитки');

INSERT INTO dish (name_en, name_ru, price, time, category_id)
VALUES
    ('Margherita', 'Маргарита', 10.50, CURRENT_TIMESTAMP, 1),
    ('Pepperoni', 'Пепперони', 11.00, CURRENT_TIMESTAMP, 1),
    ('Quattro Formaggi', 'Четыре сыра', 12.50, CURRENT_TIMESTAMP, 1),
    ('Hawaiian', 'Гавайская', 11.50, CURRENT_TIMESTAMP, 1),
    ('Vegetarian', 'Вегетарианская', 10.00, CURRENT_TIMESTAMP, 1),
    ('Margarita with vegetables', 'Маргарита с овощами', 11.00, CURRENT_TIMESTAMP, 1),
    ('Capricciosa', 'Капричоза', 11.50, CURRENT_TIMESTAMP, 1),
    ('Seafood Delight', 'Морское удовольствие', 13.00, CURRENT_TIMESTAMP, 1),
    ('Spinach and Feta', 'Шпинат и Фета', 10.50, CURRENT_TIMESTAMP, 1),
    ('Fried mozzarella sticks', 'Жареные палочки из моцареллы', 6.50, CURRENT_TIMESTAMP, 2),
    ('Garlic bread', 'Чесночный хлеб', 5.00, CURRENT_TIMESTAMP, 2),
    ('Bruschetta', 'Брускетта', 7.00, CURRENT_TIMESTAMP, 2),
    ('Stuffed mushrooms', 'Фаршированные грибы', 8.00, CURRENT_TIMESTAMP, 2),
    ('Onion Rings', 'Кольца лука', 6.50, CURRENT_TIMESTAMP, 2),
    ('Mozzarella Caprese', 'Моцарелла Капрезе', 7.00, CURRENT_TIMESTAMP, 2),
    ('Loaded Nachos', 'Начос с начинкой', 8.50, CURRENT_TIMESTAMP, 2),
    ('Bacon-wrapped Shrimp', 'Креветки в беконе', 9.00, CURRENT_TIMESTAMP, 2),
    ('Caprese salad', 'Салат Капрезе', 9.50, CURRENT_TIMESTAMP, 3),
    ('Greek salad', 'Греческий салат', 9.00, CURRENT_TIMESTAMP, 3),
    ('Caesar salad', 'Салат Цезарь', 8.50, CURRENT_TIMESTAMP, 3),
    ('Spinach and Strawberry Salad', 'Салат со шпинатом и клубникой', 8.50, CURRENT_TIMESTAMP, 3),
    ('Mediterranean Quinoa Salad', 'Салат с киноа по-средиземноморски', 8.50, CURRENT_TIMESTAMP, 3),
    ('Tiramisu', 'Тирамису', 7.50, CURRENT_TIMESTAMP, 4),
    ('Cheesecake', 'Чизкейк', 8.00, CURRENT_TIMESTAMP, 4),
    ('Chocolate fondant', 'Шоколадный фондан', 9.00, CURRENT_TIMESTAMP, 4),
    ('Apple Pie', 'Яблочный пирог', 6.00, CURRENT_TIMESTAMP, 4),
    ('Strawberry Shortcake', 'Клубничный торт', 8.50, CURRENT_TIMESTAMP, 4),
    ('Lemon Tart', 'Лимонный тарт', 7.50, CURRENT_TIMESTAMP, 4),
    ('Coca-Cola', 'Кока-Кола', 2.00, CURRENT_TIMESTAMP, 5),
    ('Pepsi', 'Пепси', 2.00, CURRENT_TIMESTAMP, 5),
    ('Sprite', 'Спрайт', 2.00, CURRENT_TIMESTAMP, 5),
    ('Fanta', 'Фанта', 2.00, CURRENT_TIMESTAMP, 5),
    ('Lemonade', 'Лимонад', 2.00, CURRENT_TIMESTAMP, 5),
    ('Iced Tea', 'Холодный чай', 2.50, CURRENT_TIMESTAMP, 5),
    ('Red wine', 'Красное вино', 12.50, CURRENT_TIMESTAMP, 6),
    ('White wine', 'Белое вино', 11.50, CURRENT_TIMESTAMP, 6),
    ('Beer', 'Пиво', 3.50, CURRENT_TIMESTAMP, 6),
    ('Whiskey', 'Виски', 10.50, CURRENT_TIMESTAMP, 6),
    ('Vodka', 'Водка', 9.50, CURRENT_TIMESTAMP, 6),
    ('Tequila', 'Текила', 11.00, CURRENT_TIMESTAMP, 6)

INSERT INTO orders (user_id,total_price,status,time)
VALUES
    (1,35.00,'DELIVERY', CURRENT_TIMESTAMP),
    (2,34.50,'PAYMENT_CONFIRM', CURRENT_TIMESTAMP),
    (3,45.50,'PAYED',CURRENT_TIMESTAMP),
    (4,33.00,'PAYED',CURRENT_TIMESTAMP),
    (5,76.50,'PAYED',CURRENT_TIMESTAMP),
    (1,64.00,'PAYED',CURRENT_TIMESTAMP),
    (2,43.20,'PAYED',CURRENT_TIMESTAMP),
    (3,38.50,'PAYED',CURRENT_TIMESTAMP),
    (4,53.20,'PAYED',CURRENT_TIMESTAMP),
    (5,59.89,'PAYED',CURRENT_TIMESTAMP);