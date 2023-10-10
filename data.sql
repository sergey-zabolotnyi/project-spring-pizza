-- ТЕСТОВЫЕ ДАННЫЕ ДЛЯ ПРОЕКТА ПИЦЦА...
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
    -- Добавьте еще пиццы по аналогии

    ('Fried mozzarella sticks', 'Жареные палочки из моцареллы', 6.50, CURRENT_TIMESTAMP, 2),
    ('Garlic bread', 'Чесночный хлеб', 5.00, CURRENT_TIMESTAMP, 2),
    ('Bruschetta', 'Брускетта', 7.00, CURRENT_TIMESTAMP, 2),
    ('Stuffed mushrooms', 'Фаршированные грибы', 8.00, CURRENT_TIMESTAMP, 2),
    ('Caprese salad', 'Салат Капрезе', 9.50, CURRENT_TIMESTAMP, 3),
    ('Greek salad', 'Греческий салат', 9.00, CURRENT_TIMESTAMP, 3),
    ('Caesar salad', 'Салат Цезарь', 8.50, CURRENT_TIMESTAMP, 3),
    -- Добавьте еще закусок по аналогии

    ('Tiramisu', 'Тирамису', 7.50, CURRENT_TIMESTAMP, 4),
    ('Cheesecake', 'Чизкейк', 8.00, CURRENT_TIMESTAMP, 4),
    ('Chocolate fondant', 'Шоколадный фондан', 9.00, CURRENT_TIMESTAMP, 4),
    -- Добавьте еще десертов по аналогии

    ('Coca-Cola', 'Кока-Кола', 2.00, CURRENT_TIMESTAMP, 5),
    ('Pepsi', 'Пепси', 2.00, CURRENT_TIMESTAMP, 5),
    ('Sprite', 'Спрайт', 2.00, CURRENT_TIMESTAMP, 5),
    -- Добавьте еще безалкогольных напитков по аналогии

    ('Red wine', 'Красное вино', 12.50, CURRENT_TIMESTAMP, 6),
    ('White wine', 'Белое вино', 11.50, CURRENT_TIMESTAMP, 6),
    ('Beer', 'Пиво', 3.50, CURRENT_TIMESTAMP, 6);