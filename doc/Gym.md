# Gym
The logic specific to this component



## Note to workout log convertor

0x12
10:12

x -> 
: -> reps count
, -> set


10:12,12:10,12:9	*super straight
10,12:10,12:9
10,12:10-9
10,12:10-9


10:MaxSet,10:MS,10:MS
10:MS-MS-MS
10x3:MS
10x3

10:12
10:12-9-8

10:12,12:12,12:12
10,12,12
10,12:12-12
10,12x2:12 		* ignored
10,12x2


anyway, i need [W, R]

process:
```
- all sets are devided by ',', but if it doesnt have
	- it must have ':'
- if has 'x', pick before 'x' and multiply by after 'x'
- if has :
	- extract before ':' as W
	- extract after ':' as possible sets
		- check if has '-'
			- yes: extract each and save for each W:R
			- no: extract and save W:R
```