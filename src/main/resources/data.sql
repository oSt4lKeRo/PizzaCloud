delete from Ingredient_Ref;
delete from Pizza;
delete from Pizza_Order;

delete from Ingredient;
insert into Ingredient (id, name, type)
values ('CHDO', 'Cheese Dough', 'WRAP');
insert into Ingredient (id, name, type)
values ('CLDO', 'Classic Dough', 'WRAP');
insert into Ingredient (id, name, type)
values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into Ingredient (id, name, type)
values ('CARN', 'Carnitas', 'PROTEIN');
insert into Ingredient (id, name, type)
values ('TMTO', 'Tomatoes', 'VEGGIES');
insert into Ingredient (id, name, type)
values ('LETC', 'Lettuce', 'VEGGIES');
insert into Ingredient (id, name, type)
values ('CHED', 'Cheddar', 'CHEESE');
insert into Ingredient (id, name, type)
values ('MZRL', 'Mozzarella', 'CHEESE');
insert into Ingredient (id, name, type)
values ('PSTO', 'Pesto', 'SAUCE');
insert into Ingredient (id, name, type)
values ('TMSA', 'Tomato Sauce', 'SAUCE');