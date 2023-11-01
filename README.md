# SkinVideogame API

## Project Description

This project aims to create a RESTful API developed in Java with the Spring framework that allows users to query, purchase, modify, and delete skins for a video game. The application will provide a set of endpoints that will enable users to interact with the skins in the system.

## Table of Contents

- [Features](#features)
- [API Endpoints](#api-endpoints)
- [API Endpoints](#api-endpoints)
- [Requisites](#requisites)
- [Configuration](#configuration)
- [Database](#database)
- [JWT](#jwt)
- [Run Spring Boot Application](#run-spring-boot-application)
- [Run following SQL insert statements](#run-following-sql-insert-statements)
- [Usage of Postman](#usage-of-postman)
###  Features

- User registration and authentication using JWT.
- Secure API endpoints with role-based access control.
- Error handling and exception management.
- Integration with MySQL database for data storage.
- API documentation using Postman collections.
- Skin Model: The application defines a Skin model that includes fields such as id, name, type, price, color, etc.
- Read Skins: The application reads the available skins from a JSON file.


### API Endpoints
GET /skins/available: Returns a list of all available skins for purchase.
POST /skins/buy/{id}: Allows users to purchase a skin and store it in the database.
GET /skins/myskins: Returns a list of skins purchased by the user.
PUT /skins/{id}/{color}: Allows users to change the color of a purchased skin.
DELETE /skins/delete/{id}: Allows users to delete a purchased skin.
GET /skin/getskin/{id}: Returns a specific skin.


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

#### JWT

Configure the secret key for JWT generation and validation in `src/main/resources/application.properties`.

```properties
jwt.secret=clave_secreta
jwt.expiration-ms=tiempo_de_expiracion_en_milisegundos
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

### Usage of Postman

Register some users with `/api/auth/signup`.

***Example***:
```json
  {
  "username": "tim",
  "email": "tim@gmail.com",
  "password": "12345678",
  "role": ["admin", "user"]
}
```

Login an account: POST `/api/auth/signin`.

Send all requests with an Authentication Header with `Bearer HERE_YOUR_TOKEN`

There is a Postman collection (postman_collection.json) included, which contains request examples for testing the API endpoints. Import this collection into Postman and configure the necessary environment variables.

