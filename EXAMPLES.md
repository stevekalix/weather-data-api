# API Request/Response Examples

## 1. Health Check

### Request
```
GET /api/weather/health
```

### Response
```
Status: 200 OK
Body: Weather API is running successfully!
```

---

## 2. Upload CSV File

### Request
```
POST /api/weather/upload
Content-Type: multipart/form-data

file: [CSV file]
```

### Sample CSV Content
```csv
datetime_utc,_conds,_dewptm,_fog,_hail,_heatindexm,_hum,_precipm,_pressurem,_rain,_snow,_tempm,_thunder,_tornado,_vism,_wdird,_wdire,_wgustm,_windchillm,_wspdm
2024-01-01T00:00:00,Clear,2,0,0,15,45,0,1013,0,0,5,0,0,10,270,W,10,3,8
2024-01-01T01:00:00,Partly Cloudy,1,0,0,14,48,0,1014,0,0,4,0,0,10,280,W,9,2,7
2024-01-01T02:00:00,Cloudy,0,0,0,13,52,0,1015,0,0,3,0,0,10,290,WNW,8,1,6
```

### Response - Success
```
Status: 200 OK
Body: File processed successfully. Saved 3 records.
```

### Response - Error (Empty File)
```
Status: 400 Bad Request
Body: File is empty
```

### Response - Error (Invalid Columns)
```
Status: 500 Internal Server Error
Body: Error processing the file: Error parsing line X: ...
```

---

## 3. Get All Records

### Request
```
GET /api/weather/all
```

### Response - With Data
```
Status: 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "datetime_utc": "2024-01-01T00:00:00",
    "_conds": "Clear",
    "_dewptm": 2,
    "_fog": 0,
    "_hail": 0,
    "_heatindexm": "15",
    "_hum": 45,
    "_precipm": "0",
    "_pressurem": 1013,
    "_rain": 0,
    "_snow": 0,
    "_tempm": 5,
    "_thunder": 0,
    "_tornado": 0,
    "_vism": "10",
    "_wdird": 270,
    "_wdire": "W",
    "_wgustm": "10",
    "_windchillm": "3",
    "_wspdm": "8"
  },
  {
    "id": 2,
    "datetime_utc": "2024-01-01T01:00:00",
    "_conds": "Partly Cloudy",
    "_dewptm": 1,
    "_fog": 0,
    "_hail": 0,
    "_heatindexm": "14",
    "_hum": 48,
    "_precipm": "0",
    "_pressurem": 1014,
    "_rain": 0,
    "_snow": 0,
    "_tempm": 4,
    "_thunder": 0,
    "_tornado": 0,
    "_vism": "10",
    "_wdird": 280,
    "_wdire": "W",
    "_wgustm": "9",
    "_windchillm": "2",
    "_wspdm": "7"
  }
]
```

### Response - No Data
```
Status: 204 No Content
```

---

## 4. Get Record by ID

### Request
```
GET /api/weather/1
```

### Response - Success
```
Status: 200 OK
Content-Type: application/json

{
  "id": 1,
  "datetime_utc": "2024-01-01T00:00:00",
  "_conds": "Clear",
  "_dewptm": 2,
  "_fog": 0,
  "_hail": 0,
  "_heatindexm": "15",
  "_hum": 45,
  "_precipm": "0",
  "_pressurem": 1013,
  "_rain": 0,
  "_snow": 0,
  "_tempm": 5,
  "_thunder": 0,
  "_tornado": 0,
  "_vism": "10",
  "_wdird": 270,
  "_wdire": "W",
  "_wgustm": "10",
  "_windchillm": "3",
  "_wspdm": "8"
}
```

### Response - Record Not Found
```
Status: 204 No Content
```

---

## 5. Update Record

### Request
```
PUT /api/weather/update/1
Content-Type: application/json

{
  "datetime_utc": "2024-01-01T00:00:00",
  "_conds": "Rainy",
  "_dewptm": 5,
  "_fog": 1,
  "_hail": 0,
  "_heatindexm": "18",
  "_hum": 70,
  "_precipm": "2.5",
  "_pressurem": 1010,
  "_rain": 1,
  "_snow": 0,
  "_tempm": 8,
  "_thunder": 1,
  "_tornado": 0,
  "_vism": "5",
  "_wdird": 180,
  "_wdire": "S",
  "_wgustm": "20",
  "_windchillm": "5",
  "_wspdm": "15"
}
```

### Response - Success
```
Status: 200 OK
Body: Updated successfully
```

### Response - Not Found
```
Status: 404 Not Found
```

### Response - Error
```
Status: 500 Internal Server Error
Body: Error updating record: ...
```

---

## 6. Delete Record

