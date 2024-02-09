# Unit Tests
These set of tests focus on what a user is intended to see on front-end side of the project and mimics a user's behavior on web site. Tests will cover essential parts and pages to make sure everything works and is showed as expected per requirements.

## Tools

## Environment
All tests are aimed to be executed on **Production** environment. The design and implementation are started on environment **Test** and executed to make sure everything is fine as expected. On successful execution and at the end of project milestone, they are ported on environment **Production**.

> If a test is aimed for a specific environment, it will be mentioned explicitly


## Components
- User
    - User API `/api/v1/user`

## REST API
The path `/api/v1/*` is secured by Basic Authentication, so that, if the user is `enabled` and credentials are fine, he will receive his own resources.

## User API

| Test | Action  | Endpoint |
| ------------- | ------------- | ------------- |
| ✅ | Get current logged in `User`  | `GET /api/v1/user/me`  |
| ✅ | Get all `User`s  | `GET /api/v1/user`  |
| ✅ | Get one `User` by `username` | `GET /api/v1/user/{username}`  |
| ✅ | Create a `User`  | `POST /api/v1/user`  |
| ✅ | Get `User` activation token | `GET /api/v1/user/{username}/activation_token`  |
| ✅ | Activate a `User`  | `POST /api/v1/user/{username}/activate?token={token}`  |
| ✅ | Delete (deactivate) a `User`  | `DELETE /api/v1/user/{username}`  |
| ✅ | Delete from DB a `User`  | `DELETE /api/v1/user/{username}/db`  |


