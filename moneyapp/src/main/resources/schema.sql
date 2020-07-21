create table if not exists users (
	username varchar(20) not null,
	password varchar(100) not null,
	enabled bit not null
);

create table if not exists authorities (
	username varchar(20) not null,
	authority varchar(20) not null
);

create table if not exists wallets (
	id identity,
	name varchar(100) not null,
	type varchar(100) not null,
	amount decimal(15,2) default 0 not null,
	entry_date timestamp not null,
	username varchar(20) not null,
	foreign key (username) references users(username)
);
 
create table if not exists expenses (
	id identity,
	name varchar(100) not null,
	amount decimal(15,2) default 0 not null,
	type varchar(100) not null,
	entry_date timestamp not null,
	id_wallet int not null,
	foreign key (id_wallet) references wallets(id)
);