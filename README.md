# Employee Client

This is client for Employee application. This is a program for Advanced Programming in Java for my University.
***
## Prerequisites

For correct working, this application depends on MySQL server and EmployeeServer application.

For run this program you will need:

* Running docker container with mysql 8.0
* Run server - application EmployeeServer.
* After start EmployeeServer run wsimport.

***
## Installing

### Docker

MySQL server in 8.0 version is working in docker container.
In terminal type:
```
$ docker run --name employee-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql --default-authentication-plugin=mysql_native_password --character-set-server=latin1 --collation-server=latin1_swedish_ci
```

Read more about [Docker](https://docker.com)

### MySQL

You have to prepare MySQL Server. If Docker container is running, in terminal insert: 
`$ docker exec -it employee-mysql mysql -uroot -proot`

In MySQL server create schema:
```CREATE SCHEMA 'employee';```

Create employee table in Database:
```
CREATE TABLE `employee`.`employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `salary` DECIMAL NULL,
  `phoneNumber` VARCHAR(45) NULL,
  `commission` DECIMAL NULL,
  `maxCommission` DECIMAL NULL,
  `creditCardNumber` VARCHAR(45) NULL,
  `costLimit` DECIMAL NULL,
  PRIMARY KEY (`id`));
 ```
 
Create users table in Database:
```
CREATE TABLE `employee`.`users`(
    iduser int PRIMARY KEY NOT NULL,
    userName varchar(45),
    password varchar(45));
```

Create user for logging (default user name is user and password is password):
```
INSERT INTO `employee`.`users`(userName,password) VALUES('user','password');
```

### EmployeeServer

Server for this application is available here:  [https://github.com/mysior103/EmployeeServerv2](https://github.com/mysior103/EmployeeServerv2)
In purpose run this application follow the instruction in README.md in above repository.

### WsImport

Server is exposing SOAP service on http://localhost:4321/workers . To be able run EmployeeClient you need to run wsimport command.

Open terminal, navigate to `\EmployeeClient\src\main\java` and run:

`$ wsimport -keep http://localhost:4321/workers?wsdl`

Remember that Server needs to run.

# Run Application and Enjoy! 
***
#### Authors

* **Rafał Podleś** 

#### References

[EmployeeServer](https://github.com/mysior103/EmployeeServerv2)