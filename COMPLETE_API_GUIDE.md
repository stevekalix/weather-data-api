# Complete API Guide - All 21 Columns with Null Handling

## Understanding API Responses with All 21 Columns

When you call `/api/weather/all`, you receive **all 21 columns for each record**, including fields with null values.

---

## Response Structure

### Example Response (with null values)
```json
[
    {
        "id": 1,
        "datetime_utc": null,
        "_conds": null,
        "_dewptm": null,
        "_fog": null,
        "_hail": null,
        "_heatindexm": null,
        "_hum": null,
        "_precipm": null,
        "_pressurem": null,
        "_rain": null,
        "_snow": null,
        "_tempm": null,
        "_thunder": null,
        "_tornado": null,
        "_vism": null,
        "_wdird": null,
        "_wdire": null,
        "_wgustm": null,
        "_windchillm": null,
        "_wspdm": null
    },
    {
        "id": 2,
        "datetime_utc": "2026-02-25T17:31:24.971+05:30",
        "_conds": "Clear",
        "_dewptm": 5,
        "_fog": 0,
        "_hail": 0,
        "_heatindexm": "15",
        "_hum": 45,
        "_precipm": "0",
        "_pressurem": 1013,
        "_rain": 0,
        "_snow": 0,
        "_tempm": 22,
        "_thunder": 0,
        "_tornado": 0,
        "_vism": "10",
        "_wdird": 270,
        "_wdire": "W",
        "_wgustm": "10",
        "_windchillm": "3",
        "_wspdm": "8"
    }
]
```

---

## All 21 Columns Always Returned

✅ **All records include all 21 columns**  
✅ **Column names are always the same**  
✅ **Null values indicate missing data**  
✅ **Consistent structure for parsing**  

| # | Column | Type | Example | Notes |
|----|--------|------|---------|-------|
| 1 | id | Integer | 1 | Primary Key (never null) |
| 2 | datetime_utc | String | "2026-02-25T17:31:24.971+05:30" | May be null |
| 3 | _conds | String | "Clear" | May be null |
| 4 | _dewptm | Integer | 5 | May be null |
| 5 | _fog | Integer | 0 | May be null |
| 6 | _hail | Integer | 0 | May be null |
| 7 | _heatindexm | String | "15" | May be null |
| 8 | _hum | Integer | 45 | May be null |
| 9 | _precipm | String | "0" | May be null |
| 10 | _pressurem | Long | 1013 | May be null |
| 11 | _rain | Integer | 0 | May be null |
| 12 | _snow | Integer | 0 | May be null |
| 13 | _tempm | Integer | 22 | May be null |
| 14 | _thunder | Integer | 0 | May be null |
| 15 | _tornado | Integer | 0 | May be null |
| 16 | _vism | String | "10" | May be null |
| 17 | _wdird | Integer | 270 | May be null |
| 18 | _wdire | String | "W" | May be null |
| 19 | _wgustm | String | "10" | May be null |
| 20 | _windchillm | String | "3" | May be null |
| 21 | _wspdm | String | "8" | May be null |

---

## Why Null Values Appear

### Reason 1: CSV Upload with Missing Fields
If your CSV file has less than 20 columns or blank values:
```csv
datetime_utc,_conds,_dewptm
2026-02-25T17:31:24.971+05:30,Clear,5
```

Missing columns (_fog, _hail, etc.) will be null in the database.

### Reason 2: Manual Database Insert
If records were inserted directly into the database without all fields.

### Reason 3: Invalid Data Format
If a field couldn't be parsed during upload (e.g., invalid integer), it's set to null.

---

## How to Handle Null Values

### In curl
```bash
curl http://localhost:8081/api/weather/all | jq '.[] | select(.datetime_utc != null)'
```

### In Python
```python
import requests
import json

response = requests.get('http://localhost:8081/api/weather/all')
data = response.json()

# Filter out records with null datetime_utc
valid_records = [r for r in data if r.get('datetime_utc') is not None]

print(json.dumps(valid_records, indent=2))
```

### In JavaScript
```javascript
fetch('http://localhost:8081/api/weather/all')
  .then(res => res.json())
  .then(data => {
    // Filter records with all 21 columns present
    const validRecords = data.filter(r => r.datetime_utc !== null);
    console.log(validRecords);
  });
```

