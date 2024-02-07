# ToDocial

## Manage your todos and share with friends
The projects is inspired by Github. A social media to create, track todos and share them publicly with friends and others. ToDocial is inspired by Github as a tool to let others cooperate on your todos and ideas. Just signup and create your first todo ☺️


## Inspirations
This project is a combination of the features in Github and Wordpress that I could merge into one single project.
- Todo management & sociality (Github)
- Content management (Wordpress)


## Features
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


## Images
Project's [screenshots]()

## Backlog
Track project's tasks in [backlog ](doc/Backlog.md)