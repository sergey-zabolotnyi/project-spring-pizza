insert into login (login, password, email, role, time)
values
    ('manager', '$2a$10$1Y1PNzXea52hQZNAW0d0FeW6Iadlf7hGWFZLzO.rFgiHuONHF/Xu.', 'cvz@ukr.net', 'ROLE_MANAGER', CURRENT_TIMESTAMP),
    ('admin', '$2a$10$mSqNtzYREM0m8qwbYyl5QOG2Z2vWHxk5NDywuLIEXVKnAGYobKKj.', 'admin@gmail.com', 'ROLE_MANAGER', CURRENT_TIMESTAMP),
    ('root', '$2a$10$mSqNtzYREM0m8qwbYyl5QOG2Z2vWHxk5NDywuLIEXVKnAGYobKKj.', 'root@root.ru', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP),
    ('alona', '$2a$10$q1O1K.mLbATHzkK1192lae4rQb.TtVWu/GHQ.dtPa8dmjK4blFMDi', 'alona@ukr.net', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP),
    ('user', '$2a$10$EcEIw7f.Wmiw8lpHbiFTQu76O8yc4SltaXjfBsiNJLGhKT7vhBWKm', 'user@mail.ru', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP)
GO