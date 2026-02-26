# Weather API Test Script
# Run this after starting the Spring Boot application

$baseUrl = "http://localhost:8081/api/weather"
$csvFilePath = "C:\Users\Venkatesan\OneDrive\Desktop\WeatherData\sample_weather.csv"

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Weather Data API Test Suite" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Test 1: Health Check
Write-Host "[1/9] Testing Health Check..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/health" -Method Get
    Write-Host "✓ Health Check PASSED" -ForegroundColor Green
    Write-Host "Response: $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Health Check FAILED" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 2: Upload CSV File
Write-Host "[2/9] Testing CSV File Upload..." -ForegroundColor Yellow
if (Test-Path $csvFilePath) {
    try {
        $form = @{
            file = Get-Item -Path $csvFilePath
        }
        $response = Invoke-RestMethod -Uri "$baseUrl/upload" -Method Post -Form $form
        Write-Host "✓ File Upload PASSED" -ForegroundColor Green
        Write-Host "Response: $response" -ForegroundColor Green
    } catch {
        Write-Host "✗ File Upload FAILED" -ForegroundColor Red
        Write-Host "Error: $_" -ForegroundColor Red
    }
} else {
    Write-Host "⚠ CSV file not found at $csvFilePath" -ForegroundColor Yellow
}
Write-Host ""

# Test 3: Get All Records
Write-Host "[3/9] Testing Get All Records..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/all" -Method Get
    if ($response) {
        Write-Host "✓ Get All Records PASSED" -ForegroundColor Green
        Write-Host "Found $(($response | Measure-Object).Count) records" -ForegroundColor Green
        if ($response.Count -gt 0) {
            Write-Host "First record ID: $($response[0].id)" -ForegroundColor Green
        }
    } else {
        Write-Host "⚠ No records found in database" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ Get All Records FAILED" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 4: Get Record by ID
Write-Host "[4/9] Testing Get Record by ID..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/1" -Method Get
    Write-Host "✓ Get Record by ID PASSED" -ForegroundColor Green
    Write-Host "Record ID: $($response.id), Temperature: $($response._tempm)" -ForegroundColor Green
} catch {
    Write-Host "✗ Get Record by ID FAILED (may be empty database)" -ForegroundColor Yellow
}
Write-Host ""

# Test 5: Filter by Humidity
Write-Host "[5/9] Testing Filter by Humidity..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/humidity/filter?hum=50" -Method Get
    if ($response) {
        Write-Host "✓ Filter by Humidity PASSED" -ForegroundColor Green
        Write-Host "Found $(($response | Measure-Object).Count) records with humidity > 50" -ForegroundColor Green
    } else {
        Write-Host "⚠ No records matching humidity filter" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ Filter by Humidity FAILED" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 6: Filter by Temperature
Write-Host "[6/9] Testing Filter by Temperature..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/temperature/filter?temp=15" -Method Get
    if ($response) {
        Write-Host "✓ Filter by Temperature PASSED" -ForegroundColor Green
        Write-Host "Found $(($response | Measure-Object).Count) records with temperature >= 15" -ForegroundColor Green
    } else {
        Write-Host "⚠ No records matching temperature filter" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ Filter by Temperature FAILED" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 7: Filter by Rain
Write-Host "[7/9] Testing Filter by Rain..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/rain/filter?rain=1" -Method Get
    if ($response) {
        Write-Host "✓ Filter by Rain PASSED" -ForegroundColor Green
        Write-Host "Found $(($response | Measure-Object).Count) records with rain" -ForegroundColor Green
    } else {
        Write-Host "⚠ No records matching rain filter" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ Filter by Rain FAILED" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 8: Update Record
Write-Host "[8/9] Testing Update Record..." -ForegroundColor Yellow
try {
    $updateData = @{
        datetime_utc = "2024-01-01T12:00:00"
        _conds = "Rainy"
        _tempm = 20
        _hum = 80
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "$baseUrl/update/1" `
        -Method Put `
        -ContentType "application/json" `
        -Body $updateData
    Write-Host "✓ Update Record PASSED" -ForegroundColor Green
    Write-Host "Response: $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Update Record FAILED (may be no record with ID 1)" -ForegroundColor Yellow
}
Write-Host ""

# Test 9: Delete Record
Write-Host "[9/9] Testing Delete Record..." -ForegroundColor Yellow
$confirmDelete = Read-Host "Do you want to delete test record ID 1? (y/n)"
if ($confirmDelete -eq 'y') {
    try {
        $response = Invoke-RestMethod -Uri "$baseUrl/1" -Method Delete
        Write-Host "✓ Delete Record PASSED" -ForegroundColor Green
        Write-Host "Response: $response" -ForegroundColor Green
    } catch {
        Write-Host "✗ Delete Record FAILED" -ForegroundColor Red
        Write-Host "Error: $_" -ForegroundColor Red
    }
} else {
    Write-Host "⊘ Delete test skipped" -ForegroundColor Yellow
}
Write-Host ""

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Test Suite Completed" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

