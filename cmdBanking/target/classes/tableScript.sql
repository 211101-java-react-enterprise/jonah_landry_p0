-- Table creation/reset for p0

/*
 *  Creates the tables for users, accounts and transactions
 * users
 *  - password varchar(25)
 *  - username varchar(25) unique not null
 *  - user_id varchar(36) constraint user_pk primary key
 *  - first_name varchar(25)
 *  - last_name varchar(25)
 *  - email
 *
 * accounts
 *  - balance numeric(11,2)
 *  - account_id varchar(36) constraint account_pk primary key
 *
 * account_users
 *  - account_id varchar(36)
 *  - user_id varchar(36)
 *
 * transactions
 *  - account_id varchar(36)
 *  - user_id varchar(36)
 *  - transaction_id varchar(36) constraint transaction_pk primary key
 *  - amount numeric(11,2)
 *
 *
 */
drop table if exists app_users;
drop table if exists accounts;
drop table if exists account_users;
drop table if exists transactions;

create table app_users (
    user_id varchar check (user_id <> ''),
    first_name varchar(25) not null check (first_name <> ''),
    last_name varchar(25) not null check (last_name <> ''),
    email varchar(255) unique not null check (email <> ''),
    username varchar(20) unique not null check (username <> ''),
    password varchar(255) not null check (password <> ''),

    constraint app_users_pk
    primary key (user_id)
);

create table accounts (
	balance numeric(11,2),
	account_id varchar check (account_id <> ''),
	account_name varchar(25) not null check (account_name <> ''),

	constraint accounts_pk
	primary key (account_id)
);

create table account_users (
	account_id varchar,
	user_id varchar,
	constraint fk_user
		foreign key(user_id)
			references app_users(user_id),
	constraint fk_account_id
		foreign key(account_id)
			references accounts(account_id)
);

create table transactions (
	transaction_id varchar check (transaction_id <> ''),
	account1_id varchar,
	account2_id varchar,
	user_id varchar,
	amount numeric(11,2) check (amount != 0),

	constraint transactions_pk
	primary key (transaction_id),
	constraint fk_user
		foreign key(user_id)
			references app_users(user_id),
	constraint fk_account_id
		foreign key(account_id)
			references accounts(account_id)
);