## User API test cases
1. Retrieve Current User Information (`/api/v1/user/me`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 1.1 | User accesses `/api/v1/user/me` endpoint using Basic Auth | The system should return the current user's information | |

2. Retrieve All Users (`/api/v1/user`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 2.1 | The `/api/v1/user` endpoint is accessed | The system should return a list of all enabled users | |

- **precond** there are enabled users in the system
- * only available for admin

3. Retrieve User by Username (`/api/v1/user/{username}`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 3.1 | The `/api/v1/user/{username}` endpoint is accessed providing a valid username | The system should return the user's information | Existing user |
    | 3.2 | The `/api/v1/user/{username}` endpoint is accessed providing an invalid username | The system should return a "Username not found: {username}" error with HTTP status code 404 | |

4. Retrieve User by ID (`/api/v1/user/id/{id}`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 4.1 | The `/api/v1/user/id/{id}` endpoint is accessed providing a valid id | The system should return the user's information | Existing user |
    | 4.2 | The `/api/v1/user/id/{id}` endpoint is accessed providing an invalid id | The system should return a "User ID not found: {id}" error with HTTP status code 404 | | 

5. Create User (`/api/v1/user`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 5.1 | The `/api/v1/user` endpoint is accessed with the user details | The system should create a new user account and return a success response with HTTP status code 201 | valid user information is provided |
    | 5.2 | The `/api/v1/user` endpoint is accessed | the system should return an error response indicating that the user already exists with HTTP status code 409 | the provided username or email already exists in the system |

6. Activate User (`/api/v1/user/{username}/activate`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 6.1 | the `/api/v1/user/{username}/activate` endpoint is accessed with the username and token | the system should activate the user account and return a success response with HTTP status code 202 | a valid activation token is provided |
    | 6.2 | the `/api/v1/user/{username}/activate` endpoint is accessed | the system should return an error response indicating that the token is invalid with HTTP status code 400 | an invalid activation token is provided |

7. Retrieve Activation Token (`/api/v1/user/{username}/activation_token`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 7.1 | the `/api/v1/user/{username}/activation_token` endpoint is accessed | the system should return the activation token associated with the user with HTTP status code 200 | the username exists in the system |
    | 7.2 | the `/api/v1/user/{username}/activation_token` endpoint is accessed | the system should return a "Username not found" error with HTTP status code 404 | the provided username does not exist in the system |

8. Delete User (`/api/v1/user/{username}`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 8.1 | the `/api/v1/user/{username}` endpoint is accessed | the system should delete the user account and return a success response with HTTP status code 200 | the user with the specified username exists in the system |
    | 8.2 | the `/api/v1/user/{username}` endpoint is accessed | the system should return a "User not found" error with HTTP status code 404 | the user with the specified username does not exist in the system |

9. Delete User from Database (`/api/v1/user/{username}/db`)
    | #  | Input                      | Output                       | Test data |
    | --- | -------------------------- | ---------------------------- | --------- |
    | 9.1 | the `/api/v1/user/{username}/db` endpoint is accessed | the system should delete the user account from the database and return a success response with HTTP status code 200 |  the user with the specified username exists in the system |
    | 9.2 | the `/api/v1/user/{username}/db` endpoint is accessed | the system should return a success response with HTTP status code 200 |  the user with the specified username does not exist in the system |


## Todo API

| Test | Action  | Endpoint |
| ------------- | ------------- | ------------- |
| ✅ | Get all `Todo`s  | `GET /api/v1/todo`  |
|     | Get one `Todo`  | `GET /api/v1/todo/id/{id}`  |
| ✅ | Get all `Todo` for `User`  | `GET /api/v1/todo/{username}`  |
|    | Get all filtered `Todo` for `User`  | `GET /api/v1/todo/{username}?filter={filters}`  |
| ✅ | Create a `Todo` for `User` | `POST /api/v1/todo`  |
| ✅ | Cancel (pause) a `Todo`  | `DELETE /api/v1/todo/{id}` |
| ✅ | Delete a `Todo` and its `TodoLog`  | `DELETE /api/v1/todo/{id}/db` |
| ✅ | Create a `Todo Log`  | `POST /api/v1/todo/{id}/log` |
| ✅ | Get a `Todo Log`  | `GET /api/v1/todo/{id}/log/{id}` |
| ✅ | Get all `Todo Log`  | `GET /api/v1/todo/{id}/log` |
| ✅ | Delete a `Todo Log`  | `DELETE /api/v1/todo/{id}/log/{id}` |


## Search Controller

1. **Valid Search for Todos**:
   - **Scenario**: Perform a search for todos with a valid query.
   - **Test Steps**:
     1. Send a GET request to `/search` with `target=todo` and a valid query (`q` parameter).
     2. Verify that the response status is 200 OK.
     3. Verify that the returned page contains a list of todos matching the search query.

2. **Valid Search for Gym Plans**:
   - **Scenario**: Perform a search for gym plans with a valid query.
   - **Test Steps**:
     1. Send a GET request to `/search` with `target=gym` and a valid query (`q` parameter).
     2. Verify that the response status is 200 OK.
     3. Verify that the returned page contains a list of gym plans matching the search query.

3. **Search with Empty Query**:
   - **Scenario**: Perform a search with an empty query.
   - **Test Steps**:
     1. Send a GET request to `/search` with `target=todo` and an empty query (`q` parameter).
     2. Verify that the response status is 200 OK.
     3. Verify that the returned page contains a list of all todos.

4. **Invalid Target**:
   - **Scenario**: Perform a search with an invalid target.
   - **Test Steps**:
     1. Send a GET request to `/search` with an invalid `target` parameter.
     2. Verify that the response status is a redirection (e.g., 302 Found).
     3. Verify that the redirection URL is `/error`.
     4. Verify that an appropriate error message is flashed (e.g., "The search criteria is not correct").

5. **Sanitization of Query**:
   - **Scenario**: Test the sanitization of the search query.
   - **Test Steps**:
     1. Send a GET request to `/search` with `target=todo` and a query containing potentially harmful characters (e.g., HTML tags, special characters).
     2. Verify that the search query is sanitized properly to prevent XSS attacks or other security vulnerabilities.

6. **Edge Cases**:
   - **Scenario**: Test edge cases such as very long queries, special characters, or edge values for parameters.
   - **Test Steps**: 
     - For example, send requests with excessively long queries, special characters, or unusual values for the `target` parameter to ensure that the endpoint handles these cases gracefully.
