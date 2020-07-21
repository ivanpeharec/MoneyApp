delete from users;
delete from authorities;
delete from expenses;
delete from wallets;

insert into users (username, password, enabled)
values ('admin', '$2a$10$oP8vlljgbvqBiLa0c5V4UOf6M.YvnMXaRIJ6pUKiHxcZXMHHZve5e', 1);

insert into users (username, password, enabled)
values ('user', '$2a$10$7nvV7wvvNAWbgj70f8ILZedFmMNgYwLXqEyn5ehmNcdKczpzozyyS', 1);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('user', 'ROLE_USER');

insert into wallets values (1, 'Moja gotovina', 'VLASTITI', 0.0, '2017-03-04 14:32:32', 'admin');

insert into expenses values (1, 'firstExpense', 35.50, 'HRANA', '2017-03-04 14:32:32', 1);
insert into expenses values (2, 'secondExpense', 300, 'OBUCA', '2017-03-04 14:32:32', 1);
insert into expenses values (3, 'thirdExpense', 2000, 'ODJECA', '2017-03-04 14:32:32', 1);