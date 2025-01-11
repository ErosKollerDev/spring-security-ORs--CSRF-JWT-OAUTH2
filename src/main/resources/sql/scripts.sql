create table users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index ix_auth_username on authorities (username, authority);


insert into users (username, password, enabled)
values ('user', '{noop}user', true);

insert into authorities (username, authority)
values ('user', 'read');


insert into users (username, password, enabled)
values ('springsecurityuser',
        '{bcrypt}$2y$10$zWddi6AS4rGszcZB9KE8E.qOBT20Y1VzdT8wuf76Ox/zjCiCPyF/u',
        true);
#pass -> zerospringsecurity

update users
set password = '{bcrypt}$2y$10$KNq7kVw.Xqh0pXlFI2X.VOyri8RVCIscST/06.URFabJbpDVO5kbO'
where username = 'springsecurityuser';

insert into authorities (username, authority)
values ('springsecurityuser', 'admin');

insert into authorities (username, authority)
values ('springsecurityuser', 'WRITE_PRIVILEGE');


insert into users (username, password, enabled)
values ('admin', '{noop}admin', true);

update users
set password = '{noop}caraphodademais$123'
where username = 'admin';

insert into authorities (username, authority)
values ('admin', 'admin');


insert into users (username, password, enabled)
values ('eros', '{noop}eros', true);

insert into authorities (username, authority)
values ('eros', 'MASTER_OF_THE_UNIVERSE');



select * from users u , authorities a
where u.username = a.username
  and u.username = 'eros'

select * from users where username = 'springsecurityadmin';


select count(*) as quantity_of_users from users;


create table customer (
    id  int not null AUTO_INCREMENT,
    email varchar(80) not null,
    pwd varchar(600) not null,
    role varchar(30) not null,
    primary key (id)
);

select * from customer;

insert into customer(email, pwd, role)
values ('eroskoller@gmail.com', '{bcrypt}$2y$10$K3Y/MphKHIuNld4qi/4YyepFEREl3RlSMyb86kfj9UagO0nqYNSQa', 'admin');

insert into customer(email, pwd, role)
values ('admin@gmail.com', '{noop}admin', 'admin');

insert into customer(email, pwd, role)
values ('user@gmail.com', '{noop}user', 'user');

select * from customer;
update customer
set pwd = '{bcrypt}$2y$10$tRm9C0Mn3YR1Y72f62Kf/OlI8N28cHd2UiHTk9K1o.hqrOpbBOyuG'
where email = 'eroskoller@gmail.com';

