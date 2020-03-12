# Spring TO-DO Web Application
A To-Do manager with Spring and SQLite. I am trying to make it as much as possible complete, as an industry product, at the same time, a project to learn stuff.

### Features:
- Login / Sign up (multi-user)
- Todo management
- User profile
- Administrator dashboard


### Todos:

- [x] Initialize Spring project
- [x] Implement SQLite database
	- User
	- Todo
	- Role
	- [ ] Set username & password
- [ ] General
	- [x] Header/Navigation/Footer fragments
	- [x] Set page title
		- Each page&#39;s title is encapsulated in `model`
	- [x] Support UTF-8 encoding
	- [x] Handle exceptions with exact error reason
		- All exceptions will be redirected to `/error` showing the reason and what happened.
		- [ ] If any error occurs in subdirectories, the page is not shown correctly
	- [ ] Multi-language
- [x] Todos
	- [x] List of all todos
	- [x] Add/Update/Delete todos
	- [x] 1-click Todo's state change
	- [x] Search among todos
- [ ] Log in
	- Spring Security 
	- There are two roles `ROLE_USER` and `ROLE_ADMIN`
	- Everyone can access `Log in` or `Sign up`, but for accessing the todos, the user must log in or sign up first.
	- [ ] Field validation
	- [x] Log out
	- [ ] I forgot my password
	- [ ] Remember me
- [ ] [Signup new user](https://www.baeldung.com/registration-with-spring-mvc-and-spring-security)
	- All new users are registered as `USER`
	- [x] Filed validation
	- [x] Email validation
	- [x] Password confirmation
	- [x] Store passwords encrypted (BCrypt 10)
	- [x] Successful sign up
		- [x] Email verification
			- I used `smtp.gmail.com` as the server. Your email credentials `username` & `password` must be inserted in `application.properties`.
			- The user has 24 hours to verify his account
			- [x] Token is valid
			- [ ] Token is not valid
			- [ ] Token is expired
			- [ ] User already activated
	- [x] Unseccessfull sign up
		- [x] Show errors & exceptions
- [ ] User profile
	- [ ] Manage account
	- [ ] Delete account
- [ ] Admin panel
	- If user has `ROLE_ADMIN`, she can access the dashboard
	- [x] Manage users
		- [x] 1-click users's state change
		- [x] 1-click delete user
		- [ ] Modify user
		- [x] Add new user
			- [x] Handle mistakes like signing up a new user
			- [x] Email verification if user is suspended
			- [x] Set specified roles
				- [ ] Support multiple roles
	- [ ] Manage todos
		- [ ] Add new todo for any user
		- [ ] Modify todo
	- [ ] Settings
	- [ ] Limit `USER` access