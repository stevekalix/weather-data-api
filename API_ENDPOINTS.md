# Weather Data API - Complete Endpoint Documentation

## Base URL
```
http://localhost:8081/api/weather
```

## Available Endpoints

### 1. Health Check
**Method:** GET  
**Endpoint:** `/health`  
**Description:** Check if the API is running  
**Response:** Plain text message

```bash
curl -X GET http://localhost:8081/api/weather/health
```

---

### 2. Upload Weather Data from CSV
**Method:** POST  
**Endpoint:** `/upload`  
**Content-Type:** multipart/form-data  
**Description:** Upload a CSV file containing weather data  
**Parameters:** 
- `file` (required): CSV file with weather data

```bash
curl -X POST -F "file=@sample_weather.csv" http://localhost:8081/api/weather/upload
```

**Expected Response:**
```json
"File processed successfully. Saved X records."
```

---

### 3. Get All Weather Records
**Method:** GET  
**Endpoint:** `/all`  
**Description:** Retrieve all weather records from the database  
**Response:** JSON array of weather objects

```bash
curl -X GET http://localhost:8081/api/weather/all
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "datetime_utc": "2024-01-01T12:00:00",
    "_conds": "Clear",
    "_dewptm": 5,
    "_fog": 0,
    "_hail": 0,
    "_heatindexm": "25",
    "_hum": 65,
    "_precipm": "0",
    "_pressurem": 1013,
    "_rain": 0,
    "_snow": 0,
    "_tempm": 22,
    "_thunder": 0,
    "_tornado": 0,
    "_vism": "10",
    "_wdird": 180,
    "_wdire": "S",
    "_wgustm": "15",
    "_windchillm": "20",
    "_wspdm": "10"
  }
]
```

---

### 4. Get Weather Record by ID
**Method:** GET  
**Endpoint:** `/{id}`  
**Description:** Retrieve a specific weather record by its ID  
**Parameters:** 
- `id` (path parameter): Record ID

```bash
curl -X GET http://localhost:8081/api/weather/1
```

---

### 5. Delete Weather Record
**Method:** DELETE  
**Endpoint:** `/{id}`  
**Description:** Delete a weather record by its ID  
**Parameters:** 
- `id` (path parameter): Record ID

```bash
curl -X DELETE http://localhost:8081/api/weather/1
```

**Expected Response:**
```json
"Deleted successfully"
```

---

### 6. Update Weather Record
**Method:** PUT  
**Endpoint:** `/update/{id}`  
**Content-Type:** application/json  
**Description:** Update a weather record  
**Parameters:** 
- `id` (path parameter): Record ID
- `body` (JSON): Updated weather object

```bash
curl -X PUT -H "Content-Type: application/json" \
  -d '{
    "datetime_utc": "2024-01-01T12:00:00",
    "_conds": "Cloudy",
    "_tempm": 25,
    "_hum": 70,
    "_rain": 1
  }' \
  http://localhost:8081/api/weather/update/1
```

**Expected Response:**
```json
"Updated successfully"
```

---

### 7. Filter by Humidity
**Method:** GET  
**Endpoint:** `/humidity/filter`  
**Description:** Get weather records with humidity greater than specified value  
**Parameters:** 
- `hum` (query parameter): Humidity threshold

```bash
curl -X GET "http://localhost:8081/api/weather/humidity/filter?hum=60"
```

**Expected Response:** JSON array of filtered weather objects

---

### 8. Filter by Temperature
**Method:** GET  
**Endpoint:** `/temperature/filter`  
**Description:** Get weather records with temperature greater than or equal to specified value  
**Parameters:** 
- `temp` (query parameter): Temperature threshold

```bash
curl -X GET "http://localhost:8081/api/weather/temperature/filter?temp=20"
```

**Expected Response:** JSON array of filtered weather objects

---

### 9. Filter by Rain
**Method:** GET  
**Endpoint:** `/rain/filter`  
**Description:** Get weather records with specific rain value  
**Parameters:** 
- `rain` (query parameter): Rain indicator (0 or 1)

```bash
curl -X GET "http://localhost:8081/api/weather/rain/filter?rain=1"
```

**Expected Response:** JSON array of filtered weather objects

---

## CSV File Format

The CSV file should have the following columns in this order:
1. datetime_utc
2. _conds
3. _dewptm
4. _fog
5. _hail
6. _heatindexm
7. _hum
8. _precipm
9. _pressurem
10. _rain
11. _snow
12. _tempm
13. _thunder
14. _tornado
15. _vism
16. _wdird
17. _wdire
18. _wgustm
19. _windchillm
20. _wspdm

**Example CSV:**
```csv
datetime_utc,_conds,_dewptm,_fog,_hail,_heatindexm,_hum,_precipm,_pressurem,_rain,_snow,_tempm,_thunder,_tornado,_vism,_wdird,_wdire,_wgustm,_windchillm,_wspdm
2024-01-01T12:00:00,Clear,5,0,0,25,65,0,1013,0,0,22,0,0,10,180,S,15,20,10
```

---

## HTTP Status Codes

- **200 OK:** Request successful
- **204 No Content:** Request successful but no data found
- **400 Bad Request:** Invalid request parameters
- **404 Not Found:** Resource not found
- **500 Internal Server Error:** Server error

---

## Testing Commands

### Test all endpoints:

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

