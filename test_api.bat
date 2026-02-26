@echo off
REM Weather Data API Testing Script

echo ========================================
echo Weather Data API - Test Commands
echo ========================================
echo.

echo 1. Testing GET /api/all (Get all records)
echo Command: curl -X GET http://localhost:8081/api/all
echo.

echo 2. Testing POST /api/file (Upload CSV)
echo Command: curl -X POST http://localhost:8081/api/file -F "file=@sample_weather.csv"
echo.

echo 3. Testing GET /api/1 (Get record by ID)
echo Command: curl -X GET http://localhost:8081/api/1
echo.

echo 4. Testing GET /api/humidity/filter (Filter by humidity)
echo Command: curl -X GET "http://localhost:8081/api/humidity/filter?hum=50"
echo.

echo 5. Testing GET /api/temperature/filter (Filter by temperature)
echo Command: curl -X GET "http://localhost:8081/api/temperature/filter?temp=25"
echo.

echo 6. Testing GET /api/rain/filter (Filter by rain)
echo Command: curl -X GET "http://localhost:8081/api/rain/filter?rain=1"
echo.

echo 7. Testing PUT /api/update/1 (Update record)
echo Command: curl -X PUT http://localhost:8081/api/update/1 -H "Content-Type: application/json" -d "{\"_conds\":\"Modified\",\"_tempm\":30}"
echo.

echo 8. Testing DELETE /api/1 (Delete record)
echo Command: curl -X DELETE http://localhost:8081/api/1
echo.

echo ========================================
echo Running First Test: GET /api/all
echo ========================================
curl -X GET http://localhost:8081/api/all
echo.
echo.

pause

