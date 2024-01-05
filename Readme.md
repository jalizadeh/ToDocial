# ToDocial

## Manage your todos and share with friends
The projects is inspired by Github. A social media to create, track todos and share them publicly with friends and others. ToDocial is inspired by Github as a tool to let others cooperate on your todos and ideas. Just signup and create your first todo ☺️


### Inspirations
This project is a combination of the features in Github and Wordpress that I could merge into one single project.
- Todo management & sociality (Github)
- Content management (Wordpress)


### Features
- Login / Sign up (multi-user)
- Todo Management
- User Profile
- Charts & Statistics
- Administrator Dashboard
- Simulation
- CI
- Tests
	- JUnit (REST API)
	- Selenium (Web pages)


## REST API
The path `/api/v1/*` is secured by Basic Authentication, so that, if the user is `enabled` and credentials are fine, he will receive his own resources.


| Test | Action  | Endpoint |
| ------------- | ------------- | ------------- |
| ✅ | Get all `User`\`s  | `GET /api/v1/user`  |
| ✅ | Get one `User` by `username` | `GET /api/v1/user/{username}`  |
| ✅ | Create a `User`  | `POST /api/v1/user`  |
| ✅ | Get `User` activation token | `GET /api/v1/user/{username}/activation_token`  |
| ✅ | Activate a `User`  | `POST /api/v1/user/{username}/activate?token={{token}}`  |
| ✅ | Delete (deactivate) a `User`  | `DELETE /api/v1/user/{username}`  |
| ✅ | Delete from DB a `User`  | `DELETE /api/v1/user/{username}/db`  |
| ✅ | Get all `Todo`s  | `GET /api/v1/todo`  |
| ✅ | Get all `Todo` for `User`  | `GET /api/v1/todo/{username}`  |
|   | Get all filtered `Todo` for `User`  | `GET /api/v1/todo/{username}?filter={filters}`  |
| ✅ | Create a `Todo` for `User` | `POST /api/v1/todo`  |
|   | Get one `Todo`  | `GET /api/v1/todo/{id}`  |
|   | Cancel (pause) a `Todo`  | `DELETE /api/v1/todo/{id}` |
| ✅ | Delete a `Todo` and its `TodoLog`  | `DELETE /api/v1/todo/{id}/db` |
| ✅ | Create a `Todo Log`  | `POST /api/v1/todo/{id}/log` |
|   | Get a `Todo Log`  | `GET /api/v1/todo/{id}/log/{id}` |
|   | Get all `Todo Log`  | `GET /api/v1/todo/{id}/log` |
| ✅ | Delete a `Todo Log`  | `DELETE /api/v1/todo/{id}/log/{id}` |



## Run
- Clone the project
- In terminal run `mvn spring-boot:run`
- Go to `http://localhost:8080`
- Login:
	- as `admin` with `admin:12345`
    - as `javad` with `javad:12345`
	- as `user`  with `alexfergosen:12345`
	

## Run Tests
- mvn clean test
	- Run and generate report
- mvn clean site
	- Run and generate styled report
- mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=12345


## Simulation Scenario [TODO]
In simulation mode, a list of random users are created and they interact with each other. You, as an admin, can observe what is happening inside the ecosystem


### Images

Public page of a registered user
![](assets/public-page.png)

List of all todos, with full access
![](assets/my-todos.png)

Todos over time for a better visualization
![](assets/chart-todo.png)

Settings of the system
![](assets/settings.png)

## TODO
- [x] Initialize Spring project
- [x] Database
	- Currently, data is stored using SQLite in `todo.db` file in root folder
	- User
		- users_roles (many-to-many)
		- Role (high-level roles)
			- roles_privileges (many-to-many)
			- Privilege (low-level permissions)
		- verification_token
		- persistent_logins
		- password_reset_token
		- security_question
		- security_question_definition
		- users_follows(`follower` is following the `followed` user)
	- Todo
		- todo-log
		- todos_logs
	- [x] Set username & password
	- [ ] Migrate to MySQL
- [ ] General
	- [x] Header/Navigation/Footer fragments
		- `Spring Security JSP Taglib` is added for easier control of authenticated users
	- [x] Set page title
		- Each page&#39;s title is encapsulated in `model`
	- [x] Support UTF-8 encoding
	- [x] Handle exceptions with exact error reason
		- All exceptions will be redirected to `/error` showing the reason and what happened.
		- [x] If any error occurs in subdirectories, the page is not shown correctly
	- [ ] Multi-language (English / Italian)
		- Currently `user-session` based
		- [ ] Change static texts to `<spring:message code="key"/>`
- [x] Search
  - [x] in Todos
  - [x] in Gym
- [x] Todos
	- [x] All user&#39;s todos
	- [x] Public / Private
	- [x] Management
		- [x] Add new
		- [x] Modify
		- [x] Complete & Archive
		- [x] Cancel/Resume (todo is never deleted, but kept forever, can be resumed)
	- [x] Log
		- During working on the todo, user might need to comment some thoughts
		- Not editable, only removable
	- [ ] Type
		- [x] 1-time
		- [ ] Repeatable
			- [ ] Reminder
		- [ ] mini-Task
	- [ ] Subtasks
	- [x] Progress bar
