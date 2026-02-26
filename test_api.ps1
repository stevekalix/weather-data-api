# Weather Data API Testing Script (PowerShell)

$baseURL = "http://localhost:8081/api"

Write-Host "========================================" -ForegroundColor Green
Write-Host "Weather Data API - Comprehensive Tests" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Test 1: Health Check - Get all records (empty at first)
Write-Host "TEST 1: Get All Records (GET /api/all)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/all" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 2: Upload CSV File
Write-Host "TEST 2: Upload CSV File (POST /api/file)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
$csvPath = "C:\Users\Venkatesan\OneDrive\Desktop\WeatherData\sample_weather.csv"
if (Test-Path $csvPath) {
    try {
        $fileContent = Get-Content $csvPath -Raw
        Write-Host "File found: $csvPath" -ForegroundColor Green
        Write-Host "Uploading..." -ForegroundColor Cyan

        # PowerShell multipart file upload
        $fileBin = [System.IO.File]::ReadAllBytes($csvPath)
        $boundary = [System.Guid]::NewGuid().ToString()
        $LF = "`r`n"
        $bodyLines = (
            "--$boundary",
            "Content-Disposition: form-data; name=`"file`"; filename=`"sample_weather.csv`"",
            "Content-Type: application/octet-stream$LF",
            [System.Text.Encoding]::UTF8.GetString($fileBin),
            "--$boundary--"
        ) -join $LF

        $response = Invoke-WebRequest -Uri "$baseURL/file" `
            -Method POST `
            -ContentType "multipart/form-data; boundary=$boundary" `
            -Body $bodyLines `
            -UseBasicParsing

        Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
        Write-Host "Response:" -ForegroundColor Cyan
        Write-Host $response.Content
        Write-Host ""
    } catch {
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host ""
    }
} else {
    Write-Host "CSV file not found: $csvPath" -ForegroundColor Red
    Write-Host ""
}

# Wait a moment for data to be saved
Start-Sleep -Seconds 2

# Test 3: Get all records again (should have data now)
Write-Host "TEST 3: Get All Records Again (GET /api/all)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/all" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 4: Get record by ID
Write-Host "TEST 4: Get Record by ID (GET /api/1)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/1" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 5: Filter by humidity
Write-Host "TEST 5: Filter by Humidity > 70 (GET /api/humidity/filter?hum=70)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/humidity/filter?hum=70" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 6: Filter by temperature
Write-Host "TEST 6: Filter by Temperature >= 20 (GET /api/temperature/filter?temp=20)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/temperature/filter?temp=20" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 7: Filter by rain
Write-Host "TEST 7: Filter by Rain = 1 (GET /api/rain/filter?rain=1)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/rain/filter?rain=1" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 8: Update a record
Write-Host "TEST 8: Update Record (PUT /api/update/1)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $updateBody = @{
        "_conds" = "Modified"
        "_tempm" = 30
        "_hum" = 55
    } | ConvertTo-Json

    $response = Invoke-WebRequest -Uri "$baseURL/update/1" `
        -Method PUT `
        -ContentType "application/json" `
        -Body $updateBody `
        -UseBasicParsing

    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test 9: Verify update
Write-Host "TEST 9: Verify Update (GET /api/1)" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseURL/1" -Method GET -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response:" -ForegroundColor Cyan
    Write-Host $response.Content
    Write-Host ""
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

Write-Host "========================================" -ForegroundColor Green
Write-Host "Testing Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

