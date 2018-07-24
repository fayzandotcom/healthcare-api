# Healthcare API

REST Web service for healthcare system where users can search for doctors and view their details.

Database script of this web service can be find here https://github.com/fayzandotcom/healthcare-db 
APIs in this projects are consumed by an Angular web UI (https://github.com/fayzandotcom/healthcare-ui)

## Application demo 

API Endpoint: http://45.76.152.208:8083

Web UI: http://45.76.152.208/healthcare

Login Details 

email=fayzan@hotmail.com

password=fayzan


## Description

### Technology Stack
- Java 8
- Spring Boot 1.5.7
- MySQL 5.6

Note: Lombok Project is used for POJO classes. For IDE setup follow https://projectlombok.org/setup/overview

## REST API

### 1. Sign Up
URI: /user/sign-up 

Method: POST

#### Request Body  

Content-Type: multipart/form-data

| Parameter     | Type    |
| -------------	|---------|
| email  	| string  |
| password	| string  |
 
 
### 2. Login
URI: /login 

Method: POST

#### Request Body  

Content-Type: multipart/form-data

| Parameter     | Type    |
| -------------	|---------|
| email  	| string  |
| password	| string  |
 
 #### Response Body
 
 Content-Type: application/json
 
| Parameter     | Type    |
| -------------	|---------|
| token  	| string  |

### 3. Doctor Search
URI: /doctors 

Method: GET

#### Query Param  

| Parameter     | Type    | Description |
| -------------	|---------| ------------|
| query  	| string  | search keyword	|
| location	| string  | (lat,lon,range (miles))	|
| user_location	| string  | (lat,lon)	|
| skip		| integer |		|
| limit		| integer |		|
 
#### Response Body
 
 Content-Type: application/json
 
 List of doctors
 
### 3. Doctor Details
URI: /doctors/{uid} 

Method: GET

#### Path Param  

| Parameter     | Type    | Description |
| -------------	|---------| ------------|
| uid	  	| string  | doctor uid	|
 
 #### Response Body
 
 Content-Type: application/json
 
 Doctor object


## Build and Run

Maven is used for dependencies and project build.

To build the project run following maven command   
`mvn clean package`  

Exceute following command to run the application  
`mvn spring-boot:run`  

Open the following URL in browser  
`localhost:8082/`  

Note: The default port set for this application is 8082 in application.properties file. Web server in the application will start on port 8081