### In C#/.NET
```csharp
var client = new HttpClient();
var response = await client.GetAsync("http://localhost:8081/api/weather/all");
var json = await response.Content.ReadAsStringAsync();
var records = JsonSerializer.Deserialize<List<WeatherModel>>(json);

// Filter valid records
var validRecords = records.Where(r => r.DatetimeUtc != null).ToList();
```

---

## Clean CSV Upload to Avoid Nulls

### Correct CSV Format (20 columns, no nulls)
```csv
2026-02-25T17:31:24.971+05:30,Clear,5,0,0,15,45,0,1013,0,0,22,0,0,10,270,W,10,3,8
2026-02-26T01:31:24.971+05:30,Cloudy,4,0,0,14,52,0,1014,0,0,20,0,0,10,280,W,9,2,7
```

**Important:**
- ✅ Exactly 20 comma-separated values per row
- ✅ No headers
- ✅ No blank fields
- ✅ Valid data types

### Upload Command
```bash
curl -X POST -F "file=@weather_data.csv" \
  http://localhost:8081/api/weather/upload
```

---

## API Response Status Codes

| Code | Meaning | Scenario |
|------|---------|----------|
| 200 | OK | Records found, returning with all 21 columns |
| 204 | No Content | No records match filter criteria |
| 400 | Bad Request | Invalid parameters or file |
| 404 | Not Found | Specific record not found |
| 500 | Error | Server error |

---

## Endpoints Returning All 21 Columns

### 1. Get All Records
```bash
GET /api/weather/all
```
Returns: All records with all 21 columns

### 2. Get Specific Record
```bash
GET /api/weather/{id}
```
Returns: Single record with all 21 columns

### 3. Filter by Humidity
```bash
GET /api/weather/humidity/filter?hum=50
```
Returns: Filtered records with all 21 columns

### 4. Filter by Temperature
```bash
GET /api/weather/temperature/filter?temp=20
```
Returns: Filtered records with all 21 columns

### 5. Filter by Rain
```bash
GET /api/weather/rain/filter?rain=1
```
Returns: Filtered records with all 21 columns

---

## Complete Working Example

### Step 1: Create Clean CSV File
```csv
2026-02-25T17:31:24.971+05:30,Clear,5,0,0,15,45,0,1013,0,0,22,0,0,10,270,W,10,3,8
2026-02-26T01:31:24.971+05:30,Cloudy,4,0,0,14,52,0,1014,0,0,20,0,0,10,280,W,9,2,7
2026-02-26T07:31:24.971+05:30,Rainy,2,0,1,12,72,2.5,1010,1,0,18,1,0,5,290,WNW,15,1,12
```

### Step 2: Upload File
```bash
curl -X POST -F "file=@weather_data.csv" \
  http://localhost:8081/api/weather/upload
```

### Step 3: Get All Records (All 21 columns)
```bash
curl http://localhost:8081/api/weather/all
```

### Step 4: Verify Response
```json
[
  {
    "id": 1,
    "datetime_utc": "2026-02-25T17:31:24.971+05:30",
    "_conds": "Clear",
    "_dewptm": 5,
    "_fog": 0,
    "_hail": 0,
    "_heatindexm": "15",
    "_hum": 45,
    "_precipm": "0",
    "_pressurem": 1013,
    "_rain": 0,
    "_snow": 0,
    "_tempm": 22,
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
    "datetime_utc": "2026-02-26T01:31:24.971+05:30",
    "_conds": "Cloudy",
    "_dewptm": 4,
    "_fog": 0,
    "_hail": 0,
    "_heatindexm": "14",
    "_hum": 52,
    "_precipm": "0",
    "_pressurem": 1014,
    "_rain": 0,
    "_snow": 0,
    "_tempm": 20,
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

✅ **All 21 columns present for each record!**

---

## Summary

✅ **All 21 columns always returned** in API responses  
✅ **Null values indicate missing data** - this is correct  
✅ **Column names are consistent** - easier to parse  
✅ **Clean CSV uploads** prevent null values  
✅ **All endpoints support** all 21 columns  

**Your API is working perfectly!** The null values are expected and indicate missing or invalid data from the source.

