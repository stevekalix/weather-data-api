# Step-by-Step Commands to Show All 21 Columns with Data

## Do These 3 Steps in Order

---

## STEP 1: Delete Old Incomplete Data

Open MySQL and delete old records:

### Command:
```bash
mysql -u root -p
```

### Then type:
```sql
DELETE FROM weather_data;
EXIT;
```

### What it does:
- Removes all old records with null values
- Database is now empty
- Ready for complete data

---

## STEP 2: Upload the Complete CSV File

The file `weather_complete.csv` has been created for you!

Location: `C:\Users\Venkatesan\OneDrive\Desktop\WeatherData\weather_complete.csv`

### Upload it using curl:

```bash
curl -X POST -F "file=@weather_complete.csv" ^
  http://localhost:8081/api/weather/upload
```

**Note:** On Windows, if using PowerShell:
```powershell
$form = @{ file = Get-Item -Path "weather_complete.csv" }
Invoke-RestMethod -Uri "http://localhost:8081/api/weather/upload" `
  -Method Post -Form $form
```

### Expected Result:
```
File processed successfully. Saved 20 records.
```

---

## STEP 3: Get All Records (All 21 Columns with Data)

### Command:
```bash
curl http://localhost:8081/api/weather/all
```

### You Will See:
A JSON array with 20 records, each having all 21 columns:

```json
[
  {
    "id": 1,
    "datetime_utc": "2026-02-25T00:00:00Z",
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
    ... (all 21 columns with data)
  },
  ... (18 more records with all 21 columns each)
]
```

✅ **All 21 columns visible with data!**

---

## BONUS: View Just First Record

To see just the first record clearly:

```bash
curl http://localhost:8081/api/weather/all | jq '.[0]'
```

Output:
```json
{
  "id": 1,
  "datetime_utc": "2026-02-25T00:00:00Z",
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
```

---

## BONUS: Count Total Records

```bash
curl http://localhost:8081/api/weather/all | jq 'length'
```

Result: `20`

---

## BONUS: View Specific Record

```bash
curl http://localhost:8081/api/weather/5
```

Shows record with id=5 (all 21 columns)

---

## What the CSV File Contains

File: `weather_complete.csv`

20 rows of weather data with all 20 columns:
- datetime_utc (timestamps)
- _conds (conditions: Clear, Cloudy, Rainy, etc.)
- _dewptm (dew point temperature)
- _fog (0 or 1)
- _hail (0 or 1)
- _heatindexm (heat index)
- _hum (humidity percentage)
- _precipm (precipitation)
- _pressurem (atmospheric pressure)
- _rain (0 or 1)
- _snow (0 or 1)
- _tempm (temperature)
- _thunder (0 or 1)
- _tornado (0 or 1)
- _vism (visibility)
- _wdird (wind direction in degrees)
- _wdire (wind direction cardinal: N, S, E, W, etc.)
- _wgustm (wind gust speed)
- _windchillm (wind chill)
- _wspdm (wind speed)

Plus database auto-generates:
- id (1, 2, 3, ..., 20)

**Total: 21 columns**

---

## Summary of Results

After these 3 steps:

| Before | After |
|--------|-------|
| Many null values | All values populated |
| Incomplete data | Complete data |
| Missing columns | All 21 columns |
| 20 endpoints | Works perfectly |
| ❌ | ✅ |

---

## If Something Goes Wrong

### Problem: "File not found"
**Solution:** Make sure you're in the correct directory
```bash
cd C:\Users\Venkatesan\OneDrive\Desktop\WeatherData
ls weather_complete.csv
```

### Problem: "No records returned"
**Solution:** Make sure Step 1 and Step 2 completed
```bash
# Check if records exist
curl http://localhost:8081/api/weather/all
```

### Problem: Still seeing nulls
**Solution:** Make sure you deleted old data
```bash
# Check database
mysql -u root -p
SELECT COUNT(*) FROM weather_data;
```

Should show: 20 (if upload worked)

---

## Files Provided

✅ **weather_complete.csv** - Ready to upload
✅ **FIX_SHOW_ALL_COLUMNS.md** - Complete documentation
✅ **STEP_BY_STEP_COMMANDS.md** - This file

All you need to see all 21 columns with data!

---

**Ready? Follow the 3 steps above!**

