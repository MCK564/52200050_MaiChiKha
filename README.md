# ShopApp

## Introduction
This project is a web application built using Spring Boot for the backend, ReactJS for the frontend, and MySQL for the database. It is designed to serve as a platform for managing a shop's inventory and orders.

## Software Development Principles, Patterns, and Practices
- **SOLID Principles**: The codebase adheres to SOLID principles to ensure maintainability and scalability.
- **RESTful API**: Backend APIs follow RESTful principles for simplicity and consistency.
- **CLIENT-SERVER-COMMUNICATION**: Client(ReactJS) use axios library to send, and receive APIs request from the server(Springboot){
}
## Code Structure
- **Backend**: The backend is implemented using Spring Boot framework, following a modular structure with separate packages for controllers, services, repositories, models,configurations, dtos, utils, converters, exceptions, filters, and exceptions.
    + **VERSION**: JAVA 17, Spring boot 3.2.3, maven  , tomcat 10
    + **Library**: 
            1.Lombok devtools.
            2.Spring data jpa.
            3.Spring auto-configure
            4.Flyway. (to migrate database to new version)
            5.Spring security.
            6.Spring validation.
            7.Jjwt (handle JWT)
            8.ModelMapper (convert quickly models or entity)
        
- **Frontend**: The frontend is developed using ReactJS, following component-based architecture for better code reusability and maintainability. In this project I have used the react-hook function multipages, 
    + **VERSION**: 
    + **Library**:
    1.@mui/material (built-in ui)
    2.axios (resful APIs)
    2.react-pro-sidebar (admin sidebar)
    3.react-router-dom (navigation in system)
    4.mui-icon (built-in icon follow @mui/material)

- **Database**: MySQL is used as the database management system, with separate scripts for schema definition, data initialization, and migrations.

## Running the Application Locally
To run this application on your local computer, follow these steps:

1. Clone the repository from GitHub:
    https://github.com/MCK564/52200050_MaiChiKha.git

    and this project is in branch midterm

2. set up mysql:
    -with docker: i gaved u a docker-compose.yml in this branch. Compose it and setup database in that container so you dont have       
     to change anything in springboot directory. (whether u use mac, you have to change the direction of the volumne in docker-         compose.yml)
    -with window: you should change information in application.yml

3. java springboot:
    -i dont use docker to deploy spring boot project.
    -but you should set up tomcat, java version, jdk version
    -cd to the directory of the java spring boot
    -you should run 'mvn clean install' at least one time to update dependencies.

    -port:8080

4. react js:
    - set up npm in environment (install nodejs )
    Step to run:
    ->cd FE/shopapp
    ->npm install (to update node_modules, library)
    ->npm start
    the project will automatically appear in port:3000
    There is more information in FE/shopapp/README.md.

