create table customer (
    customer_id int not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique ,
    mobile_number varchar(20) not null,
    pwd varchar(600) not null,
    role varchar(30) not null,
    create_dt date default null,
    primary key (customer_id)
);

insert into customer(name, email, mobile_number, pwd, role, create_dt)
values ('Admin', 'admin@gmail.com', '1234567890', '{bcrypt}$2y$10$K3Y/MphKHIuNld4qi/4YyepFEREl3RlSMyb86kfj9UagO0nqYNSQa', 'admin', now()),
       ('User', 'user@gmail.com', '0987654321', '{noop}user', 'user', now());

insert into customer(name, email, mobile_number, pwd, role, create_dt)
values ('Eros', 'eros@gmail.com', '9999999999', '{bcrypt}$2y$10$tRm9C0Mn3YR1Y72f62Kf/OlI8N28cHd2UiHTk9K1o.hqrOpbBOyuG', 'admin', now());

insert into customer(name, email, mobile_number, pwd, role, create_dt)
values ('Curtomer', 'curtomer@gmail.com', '1111111111', '{bcrypt}$2y$10$K3Y/MphKHIuNld4qi/4YyepFEREl3RlSMyb86kfj9UagO0nqYNSQa', 'user', now());

select * from customer;

 create table accounts (
     customer_id int not null,
     account_number int not null,
     account_type varchar(100) not null,
     branch_address varchar(200) not null,
     create_dt date default null,
     primary key (account_number),
     key customer_id (customer_id),
     constraint  fk_accounts_customer foreign key (customer_id) references customer (customer_id) on delete cascade
 );
insert into accounts(customer_id, account_number, account_type, branch_address, create_dt)
select c.customer_id, 1234567890, 'Saving', '123 Main St', now()
from customer c
where c.email = 'eros@gmail.com';

select * from accounts;

create table account_transactions (
    transaction_id varchar(200) not null,
    account_number int not null,
    customer_id int not null,
    transaction_dt date not null,
    transaction_summary varchar(200) not null,
    transaction_type varchar(100) not null,
    transaction_amt int not null,
    closing_balance int not null,
    create_dt date default null,
    primary key (transaction_id),
    key customer_id (customer_id),
    key account_number (account_number),
    constraint account_ibfk_2 foreign key (account_number) references accounts (account_number) on delete cascade,
    constraint account_customer_ibfk_2 foreign key (customer_id) references customer (customer_id) on delete cascade
);

insert into account_transactions(transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt)
select concat('eros-trans-', c.customer_id), a.account_number, c.customer_id, now(), 'Initial Deposit', 'Deposit', 1000, 1000, now()
from customer c, accounts a
where c.email = 'eros@gmail.com' and a.customer_id = c.customer_id;


insert into account_transactions(transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt)
select concat('eros-trans-', floor(rand()*1000000000000)+1), a.account_number, c.customer_id, now(), 'Deposit', 'Deposit', 3000, 1000, now()
from customer c, accounts a
where c.email = 'eros@gmail.com' and a.customer_id = c.customer_id;

select concat('eros-trans-', ), a.account_number, c.customer_id, now(), 'Deposit', 'Deposit', 3000, 1000, now()
from customer c, accounts a
where c.email = 'eros@gmail.com' and a.customer_id = c.customer_id;

select * from account_transactions;

create table loans (
    loan_number int not null auto_increment,
    customer_id int not null,
    start_dt date not null,
    loan_type varchar(200) not null,
    total_load int not null,
    amount_paid int not null,
    outstanding_amount  int not null,
    create_dt date default null,
    primary key (loan_number),
    key customer_id (customer_id),
    constraint fk_loans_customer foreign key (customer_id) references customer (customer_id) on delete cascade
);

insert into loans(customer_id, start_dt, loan_type, total_load, amount_paid, outstanding_amount, create_dt)
select c.customer_id, now(), 'Personal Loan', 10000, 0, 10000, now()
from customer c
where c.email = 'eros@gmail.com';

insert into loans(customer_id, start_dt, loan_type, total_load, amount_paid, outstanding_amount, create_dt)
select c.customer_id, now(), 'Personal Loan', 10000, 2000, 10000, now()
from customer c
where c.email = 'eros@gmail.com';


insert into loans(customer_id, start_dt, loan_type, total_load, amount_paid, outstanding_amount, create_dt)
select c.customer_id, now(), 'Personal Loan', 10000, 2000, 10000, now()
from customer c
where c.email = 'eros@gmail.com';


create table cards (
    card_id int not null auto_increment,
    card_number varchar(500) not null,
    customer_id int not null,
    card_type varchar(100) not null,
    total_limit int not null,
    amount_used int not null,
    available_amount int not null,
    create_dt date default null,
    primary key (card_id),
    key customer_id (customer_id),
    constraint card_customer_ibkf foreign key (customer_id) references customer (customer_id) on delete cascade
);

insert into cards(card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
select '1234 5678 9012 3456', c.customer_id, 'Visa Gold', 1000000, 0, 10000, now()
from customer c
where c.email = 'eros@gmail.com';

select * from cards;


create table notice_details (
    notice_id int not null,
    notice_summary varchar(200) not null,
    notice_details varchar(500) not null,
    notic_beg_dt date not null,
    notic_end_dt date default null,
    create_dt date default null,
    update_dt date default null,
    primary key (notice_id)
);

insert into notice_details(notice_id, notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values (1, 'Dummy Notice', 'This is a dummy notice', now(), null, now(), null),
       (2, 'Dummy Notice 2', 'This is a dummy notice 2', now(), now(), now(), null),
       (3, 'Dummy Notice 3', 'This is a dummy notice 3', now(), null, now(), null);

create table contact_message (
    contact_id varchar(50) not null,
    contact_name varchar(50) not null,
    contact_email varchar(100) not null,
    subject varchar(500) not null,
    message varchar(2000) not null,
    create_dt date default null,
    primary key (contact_id)
);

select * from customer c
where c.email = 'eros@gmail.com';

delete from customer
where email like 'test%';

select * from notice_details;