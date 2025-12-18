# QR Code Generator REST API

A Spring Boot REST API service that generates customizable QR codes in multiple image formats using the ZXing library. This project demonstrates proficiency in handling HTTP requests, image processing, and REST API design patterns.

## Features

- Generate QR codes from text content via REST API
- Support for multiple image formats (PNG, JPEG, GIF)
- Configurable QR code size (150-350 pixels)
- Four error correction levels (L, M, Q, H)
- Comprehensive input validation and error handling
- Health check endpoint for service monitoring

## Technologies

- **Java 21** - Modern Java features and performance
- **Spring Boot 4.0.0** - RESTful web service framework
- **ZXing 3.5.2** - QR code generation library
- **Gradle** - Build automation and dependency management

## API Endpoints

### Health Check
```
GET /api/health
```
Returns 200 OK if the service is running.

### Generate QR Code
```
GET /api/qrcode
```

#### Parameters

| Parameter | Type | Default | Range/Values | Description |
|-----------|------|---------|--------------|-------------|
| `contents` | String | (required) | Non-blank string | Content to encode in QR code |
| `size` | Integer | 250 | 150-350 | QR code dimensions in pixels |
| `type` | String | png | png, jpeg, gif | Output image format |
| `correction` | String | L | L, M, Q, H | Error correction level |

#### Error Correction Levels
- **L** - Low (7% recovery)
- **M** - Medium (15% recovery)
- **Q** - Quartile (25% recovery)
- **H** - High (30% recovery)

#### Example Requests

Generate a basic QR code:
```bash
curl "http://localhost:8080/api/qrcode?contents=Hello%20World"
```

Generate a 300x300 PNG with high error correction:
```bash
curl "http://localhost:8080/api/qrcode?contents=https://github.com&size=300&type=png&correction=H" --output qrcode.png
```

Generate a JPEG QR code:
```bash
curl "http://localhost:8080/api/qrcode?contents=My%20Data&type=jpeg" --output qrcode.jpeg
```

#### Error Responses

**400 Bad Request** - Invalid parameters:
```json
{
  "error": "Contents cannot be null or blank"
}
```
```json
{
  "error": "Image size must be between 150 and 350 pixels"
}
```
```json
{
  "error": "Permitted error correction levels are L, M, Q, H"
}
```
```json
{
  "error": "Only png, jpeg and gif image types are supported"
}
```

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle (or use the included wrapper)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd qrcode
```

2. Build the project:
```bash
./gradlew build
```

3. Run the application:
```bash
./gradlew bootRun
```

The service will start on `http://localhost:8080`


## Implementation Highlights

### QR Code Generation
The service uses ZXing's `QRCodeWriter` to encode data into a `BitMatrix`, which is then converted to a `BufferedImage` and served as a byte array response.

### Input Validation
Comprehensive validation ensures:
- Content is not null or blank
- Size is within acceptable bounds (150-350 pixels)
- Error correction level is valid (L, M, Q, H)
- Image format is supported (PNG, JPEG, GIF)

### Content Type Handling
Proper HTTP content types are set based on the requested image format using Spring's `MediaType` constants.

## Skills Demonstrated

- RESTful API design with Spring Boot
- HTTP request/response handling
- Query parameter processing and validation
- Image generation and manipulation
- Third-party library integration (ZXing)
- Error handling and appropriate HTTP status codes


## License

This project was created as part of the JetBrains Academy Hyperskill learning track.

## Author

pafsmith
