select
	follower.id,
	follower.email,
	follower.followed,
	u.email,
	u.firstname || " " || u.lastname as full_name
from (
	select 
		u.id,
		u.email,
		uf.followed,
		u.email
	from user u
	join users_follows uf
		on u.id = uf.follower
	order by u.id) as follower
join user u
	on u.id = follower.followed;
--where follower.id = 1



--count todos by user
select 
	u.id,
	u.email,
	td.name,
	count(*) as c
from user u
join todo td
	on u.id = td.user_id
group by u.id;



-- roles of users
SELECT
	u.firstname,
	u.username,
	ur.role_id,
	r.name
FROM user u
left JOIN users_roles ur 
	ON u.id = ur.user_id
left join role r
	on ur.role_id = r.id;



-- todos of admin
SELECT
	u.firstname,
	u.username,
	ur.role_id,
	r.name,
	td.name,
	count(*) as admins_todos
FROM user u
left JOIN users_roles ur 
	ON u.id = ur.user_id
left join role r
	on ur.role_id = r.id
left join todo td
	on u.id = td.user_id
	where r.id = 3
group by u.id;