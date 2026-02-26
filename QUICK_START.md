# Quick Start Guide - Weather Data API

## 🚀 Quick Start (5 minutes)

### Step 1: Ensure MySQL is Running
```bash
# Verify MySQL is running on port 3306
mysql -u root -p
# Type your password and verify connection
exit
```

### Step 2: Start the Application
```bash
cd C:\Users\Venkatesan\OneDrive\Desktop\WeatherData\WeatherData
mvn spring-boot:run
```

Wait for this message:
```
Tomcat started on port 8081 (http) with context path '/'
Started WeatherDataApplication in X.XXX seconds
```

### Step 3: Test the Health Endpoint
In a new terminal:
```bash
curl -X GET http://localhost:8081/api/weather/health
```

Expected Response:
```
Weather API is running successfully!
```

### Step 4: Upload Weather Data
```bash
curl -X POST -F "file=@C:\Users\Venkatesan\OneDrive\Desktop\WeatherData\sample_weather.csv" \
  http://localhost:8081/api/weather/upload
```

Expected Response:
```json
"File processed successfully. Saved X records."
```

### Step 5: Retrieve All Records
```bash
curl -X GET http://localhost:8081/api/weather/all
```

---

## 📋 All Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/weather/health` | Health check |
| POST | `/api/weather/upload` | Upload CSV file |
| GET | `/api/weather/all` | Get all records |
| GET | `/api/weather/{id}` | Get record by ID |
| PUT | `/api/weather/update/{id}` | Update record |
| DELETE | `/api/weather/{id}` | Delete record |
| GET | `/api/weather/humidity/filter?hum={value}` | Filter by humidity |
| GET | `/api/weather/temperature/filter?temp={value}` | Filter by temperature |
| GET | `/api/weather/rain/filter?rain={0\|1}` | Filter by rain |

---

## 💻 Using PowerShell for Testing

Run the comprehensive test script:
```powershell
.\test_api_comprehensive.ps1
```

This will automatically test all endpoints and show results.

---

## 📱 Using Postman

1. Import the collection:
   - File → Import
   - Select `WeatherAPI_Postman_Collection.json`
   
2. Set the base URL to: `http://localhost:8081`

3. Run the requests in order

---

## 🛠️ Common Commands

### Compact curl commands:

```bash
# Health check
curl http://localhost:8081/api/weather/health

# Get all
curl http://localhost:8081/api/weather/all

# Get by ID
curl http://localhost:8081/api/weather/1

# Filter humidity
curl "http://localhost:8081/api/weather/humidity/filter?hum=60"

# Filter temperature
curl "http://localhost:8081/api/weather/temperature/filter?temp=20"

# Filter rain
curl "http://localhost:8081/api/weather/rain/filter?rain=1"

# Delete record
curl -X DELETE http://localhost:8081/api/weather/1
```

---

## 📊 CSV File Format

Your CSV file must have these columns in order (no header needed after the first line):
```
datetime_utc,_conds,_dewptm,_fog,_hail,_heatindexm,_hum,_precipm,_pressurem,_rain,_snow,_tempm,_thunder,_tornado,_vism,_wdird,_wdire,_wgustm,_windchillm,_wspdm
```

---

## 🔧 Troubleshooting

### API shows "Connection refused"
- ✓ Check if application is running (should see "Tomcat started on port 8081")
- ✓ Check MySQL is running

### File upload returns error
- ✓ Use POST method (not PUT/GET)
- ✓ Set Content-Type to multipart/form-data
- ✓ CSV must have 20 columns

### No records showing
- ✓ Upload a CSV file first
- ✓ Check database connection in application.properties
- ✓ Verify MySQL username/password

---

## 📁 Project Files

- **WeatherDataApplication.java** - Main Spring Boot entry point
- **WeatherController.java** - All API endpoints
- **WeatherModel.java** - Database entity class
- **WeatherRepo.java** - Database repository
- **application.properties** - Configuration file
- **pom.xml** - Maven dependencies

---

## 🎯 Next Steps

1. Upload your CSV file using the `/upload` endpoint
2. Test filters and queries
3. Customize the model for your needs
4. Deploy to production

---

## 📚 Documentation

- **API_ENDPOINTS.md** - Detailed endpoint documentation
- **SETUP_README.md** - Full setup and configuration guide
- **WeatherAPI_Postman_Collection.json** - Postman collection for testing

---

## ✅ Verification Checklist

- [ ] MySQL is running and accessible
- [ ] Application starts without errors
- [ ] Health endpoint responds
- [ ] CSV file is in correct format
- [ ] File uploads successfully
- [ ] Records are retrieved
- [ ] Filters work correctly
- [ ] Updates work correctly
- [ ] Deletes work correctly

**All Done! Your API is ready to use.** 🎉

