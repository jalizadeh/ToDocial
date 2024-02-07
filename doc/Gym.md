# Gym
The logic specific to this component



## Structure
- x -> times
	- 10x3 means: 3 sets of 10 kg
- : -> reps count
	- by default, each set has 12 reps, unless it is different or could not reached.
	- 10:12-11-10 means: 3 sets of 10 kg, but the first one, 12 reps, the second one, 11 and the third one, 10 reps
- , -> set
	- separates different weights
	- 5,7.5,10 means: 3 sets of which the first one is 5 kg, second one is 7.5 kg, and the third one is 10 kg


## Examples
- 10:12,12:10,12:9 *\*super straight*
- 10,12:10,12:9
- 10,12:10-9
- 10,12:10-9

- 10:MaxSet,10:MS,10:MS
- 10:MS-MS-MS
- 10x3:MS
- 10x3

- 0x12
- 10:12

- 10:12
- 10:12-9-8

- 10:12,12:12,12:12
- 10,12,12
- 10,12:12-12
- 10,12x2:12 		*\* ignored*
- 10,12x2

## Algorithm
> anyway, i need [Weight, Rep]

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