🌦 Weather Data Management System
📌 Project Overview

The Weather Data Management System is a Spring Boot REST API application that allows users to upload weather data from a CSV file and perform CRUD operations on stored weather records.

This project demonstrates:

File Upload using MultipartFile

Data parsing from CSV

Database integration using Spring Data JPA

REST API development

Filtering data using Streams

🛠 Technologies Used

Java 17

Spring Boot

Spring Web

Spring Data JPA

MySQL / H2 Database

Maven

⚙ Setup & Run Instructions
1️⃣ Clone Repository
git clone https://github.com/your-username/weather-data-api.git
cd weather-data-api
2️⃣ Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/weather_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3️⃣ Run Application

Run the main class:

WeatherDataApplication.java

Application runs at:

http://localhost:8080
🌐 API Details

Base URL:

http://localhost:8080/api
1️⃣ Upload Weather CSV File

Endpoint:

POST /api/file

Request Type:
form-data

Key:

file → Upload CSV file

Response:

File processed successfully
2️⃣ Get All Weather Records
GET /api/all
3️⃣ Get Weather by ID
GET /api/{id}

Example:

GET /api/1
4️⃣ Update Weather by ID
PUT /api/update/{id}

Body (JSON):

{
  "datetime_utc": "2024-01-01",
  "hum": 80,
  "tempm": 30
}
5️⃣ Delete Weather by ID
DELETE /api/{id}
6️⃣ Get Weather Records Where Humidity > Given Value
GET /api/humidity?hum=80
📂 Project Structure
WeatherData
│
├── controller
│     └── WeatherController.java
├── model
│     └── WeatherModel.java
├── repository
│     └── WeatherRepo.java
└── WeatherDataApplication.java
🚀 Future Improvements

Add Global Exception Handling

Add Pagination

Add Sorting

Add Swagger Documentation
