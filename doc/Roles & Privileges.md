# Roles & Privileges
How roles and privileges work and are setup in ToDocial

## General note
In Spring Security, both roles and authorities (privileges) are used to define permissions and access control, but they have slightly different purposes:

1. Role:
	- A role is a high-level classification that represents a set of permissions or actions that a user can perform in an application.
	- Roles are often used to group related privileges together for easier management.
	- Roles are typically prefixed with "ROLE_" by convention in Spring Security (e.g., ROLE_USER, ROLE_ADMIN).

2. Authority (Privilege):
	- An authority, often referred to as a privilege, represents a specific permission or action that a user is granted.
	- Authorities are more fine-grained than roles and directly represent the individual permissions a user possesses.
	- Authorities are not necessarily prefixed with "ROLE_" and can have more descriptive names (e.g., PRIVILEGE_READ, PRIVILEGE_UPDATE).

In Spring Security, you can use either roles or authorities (privileges) to define access control at both the method and URL levels. The choice between roles and authorities often depends on the granularity of control you need. Roles are a good way to group related privileges, while authorities allow for more specific and fine-grained control.

Regarding your practice of using `@PreAuthorize("hasAuthority('PRIVILEGE_UPDATE')")`, it is a good practice to be precise and granular in your method-level security expressions. This allows you to clearly define which specific privileges are required to access a particular method. Being more explicit in your security expressions can make your access control more manageable and maintainable.

If you have a well-defined set of privileges and want to enforce access control based on specific actions or permissions, using authorities (privileges) in `@PreAuthorize` expressions is a reasonable approach. It allows you to express the exact requirements for accessing a particular method.

However, if your application's access control is primarily based on higher-level roles (e.g., ROLE_USER, ROLE_ADMIN), you may use roles in your security expressions. For example, `@PreAuthorize("hasRole('ROLE_ADMIN')")`.

> We’re using the Privilege – Role terms here. But in Spring, these are slightly different. In Spring, our Privilege is referred to as Role and also as a (granted) authority, which is slightly confusing.

## Roles
- 1	ROLE_ADMIN
- 2	ROLE_USER
- 3	ROLE_EDITOR
- 4	ROLE_ANALYZER

## Privileges
- 1	PRIVILEGE_READ
- 2	PRIVILEGE_WRITE
- 3	PRIVILEGE_UPDATE
- 4	PRIVILEGE_DELETE
- 5	PRIVILEGE_VIEW
- 6	PRIVILEGE_ACCESS

## Relationship
Admin
- 1	ROLE_ADMIN		1	PRIVILEGE_READ
- 1	ROLE_ADMIN		2	PRIVILEGE_WRITE
- 1	ROLE_ADMIN		3	PRIVILEGE_UPDATE
- 1	ROLE_ADMIN		4	PRIVILEGE_DELETE
- 1	ROLE_ADMIN		6	PRIVILEGE_ACCESS

User
- 2	ROLE_USER		1	PRIVILEGE_READ

Editor
- 3	ROLE_EDITOR		1	PRIVILEGE_READ
- 3	ROLE_EDITOR		2	PRIVILEGE_WRITE
- 3	ROLE_EDITOR		3	PRIVILEGE_UPDATE
- 3	ROLE_EDITOR		6	PRIVILEGE_ACCESS

Analyzer
- 4	ROLE_ANALYZER	1	PRIVILEGE_READ
- 4	ROLE_ANALYZER	6	PRIVILEGE_ACCESS