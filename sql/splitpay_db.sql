--
-- Table structure for table `users`
--
drop table users cascade; 
CREATE TABLE users (
	userId serial PRIMARY KEY,
	email varchar(30) NOT NULL,
	displayName varchar(30) NOT NULL,
	isOffline boolean NOT NULL
);

alter table users add avatar_url text default 
'data:image/jpeg;base64,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';

-- alter table users drop column isOffline;
-- alter table users add column isOffline boolean;

--
-- Table structure for table `group`
--

drop table paygroups cascade;

CREATE TABLE paygroups (
	groupId serial PRIMARY KEY,
	displayName varchar(30) NOT NULL,
	leaderUserId integer references users,
	isActive boolean NOT NULL
);
-- alter table paygroup drop column isActive;
-- alter table paygroup add column isActive boolean;

--
-- Table structure for table `member`
--
drop table members cascade; 

CREATE TABLE members (
	memberId serial primary key,
	userId integer NOT NULL references users,
	groupId integer NOT NULL references paygroups,
	balance integer NOT NULL -- ?
);

--
-- Table structure for table `bill`
--
drop table bills cascade;

CREATE TABLE bills (
	billId serial PRIMARY KEY,
	amount integer NOT NULL,
	displayName varchar(30) NOT NULL,
	payer integer NOT NULL references members
);

--
-- Table structure for table `billPayees`
--
delete from involvedBillPayees;
drop table involvedBillPayees cascade; 

CREATE TABLE involvedBillPayees (
	id serial primary key,
	billId integer NOT NULL references bills,
	memberId integer NOT NULL references members
);

-- alter table member add column memberId serial constraint member_primary_key primary key;
-- alter table member add constraint member_foreign_user foreign key (userId) references users(userId);
-- alter table member add constraint member_foreign_group foreign key (groupId) references paygroup(groupId);

--
-- Table structure for table `transactions`
--
delete from transactions
drop table transactions cascade;

CREATE TABLE transactions (
	transactionId serial PRIMARY KEY,
	toMember integer NOT NULL references members,
	fromMember integer NOT NULL references members,
	amount decimal(10,0) NOT NULL CHECK (amount > 0)
);

-- alter table transactions add constraint transactions_foreign_group foreign key (groupId) references paygroup(groupId);
-- alter table transactions add constraint transactions_foreign_to_member foreign key (toMember) references member(memberId);
-- alter table transactions add constraint transactions_foreign_from_member foreign key (fromMember) references member(memberId);


select * from users;
select * from paygroups;
select * from bills;
select * from members; 
select * from transactions;
select * from involvedbillpayees;