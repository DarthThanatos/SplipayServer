select userid
from members
where groupid in (
	select groupid
	from members
	group by groupid
	having count(memberid) = 22
)
-- 2/22 + 39/19 + 461/20

select userid, groupid from members where userid in (
	select userid
	from members 
	group by userid
	having count(memberid) <> 1
)
order by 1, 2

select * from paygroups where groupid in (3, 209)

--check if every leaderuserid is also a member
select leaderuserid, count(members)
from paygroups
left outer join members
on leaderuserid =  userid
group by leaderuserid
having count(members) <> 1
