create table product (
                        id serial primary key,
                        title varchar(128),
                        price integer
);


insert into product (title, price) values ('Cheese', 310), ('Bread', 40), ('Milk', 80);