### Request
```
DELETE /api/weather/1
```

### Response - Success
```
Status: 200 OK
Body: Deleted successfully
```

### Response - Not Found
```
Status: 404 Not Found
```

### Response - Error
```
Status: 500 Internal Server Error
Body: Error deleting record: ...
```

---

## 7. Filter by Humidity

### Request
```
GET /api/weather/humidity/filter?hum=50
```

Explanation: Get all records where humidity > 50

### Response - Found Records
```
Status: 200 OK
Content-Type: application/json

[
  {
    "id": 2,
    "datetime_utc": "2024-01-01T01:00:00",
    "_conds": "Partly Cloudy",
    "_hum": 48,
    ...
  },
  {
    "id": 3,
    "datetime_utc": "2024-01-01T02:00:00",
    "_conds": "Cloudy",
    "_hum": 52,
    ...
  }
]
```

### Response - No Matches
```
Status: 204 No Content
```

---

## 8. Filter by Temperature

### Request
```
GET /api/weather/temperature/filter?temp=5
```

Explanation: Get all records where temperature >= 5

### Response - Found Records
```
Status: 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "datetime_utc": "2024-01-01T00:00:00",
    "_conds": "Clear",
    "_tempm": 5,
    ...
  },
  {
    "id": 2,
    "datetime_utc": "2024-01-01T01:00:00",
    "_conds": "Partly Cloudy",
    "_tempm": 8,
    ...
  }
]
```

### Response - No Matches
```
Status: 204 No Content
```

---

## 9. Filter by Rain

### Request
```
GET /api/weather/rain/filter?rain=1
```

Explanation: Get all records where it rained (rain=1)

### Response - Found Records
```
Status: 200 OK
Content-Type: application/json

[
  {
    "id": 5,
    "datetime_utc": "2024-01-01T05:00:00",
    "_conds": "Rainy",
    "_rain": 1,
    ...
  },
  {
    "id": 7,
    "datetime_utc": "2024-01-01T07:00:00",
    "_conds": "Thunderstorm",
    "_rain": 1,
    ...
  }
]
```

### Response - No Matches
```
Status: 204 No Content
```

---

## HTTP Status Code Reference

| Code | Meaning | When Returned |
|------|---------|---------------|
| 200 | OK | Request successful, data found |
| 204 | No Content | Request successful, no data found |
| 400 | Bad Request | Invalid request (e.g., empty file) |
| 404 | Not Found | Resource doesn't exist |
| 500 | Internal Server Error | Server-side error |

---

## Example Complete Workflow

### Step 1: Check Health
```bash
curl http://localhost:8081/api/weather/health
# Response: Weather API is running successfully!
```

### Step 2: Upload Data
```bash
curl -X POST -F "file=@sample_weather.csv" http://localhost:8081/api/weather/upload
# Response: File processed successfully. Saved 100 records.
```

### Step 3: Get All Records
```bash
curl http://localhost:8081/api/weather/all
# Response: [Array of 100 weather records]
```

### Step 4: Filter Records
```bash
curl "http://localhost:8081/api/weather/humidity/filter?hum=60"
# Response: [Weather records with humidity > 60]
```

### Step 5: Get Specific Record
```bash
curl http://localhost:8081/api/weather/1
# Response: {Weather record with ID 1}
```

### Step 6: Update Record
```bash
curl -X PUT -H "Content-Type: application/json" \
  -d '{"_tempm": 25}' \
  http://localhost:8081/api/weather/update/1
# Response: Updated successfully
```

### Step 7: Verify Update
```bash
curl http://localhost:8081/api/weather/1
# Response: {Weather record with updated temperature}
```

### Step 8: Delete Record
```bash
curl -X DELETE http://localhost:8081/api/weather/1
# Response: Deleted successfully
```

---

## Response Time Expectations

| Operation | Expected Time |
|-----------|---------------|
| Health Check | < 10ms |
| Get All (100 records) | 20-50ms |
| Get by ID | 10-20ms |
| Upload (100 records) | 100-200ms |
| Filter (100 records) | 30-80ms |
| Update | 15-30ms |
| Delete | 10-20ms |

---

## Data Types Reference

| Field | Type | Size | Example |
|-------|------|------|---------|
| id | Integer | 4 bytes | 1, 2, 3 |
| datetime_utc | String | 255 chars | 2024-01-01T00:00:00 |
| _tempm | Integer | 4 bytes | -10 to 50 |
| _hum | Integer | 4 bytes | 0 to 100 |
| _rain | Integer | 4 bytes | 0 or 1 |
| _conds | String | 255 chars | Clear, Rainy, Cloudy |
| _pressurem | Long | 8 bytes | 900 to 1100 |

