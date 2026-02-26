# Weather Data API - Spring Boot Application

## Overview
This is a complete Spring Boot REST API application for managing weather data. It provides endpoints to upload, retrieve, filter, update, and delete weather records stored in a MySQL database.

## Project Structure
```
WeatherData/
├── src/
│   ├── main/
│   │   ├── java/Weather/com/WeatherData/
│   │   │   ├── WeatherDataApplication.java (Main Application)
│   │   │   ├── controller/
│   │   │   │   └── WeatherController.java (API Endpoints)
│   │   │   ├── model/
│   │   │   │   └── WeatherModel.java (Entity Model)
│   │   │   └── repository/
│   │   │       └── WeatherRepo.java (Database Repository)
│   │   └── resources/
│   │       └── application.properties (Configuration)
│   └── test/
│       └── java/Weather/com/WeatherData/
│           └── WeatherDataApplicationTests.java
├── pom.xml (Maven Dependencies)
├── mvnw / mvnw.cmd (Maven Wrapper)
└── README.md (This file)
```

## Prerequisites
- Java 21 or higher
- Maven 3.6+
- MySQL 8.0 or higher
- curl or Postman for API testing

## Setup Instructions

### 1. Database Setup
Create a MySQL database:
```sql
CREATE DATABASE weatherdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Update Database Credentials
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/weatherdb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root123
```

Update the password if your MySQL password is different.

### 3. Build the Project
```bash
cd WeatherData
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start at: `http://localhost:8081`

## API Endpoints

### 1. Health Check
```
GET /api/weather/health
```
Check if the API is running.

### 2. Upload Weather Data
```
POST /api/weather/upload
```
Upload a CSV file containing weather data.
- **Content-Type:** multipart/form-data
- **Parameter:** file (CSV file)

### 3. Get All Records
```
GET /api/weather/all
```
Retrieve all weather records.

### 4. Get Record by ID
```
GET /api/weather/{id}
```
Get a specific weather record by its ID.

### 5. Update Record
```
PUT /api/weather/update/{id}
```
Update a specific weather record.
- **Content-Type:** application/json
- **Body:** JSON object with fields to update

### 6. Delete Record
```
DELETE /api/weather/{id}
```
Delete a weather record by its ID.

### 7. Filter by Humidity
```
GET /api/weather/humidity/filter?hum={value}
```
Get records with humidity greater than the specified value.

### 8. Filter by Temperature
```
GET /api/weather/temperature/filter?temp={value}
```
Get records with temperature greater than or equal to the specified value.

### 9. Filter by Rain
```
GET /api/weather/rain/filter?rain={0|1}
```
Get records with specific rain indicator (0 = no rain, 1 = rain).

## CSV File Format

The CSV file must have the following columns in order:
```
datetime_utc,_conds,_dewptm,_fog,_hail,_heatindexm,_hum,_precipm,_pressurem,_rain,_snow,_tempm,_thunder,_tornado,_vism,_wdird,_wdire,_wgustm,_windchillm,_wspdm
```

**Example:**
```csv
2024-01-01T12:00:00,Clear,5,0,0,25,65,0,1013,0,0,22,0,0,10,180,S,15,20,10
2024-01-01T13:00:00,Cloudy,6,0,0,26,68,0,1012,0,0,23,0,0,10,190,SSW,16,21,11
```

## Testing the API

### Using curl (Command Line)

```bash
# 1. Health check
curl -X GET http://localhost:8081/api/weather/health

# 2. Upload CSV file
curl -X POST -F "file=@sample_weather.csv" http://localhost:8081/api/weather/upload

# 3. Get all records
curl -X GET http://localhost:8081/api/weather/all

# 4. Get specific record
curl -X GET http://localhost:8081/api/weather/1

# 5. Filter by humidity
curl -X GET "http://localhost:8081/api/weather/humidity/filter?hum=60"

# 6. Filter by temperature
curl -X GET "http://localhost:8081/api/weather/temperature/filter?temp=20"

# 7. Filter by rain
curl -X GET "http://localhost:8081/api/weather/rain/filter?rain=1"

# 8. Update a record
curl -X PUT -H "Content-Type: application/json" \
  -d '{"_tempm": 25, "_hum": 70}' \
  http://localhost:8081/api/weather/update/1

# 9. Delete a record
curl -X DELETE http://localhost:8081/api/weather/1
```

### Using PowerShell Script

Run the comprehensive test script:
```powershell
.\test_api_comprehensive.ps1
```

### Using Postman

1. Import the collection or manually create requests for each endpoint
2. Set the base URL: `http://localhost:8081/api/weather`
3. Test each endpoint with appropriate methods and parameters

## Database Schema

The `weather_data` table will be automatically created with the following structure:

| Column | Type | Nullable |
|--------|------|----------|
| id | INT | NO (Primary Key, Auto Increment) |
| datetime_utc | VARCHAR(255) | YES |
| _conds | VARCHAR(255) | YES |
| _dewptm | INT | YES |
| _fog | INT | YES |
| _hail | INT | YES |
| _heatindexm | VARCHAR(255) | YES |
| _hum | INT | YES |
| _precipm | VARCHAR(255) | YES |
| _pressurem | BIGINT | YES |
| _rain | INT | YES |
| _snow | INT | YES |
| _tempm | INT | YES |
| _thunder | INT | YES |
| _tornado | INT | YES |
| _vism | VARCHAR(255) | YES |
| _wdird | INT | YES |
| _wdire | VARCHAR(255) | YES |
| _wgustm | VARCHAR(255) | YES |
| _windchillm | VARCHAR(255) | YES |
| _wspdm | VARCHAR(255) | YES |

## Dependencies

- **Spring Boot 4.0.3**
- **Spring Data JPA** - Database operations
- **Hibernate 7.2.4** - ORM
- **MySQL Connector/J 9.6.0** - MySQL driver
- **Lombok** - Reduce boilerplate code
- **OpenCSV 5.8** - CSV processing
- **Jackson** - JSON serialization

## Configuration

The application is configured in `src/main/resources/application.properties`:

```properties
# Server port
server.port=8081

# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weatherdb?...
spring.datasource.username=root
spring.datasource.password=root123

# JPA/Hibernate configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Multipart file upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## Common Issues & Solutions

### Issue: Connection refused to MySQL
**Solution:** Ensure MySQL is running and credentials in `application.properties` are correct.

### Issue: File upload returns "Current request is not a multipart request"
**Solution:** Ensure Content-Type is set to `multipart/form-data` when uploading files.

### Issue: POST method not allowed error
**Solution:** Use POST method, not GET, for the `/upload` endpoint.

### Issue: No records found after upload
**Solution:** Verify the CSV file format matches the expected schema and has at least 20 columns.

## Development

### Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Run Application
```bash
mvn spring-boot:run
```

### Package
```bash
mvn clean package
```

The JAR file will be created at: `target/WeatherData-0.0.1-SNAPSHOT.jar`

## License
This project is open source and available under the MIT License.

## Contact & Support
For issues or questions, please refer to the API_ENDPOINTS.md file for detailed endpoint documentation.

