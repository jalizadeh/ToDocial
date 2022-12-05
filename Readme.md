# ToDocial: Manage your todos and share with friends
A ToDo manager with Spring Boot. I am trying to make it as much as possible complete, as an industry product, at the same time, a project to learn stuff.


### Images

Public page of a registered user
![](assets/public-page.png)

List of all todos, with full access
![](assets/my-todos.png)

Todos over time for a better visualization
![](assets/chart-todo.png)

Settings of the system
![](assets/settings.png)


### Inspirations:

This project is a combination of the features in Github and Wordpress that I could merge into one single project.
- Todo management & sociality (Github)
- Content management (Wordpress)


### Features:

- Login / Sign up (multi-user)
- Todo Management
- User Profile
- Charts & Statistics
- Administrator Dashboard


### WIP:

- [x] Initialize Spring project
- [x] Database
	- Currently data is stored in `todo.db` file in root folder
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
	- [ ] Set username & password
	- [ ] Migrate to MySQL
- [ ] General
	- [x] Header/Navigation/Footer fragments
		- Spring Security JSP Taglib is added for easier control of authenticated users
	- [x] Set page title
		- Each page&#39;s title is encapsulated in `model`
	- [x] Support UTF-8 encoding
	- [x] Handle exceptions with exact error reason
		- All exceptions will be redirected to `/error` showing the reason and what happened.
		- [x] If any error occurs in subdirectories, the page is not shown correctly
	- [ ] Multi-language (English / Italian)
		- Currently `user-session` based
		- [ ] Change static texts to `<spring:message code="key"/>`
- [x] Basic Search
- [x] Todos
	- [x] All user&#39;s todos
	- [x] Public / Private
	- [x] Add new
	- [x] Modify
	- [x] Complete & Archive
	- [x] Cancel/Resume (todo is never deleted, but kept forever, can be resumed)
	- [x] Log
		- During working on the todo, user might need to comment some thoughts
		- Not editable, only removable
	- [ ] mini-Task
	- [x] Progress bar
- [ ] Wishes
	- Todos that will be taken care later, at unknown time. When the user wants, he can turn a `wish` into a todo.
	- Other users can comment on his wishes, or help with appropriate advices.
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
			- 🟡 a user can follow any user, mulitple times
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
- [ ] Charts
	- [x] Start-End range
	- [x] Gantt
- [x] Personal Life Cycle Test
	- The test is currently in Persian



## REST (WIP):

| Action  | Endpoint |
| ------------- | ------------- |
| Retrieve all `User`s  | `GET /api/v1/user`  |
| Create a `User`  | `POST /api/v1/user`  |
| Retrieve one `User`  | `GET /api/v1/user/{username}`  |
| Delete a `User`  | `DELETE /api/v1/user/{username}`  |

| Action  | Endpoint |
| ------------- | ------------- |
| Retrieve all `Todo`s  | `GET /api/v1/user/{username}/todos`  |
| Create a `Todo` for `User`  | `POST /api/v1/user/{username}/todos`  |
| Retrieve one `Todo`  | `GET /api/v1/user/{username}/todos/{id}`  |
| Delete a `Todo`  | `DELETE /api/v1/user/{username}/todos/{id}`  |



## Run:

- Clone the project
- In terminal run `mvn spring-boot:run`
- Go to `http://localhost:8080`
- Login:
	- as `admin` with username: `javad` & password: `12345`
	- as `user`  with username: `alexfergosen` & password: `12345`