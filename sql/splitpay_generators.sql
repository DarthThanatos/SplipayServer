create or replace function gen_first_name(generator int) 
returns text
as $first_name$
	with syllarr as (
		select '{CO,GE,FOR,SO,CO,GIM,SE,CO,GE,CA,FRA,GEC,GE,GA,FRO,GIP}'::text[] as start_arr
	), string_parts as (
		select start_arr[ 1 + ( (random() * 25)::int ) % 16 ] as x
		FROM syllarr, generate_series(1, 3)
	)
	SELECT string_agg(x,'') as name_first
	FROM string_parts
$first_name$ language sql

create or replace function gen_last_name(generator int) 
returns text
as $last_name$
	SELECT x[ 1 + ( (random() * 25)::int ) % 14 ] as last_name
	FROM (
		select '{Ltd,& Co,SARL,SA,Gmbh,United,Brothers,& Sons,International,Ext,Worldwide,Global,2000,3000}'::text[] as x
	) AS z
$last_name$ language sql

SELECT concat_ws(' ',gen_first_name(generator), gen_last_name(generator)) 
FROM generate_series(1,10000) as generator;

create or replace function rand_bool(generator int)
returns boolean 
as $rand_bool$
	select case when random()* 2 > 1 then true else false end
$rand_bool$ language sql

-- populate users
with user_info as (
	select gen_first_name(generator) n, rand_bool(generator) as isoffline
	from generate_series(1,10000) as generator
)
insert into users(email, displayname, isoffline)
select concat_ws('@', n, 'gmail.com'), n, isoffline
from user_info

-- populate paygroups table
with random_leaders as (
	select userid from users order by random() limit 500
)
insert into paygroups(displayname, leaderuserid, isactive)
select gen_first_name(userid), userid, rand_bool(userid)
from random_leaders


--
-- populate members table
--
with leader_with_group as (
	
	select leaderuserid, groupid from paygroups
	
), groups_count as(
	
	select count(*) from paygroups
	
), randomly_ranked_users as (
	
	select userid, rank() over (order by random()) as r from users  
	where userid not in (select leaderuserid from leader_with_group)

), random_user_with_group_index as (
	
	select userid, r % count as j
	from randomly_ranked_users, groups_count	
	
), group_with_group_index as (
	
	select groupid, rank() over(order by groupid) as i
	from paygroups
	
),  users_with_groups as (
	
	select groupid, userid
	from random_user_with_group_index
	join group_with_group_index 
	on j + 1 = i
		union all
	select groupid, leaderuserid
	from leader_with_group
	
)
insert into members(userid, groupid, balance)
select userid, groupid, 0
from users_with_groups


insert into bills(amount, displayname, payer)
values(20, 'pizza', 21)

insert into involvedbillpayees(billid, memberid)
values(1, 2)

insert into paygroups(displayname, leaderuserid, isactive)
values('test group', 20007, true)

insert into paygroups(displayname, leaderuserid, isactive)
values('test group 2', 20004, true)

insert into members(userid, groupid, balance)
values(20010, 1, 0), (20009, 1, 0);

insert into members(userid, groupid, balance)
values(20005, 3, 0), (20006, 3, 0);

insert into transactions(tomember, frommember, amount)
values(4, 5, 20);

insert into transactions(tomember, frommember, amount)
values(1, 2, 20);

--error below
insert into transactions(tomember, frommember, amount)
values(1, 5, 20);