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
- [x] Login / Logout
	- Spring Security 
	- There are two roles `USER` and `ADMIN`
	- Everyone can access `Log in` or `Sign up`, but for accessing the todos, the user must log in or sign up first.
	- [ ] Field validation
- [x] Header/Navigation/Footer fragments
- [x] List of all todos
- [x] Add/Update/Delete todos
- [x] Set page title automatically
	- Each page&#39;s title is encapsulated in `model`
- [x] 1-click Todo's state change
- [x] Support UTF-8 encoding
- [x] Search among todos
- [ ] [Signup new user](https://www.baeldung.com/registration-with-spring-mvc-and-spring-security)
	- All new users are registered as `USER`
	- [x] Filed validation
	- [x] Email validation
	- [x] Password confirmation
	- [x] Store passwords encrypted (BCrypt 10)
	- [ ] Verify email address
	- [x] Successful sign up
		- [ ] Email verification
	- [x] Unseccessfull sign up
		- [x] Show errors
- [x] Handle exceptions with exact error reason
	- All exceptions will be redirected to `/error` showing the reason and what happened.
	- [ ] If any error occurs in subdirectories, the page is not shown correctly
- [ ] I forgot my password
- [ ] Admin panel
	- If user has `ROLE_ADMIN`, she can access the dashboard
	- [x] List of all users
		- [x] 1-click users's state change
		- [ ] Modify user
		- [ ] Add new user
	- [ ] List of all todos
		- [ ] Add new todo for any user
		- [ ] Modify todo
	- [ ] Setting
	- [ ] Limit `USER` access