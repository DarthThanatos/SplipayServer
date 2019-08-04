create or replace function check_members_in_group_unique()
returns trigger 
as $$
	declare 
		identicalInvolved Int;
	begin 		
		select count(*) into identicalInvolved
		from members
		where new.userid = userid and new.groupid = groupid;
				
		if (identicalInvolved != 1) then
			raise exception 'There already is a user with id % in the group %', new.userid, new.groupid;
		end if;
	
		return new;		
	end
$$ language 'plpgsql'

drop trigger check_members_in_group_unique_trigger on members;

create trigger check_members_in_group_unique_trigger 
after insert
on members
for each row
execute procedure check_members_in_group_unique();


create or replace function check_involved_payees_validity()
returns trigger 
as $$
	declare 
		payerid Int;
		payergroupid Int;
		involvedgroupid Int;
		identicalInvolved Int;
	begin 
		select groupid into payergroupid
		from bills b
		join members m
		on b.payer = m.memberid
		where billid = new.billid;
		
		select groupid into involvedgroupid
		from members
		where memberid = new.memberid;
				
		if (payergroupid != involvedgroupid) then
			raise exception 'Select payees must belong to the same group! Payer gid: %, involved gid: %', payergroupid, involvedgroupid;
		end if;
		
		select count(memberid) into identicalInvolved
		from involvedbillpayees
		where new.billid = billid and new.memberid = memberid
		group by billid;
				
		if (identicalInvolved != 1) then
			raise exception 'Member % already pays for the bill % you greedy fuck', new.memberid, new.billid;
		end if;
	
		select payer into payerid
		from bills 
		where billid = new.billid;
		
		if (payerid = new.memberid) then
			raise exception 'Member % is already a payer, cannot be involved again', new.memberid;
		end if;
	
		return new;		
	end
$$ language 'plpgsql'

drop trigger check_involved_payees_validity_trigger on involvedbillpayees;

create trigger check_involved_payees_validity_trigger 
after insert
on involvedbillpayees
for each row
execute procedure check_involved_payees_validity();


create or replace function check_transaction_members_validity() 
returns trigger
as $validity$
	declare 
		firstMemberGroup integer;
		secondMemberGroup integer;
	begin
		select groupid into firstMemberGroup
-- 	   	from members m 
--   	where new.fromMember = m.memberId;
 		from members m join inserted n
 		on n.fromMember = m.memberId;
		
		select groupid into secondMemberGroup
		from members m join inserted n
		on n.toMember = m.memberId;
		
		if (firstMemberGroup != secondMemberGroup) then
			raise exception 'Both members must be within the same group! FromMember gid: %, toMember gid: %', firstMemberGroup, secondMemberGroup;
		end if;
		return new;
	end
$validity$ LANGUAGE 'plpgsql';

drop trigger check_transaction_members_validity_trigger on transactions;

create trigger check_transaction_members_validity_trigger 
after insert
on transactions
referencing new table inserted
-- for each row
execute procedure check_transaction_members_validity();

create or replace function hello()
returns text 
as $hello$
	declare 
	begin 
		return 'hello';
	end 
$hello$ language 'plpgsql'


create or replace function getSesoco()
returns table(userid Int, email text, displayname text, isoffline bool, avatar_url text)
as $sesoco$
	select * from users where displayname = 'SESOCO'
$sesoco$ language sql


create or replace function getSesocoIds()
returns table(userid Int)
as $sesoco$
	select userid from users where displayname = 'SESOCO'
$sesoco$ language sql


create or replace function groupsOfUserLike(inUserId Int, inGroupName text)
returns table(groupid Int, displayname text, leaderuserid Int, isactive bool)
as $$
	select groupid, p.displayname, leaderuserid, isactive
	from members m join paygroups p using(groupid)
	where m.userid = inUserId and UPPER( p.displayname) LIKE CONCAT('%',UPPER(inGroupName),'%')
$$ language 'sql'


create or replace function usersInGroup(inGroupId Int)
returns table (userid Int, email text, displayname text, isoffline bool, avatar_url text)
as $$
	select userid, email, displayname, isoffline, avatar_url
	from users u join members m using(userid)
	where groupid = inGroupId
$$ language 'sql'
