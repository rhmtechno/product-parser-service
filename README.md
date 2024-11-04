##### Product Parser (sample products_data.xlsx file included in project Directory)

## API Documentation
Access the Swagger UI for API documentation at: [Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Building the Project

### If Database Connection is Available (Please check db properties at application-dev.yml)

mvn clean install

### For Docker Image Build and Run

docker build -t product-parser .

docker run -d -p 8080:8080 --name product-parser-container product-parser

### For Docker Compose (PostgresSQL Included)
docker-compose up --build