- [ ] Wishes
	- Todos that will be taken care later, at unknown time. When the user wants, he can turn a `wish` into a todo
	- Other users can comment on his wishes, or help with appropriate advices
- [ ] Log in
	- Spring Security
	- There are two roles `ROLE_USER` and `ROLE_ADMIN`
	- Everyone can access `Log in` or `Sign up`, but for accessing the todos, the user must log in or sign up first.
	- User can not login on multiple clients at the same time
	- [x] After login, user shouldn't be able to login again
	- [x] Field validation
	- [x] Log out
	- [ ] Forgot my password
		- The data is separately  stored in `PasswordResetToken` with 12-hour limit
		- [ ] Security Question
		- [ ] Field validation
	- [x] Remember me
		- Note that the check-box should be `... name="remember-me" ...`
		- [x] Client-side (cookie)
		- [ ] Server-side (persistence)
			- ❌ it seems db is not populated
	- [x] Logged in user, should not be able to reach pages like "Login", "Signup", ...
		- Managed in `SecurityConfiguration`, by defining the rules
- [ ] Signup new user
	- All new users are registered as `USER`
	- [x] Filed validation
	- [x] Security Question
		- Entity `SecurityQuestionDefinition` stores the questions.
		- Entity `SecurityQuestion` manages the relationship between the `User` and it&#39;s security `question_id` & `answer`.
		- It appears while signing up or changing password.
	- [x] Password
		- Passwords are encrypted with BCrypt 10
		- [x] Password strength and validation on front-end & back-end
			- It is done with `jQuery` & `pwstrength.js`
		- [x] Password confirmation
	- [x] Profile Photo
		- It is optional, so user can add/update it later from his profile page
		- Photo&#39;s name is changed to `username + .jpg`
		- It uses `Storage Service` to handle file upload. This interface can be extended for any other file types or customizations
	- [x] Successful sign up
		- [x] Email verification
			- I used `smtp.gmail.com` as the server. Your email credentials `username` & `password` must be inserted in `application.properties`
			- The user has 24 hours to verify his account
			- [x] Token is valid
			- [x] Token is not valid
			- [x] Token is expired
			- [ ] User already activated
	- [x] Unsuccessful sign up
		- [ ] Show errors & exceptions
- [ ] User
	- [ ] Profile
		- [ ] Account details
			- Name, Bio, Location
		- [ ] Photo
		- [ ] Change password
		- [ ] Delete account
		- [ ] Security
			- [ ] Who can see me?
	- [x] User&#39;s Public Page
	- [x] Follower / Following
		- [ ] Follow / Unfollow
			- 🟡 a user can follow any user, multiple times
- [ ] Admin panel
	- [ ] General
		- [x] Site name & description
		- [x] Footer copyright
		- [ ] Anyone can register?
			- [x] Shows/Hides the links in pages
			- [ ] Changes security policy for accessing `signup` page
		- [x] Default role for registered user
			- [x] Add new role
			- [x] Modify role
		- [x] Date Structure
		- [x] Time structure
		- [x] Language
			- 1. Admin can configure the language/country of whole system
			- 2. Any user can change the language/country of current session by himself (in footer)
	- [ ] Users
		- If user has `ROLE_ADMIN`, she can access the dashboard
		- [x] List of online users
		- [x] 1-click user's state change
		- [x] 1-click delete user
		- [ ] Modify user
		- [x] Add new user
			- [x] Handle mistakes like signing up a new user
			- [x] Email verification if user is suspended
			- [x] Set specified roles
				- [ ] Support multiple roles
		- [ ] Define new roles
	- [ ] Todos
		- [x] Delete (admin needs only delete todos, nothing more is needed)
		- [ ] Page size
		- [ ] Allow search engines to fetch public todos
		- [ ] Users must be registered to access a public page
		- [ ] Users must be registered to access a public todo
		- [ ] Manage Todo types & priority
	- [ ] Email
		- [ ] Admin email
		- [ ] Configurations
		- [ ] Formats
	- [ ] Security
		- [ ] How long lasts verification email
		- [ ] How long lasts reset password email
		- [ ] Newly registered users must be verified for further access
		- [ ] Define new security questions
		- [ ] Password combination
- [ ] Logger
- [ ] Query Optimization
- [x] Charts
	- [x] Start-End range
	- [x] Gantt
- [x] Personal Life Cycle Test
	- The test is currently in Persian
- [ ] Test
	- [x] Test report
		- with `mvn clean test` or `mvn site -DgenerateReports=false`
	- [x] Test coverage report
- [ ] Gym
  - [x] Gym homepage
    - [x] All plans
    - [x] Active plans
    - [x] Completed plans
  - [ ] Record management
    - [x] Plan
      - [x] View
      - [x] Add
      - [x] Delete
    - [ ] Workout
      - [x] View
      - [ ] Add
      - [ ] Delete
    - [x] Workout log
  - [ ] Search plans
  - [ ] Filter plans