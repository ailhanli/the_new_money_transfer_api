drop database bank_db;

create table account(
	account_id SERIAL primary key,
	name varchar(100),
	balance float,
	currency char(3)
);

create table transfer(
	transfer_id SERIAL primary key,
	source_account_id int,
	destination_account_id int,
	amount float,
	currency char(3),
	comment varchar(200)
);

insert into account values(1, 'Omer', 500, 'EUR');
insert into account values(2, 'Abdullah', 1000, 'EUR');