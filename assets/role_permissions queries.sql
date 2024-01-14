select 
	u.id,
	u.username,
	r.role_id,
	rr.name,
	p.privilege_id,
	pp.name
from user u
left join users_roles r
	on u.id = r.user_id
left join role rr
	on r.role_id = rr.id
join roles_privileges p
	on r.role_id = p.role_id
join privilege pp
	on p.privilege_id = pp.id;
	

select *
from role r
join roles_privileges rp
	on r.id = rp.role_id
join privilege p
	on rp.privilege_id = p.id
order by r.id