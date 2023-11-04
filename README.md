# Skin Videogame API

## Project Description

This project aims to create a RESTful API developed in Java with the Spring framework that allows users to query, purchase, modify, and delete skins for a video game. The application will provide a set of endpoints that will enable users to interact with the skins in the system.
## Table of Contents

- [Project Description](#project-description)
- [Features](#features)
- [Requisites](#requisites)
- [Configuration](#configuration)
    - [Clone the repo](#clone-the-repo)
    - [Database](#database)
- [JWT Configuration](#jwt-configuration)
- [Run Spring Boot Application](#run-spring-boot-application)
- [Run following SQL insert statements](#run-following-sql-insert-statements)
- [API Endpoints](#api-endpoints)
- [Usage of Postman](#usage-of-postman)
- [Postman Collections for Authentication](#postman-collections-for-authentication)
- [Postman Collections for API Endpoints](#postman-collections-for-api-endpoints)
- [License](#license)

###  Features

- User registration and authentication using JWT.
- Secure API endpoints with role-based access control.
- Error handling and exception management.
- Integration with MySQL database for data storage.
- API documentation using Postman collections.
- Skin Model: The application defines a Skin model that includes fields such as id, name, type, price, color, etc.
- Read Skins: The application reads the available skins from a JSON file.

### Requisites

Before you begin, ensure you have met the following requirements:

- Java 11 or later installed.
- MySQL database server installed and running.
- Postman installed for testing the API. 

### Configuration

#### Clone the repo
  ```sh
   git clone https://github.com/AnnaFedoraeva/API_CRUD_skins.git
   ```

#### Database
Create a MySQL database and configure the connection credentials in `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_basedatos
spring.datasource.username=usuario
spring.datasource.password=contraseña
```

### JWT Configuration

To use JSON Web Tokens (JWT) for authentication, you need to configure a secret key and set the token expiration in the `application.properties` file.

```properties
jwt.secret=your_secret_key_here
jwt.expiration-ms=token_expiration_in_milliseconds
```
### Run Spring Boot application

```bash
$ mvn spring-boot:run
```
The application will be available at http://localhost:8080.

### Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```


### API Endpoints

- GET /skins/available: Returns a list of all available skins for purchase.
- POST /skins/buy/{id}: Allows users to purchase a skin and store it in the database.
- GET /skins/myskins: Returns a list of skins purchased by the user.
- PUT /skins/{id}/{color}: Allows users to change the color of a purchased skin.
- DELETE /skins/delete/{id}: Allows users to delete a purchased skin.
- GET /skin/getskin/{id}: Returns a specific skin.

### Usage of Postman

To get started with these collections:
1. Click on the "Run in Postman" button provided above the respective collection to import it into your Postman workspace.
2. Ensure you set any necessary environment variables or configurations as specified in the collection.
3. Execute individual requests within Postman to test and explore the authentication and API endpoints according to your requirements.

Signup some users with `/api/auth/signup`.

***Example***:
```json
  {
  "username": "tim",
  "email": "tim@gmail.com",
  "password": "12345678",
  "role": ["admin", "user"]
}
```

Signin: POST `/api/auth/signin`.

Send all requests with an Authentication Header with `Bearer HERE_YOUR_TOKEN`

### Postman Collections for Authentication

This collection focuses on the signup and signin processes, providing a set of requests for user registration and login. You can use it to test and explore the registration and authentication flows of your API.

[Postman Authentication Collection](<signup.postman_collection.json>)

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/24479347-0e395196-d630-4bd9-97b8-0e0733a11fd9?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D24479347-0e395196-d630-4bd9-97b8-0e0733a11fd9%26entityType%3Dcollection%26workspaceId%3D059651f8-4079-4c7a-b74b-21b5df992572)

### Postman Collections for API Endpoints

This collection covers a broader range of RESTful API endpoints related to Skin Videogame. It's a valuable resource for testing and exploring various API functionalities beyond authentication.

[Postman RESTful Collection](<RESTful_API_Skin_Videogame.postman_collection.json>)

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/24479347-ccb0f051-cfe8-4c40-b0d5-c5a9554a0260?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D24479347-ccb0f051-cfe8-4c40-b0d5-c5a9554a0260%26entityType%3Dcollection%26workspaceId%3D059651f8-4079-4c7a-b74b-21b5df992572)

### License

This project is licensed under the MIT License.