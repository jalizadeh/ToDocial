-- plan
SELECT 
	p.id,
	p.title,
	pi.more_info,
	d.focus,
	d.day_number, 
	d.total_workouts,
	d.progress
from gym_plan p
join gym_plan_introduction pi
	on p.gym_plan_introduction = pi.id
join gym_day d
	on p.id = d.plan_id ;

	
	
-- total workouts per plan;
SELECT 
	p.id,
	p.title,
	pi.more_info,
	d.focus,
	d.day_number, 
	d.total_workouts,
	d.progress,
	sum(d.total_workouts) as "count workouts"
from gym_plan p
join gym_plan_introduction pi
	on p.gym_plan_introduction = pi.id
join gym_day d
	on p.id = d.plan_id
group by p.id;



-- calculate daily weight lift
select 
		--wl.id,
		wl.pwd_id,
		wl.day_workout_id,
		--wl.set_number,
		--wl.weight,
		--wl.reps,
		--w.id,
		w.name,
		--, count(*)
		sum(wl.weight) as daily_w
	from gym_workout_log wl
	join gym_day_workout dw
		on dw.id = wl.day_workout_id
	join gym_workout w
		on dw.workout_id = w.id
	group by pwd_id, day_workout_id;
	
	


-- calculate weekly weight lifting
select 
	pwd_id,
	sum(daily_w) as weekly_w
from (
	select 
		--wl.id,
		wl.pwd_id,
		wl.day_workout_id,
		wl.set_number,
		wl.weight,
		wl.reps,
		--w.id,
		w.name,
		--, count(*)
		sum(wl.weight) as daily_w
	from gym_workout_log wl
	join gym_day_workout dw
		on dw.id = wl.day_workout_id
	join gym_workout w
		on dw.workout_id = w.id
	group by pwd_id, day_workout_id )
group by pwd_id;


-- count trained muscles in a plan
-- a workout may involve more than one muscle group
select 
	p.id, p.title, 
	d.id, d.focus,
	dw.workout_id,
	w.name,
	wm.muscle_category,
	count(*) as totalMC
from gym_plan p
join gym_day d
	on p.id = d.plan_id
join gym_day_workout dw
	on d.id = dw.day_id
JOIN gym_workout w
	on dw.workout_id = w.id
join gym_workout_muscle wm
	on w.id = wm.workout_id
where p.id = 855
group by p.title, wm.muscle_category
order by totalMC;