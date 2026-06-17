# Supplier Document Platform

A full-stack supplier document management platform built with **Spring Boot, React, PostgreSQL, Docker, AWS EC2, AWS S3, IAM Roles, and Terraform**.

This project was built as a real-world style backend/cloud engineering project. The goal was not just to create a basic CRUD app, but to build something that looks closer to how an engineering or quality team might manage supplier documents in a production environment.

---

## Why I Built This Project

In engineering and manufacturing environments, supplier documents are often handled across multiple systems, emails, spreadsheets, and shared folders. This can make it difficult to track what documents were submitted, what status they are in, and where the actual files are stored.

The idea behind this project is simple:

> Create a platform where supplier document metadata can be tracked in a database, while the actual uploaded files are stored securely in AWS S3.

This gave me the opportunity to practice backend development, database integration, frontend API usage, Docker containerization, AWS deployment, cloud storage, IAM permissions, and infrastructure documentation.

---

## What This Project Does

The Supplier Document Platform currently allows users to:

* Create supplier document records
* View all saved document records
* Retrieve a document by ID
* Store document metadata in PostgreSQL
* Upload document files to AWS S3
* Run the backend and database using Docker Compose
* Deploy the application to AWS EC2
* Use an EC2 IAM Role for secure S3 access
* Document infrastructure using Terraform

---

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* AWS SDK for Java
* Maven

### Frontend

* React
* Vite
* Axios
* JavaScript

### Cloud / DevOps

* Docker
* Docker Compose
* AWS EC2
* AWS S3
* AWS IAM Role
* AWS IAM Policy
* Terraform
* GitHub

### Testing / Development Tools

* Postman
* DBeaver
* IntelliJ IDEA
* Git Bash
* Docker Desktop

---

## Project Architecture

```text
React Frontend
      |
      v
Spring Boot REST API
      |
      |---------------------> PostgreSQL
      |                         Stores document metadata
      |
      |---------------------> AWS S3
                                Stores uploaded document files

Docker Compose runs the backend and database.
AWS EC2 hosts the deployed application.
IAM Role allows the backend to access S3 without hardcoded AWS keys.
```

---

## Folder Structure

```text
supplier-document-platform/
├── backend/
│   ├── src/main/java/com/moharayed/supplier_document_platform/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   └── config/
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend/
│   └── React application
│
├── infra/
│   ├── docker-compose.yml
│   └── terraform/
│       ├── main.tf
│       ├── variables.tf
│       └── outputs.tf
│
└── README.md
```

---

## API Endpoints

| Method | Endpoint          | Description                  |
| ------ | ----------------- | ---------------------------- |
| GET    | `/documents`      | Get all document records     |
| POST   | `/documents`      | Create a new document record |
| GET    | `/documents/{id}` | Get a document by ID         |
| POST   | `/files/upload`   | Upload a file to AWS S3      |

---

## Example Document JSON

```json
{
  "filename": "supplier-quality-report.pdf"
}
```

The backend automatically assigns:

```json
{
  "status": "PENDING",
  "uploadDate": "current timestamp"
}
```

---

## Running the Project Locally with Docker

Make sure Docker Desktop is running first.

From the project root:

```bash
docker compose -f infra/docker-compose.yml up --build
```

Or from inside the `infra` folder:

```bash
docker compose up --build
```

The backend will run on:

```text
http://localhost:8080
```

Test the API:

```http
GET http://localhost:8080/documents
```

To stop the containers:

```bash
docker compose down
```

To stop containers and delete the local PostgreSQL Docker volume:

```bash
docker compose down -v
```

Only use `-v` if you are okay deleting the local database data.

---

## Environment Variables

This project uses a `.env` file inside the `infra/` folder.

Example:

```env
POSTGRES_USER=admin
POSTGRES_PASSWORD=your_password_here
POSTGRES_DB=supplierdb

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/supplierdb
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=your_password_here

AWS_REGION=us-east-1
AWS_S3_BUCKET=supplier-document-platform-rayed
```

The `.env` file is intentionally ignored by Git and should not be committed.

---

## AWS Deployment

The application was deployed to an AWS EC2 Ubuntu instance using Docker Compose.

Deployment flow:

```text
GitHub Repository
      |
      v
AWS EC2 Instance
      |
      v
Docker Compose
      |
      |---- Spring Boot Backend Container
      |
      |---- PostgreSQL Container
```

On the EC2 instance:

```bash
git clone https://github.com/MohammedRayed/supplier-document-platform.git
cd supplier-document-platform/infra
nano .env
docker compose up --build -d
```

Test from inside EC2:

```bash
curl http://localhost:8080/documents
```

Test from a browser or Postman:

```text
http://EC2_PUBLIC_IP:8080/documents
```

---

## AWS S3 Integration

The project supports file uploads to AWS S3.

Files are uploaded through this endpoint:

```http
POST /files/upload
```

In Postman:

```text
Body → form-data
Key: file
Type: File
Value: choose a file
```

Example response:

```json
{
  "message": "File uploaded successfully",
  "s3Key": "uuid-supplier-document.pdf"
}
```

The actual file is stored in the private S3 bucket, while the application can continue managing metadata through PostgreSQL.

---

## IAM Role Security

Instead of storing AWS access keys inside the app, the EC2 instance uses an IAM Role.

This is a better security practice because:

* No AWS access keys are stored in source code
* No AWS secret keys are stored in Docker Compose
* The EC2 instance receives temporary credentials automatically
* The IAM policy follows least-privilege access
* The backend only has access to the project S3 bucket

The IAM policy allows the backend to:

* Upload files to the project S3 bucket
* Read files from the bucket
* Delete files from the bucket
* List objects in the bucket

This makes the cloud setup more realistic and closer to how production systems are normally secured.

---

## Terraform

Terraform files are included under:

```text
infra/terraform/
```

The Terraform configuration documents AWS infrastructure such as:

* S3 bucket
* IAM role
* IAM policy
* IAM instance profile

Terraform was added to show how this project can move toward Infrastructure as Code instead of relying only on manual AWS Console setup.

Important Terraform files:

```text
main.tf
variables.tf
outputs.tf
```

Terraform state files and local variable files are ignored through `.gitignore`.

---

## What I Learned

This project helped me practice the full flow of building and deploying a backend/cloud application.

Some of the main things I learned:

* How to structure a Spring Boot project using Controller, Service, Repository, and Model layers
* How to connect Spring Boot to PostgreSQL using Spring Data JPA
* How to test REST APIs using Postman
* How to connect a React frontend to a backend API
* How to containerize a Spring Boot application with Docker
* How to run PostgreSQL and Spring Boot together using Docker Compose
* How to deploy a Dockerized application to AWS EC2
* How to troubleshoot real deployment issues on a Linux server
* How to store files in AWS S3
* How to use IAM Roles instead of hardcoded AWS credentials
* How Terraform can be used to document and manage cloud infrastructure

---

## Challenges I Ran Into

This project included a lot of real-world debugging, including:

* Fixing Spring Boot package structure issues
* Resolving PostgreSQL password authentication problems
* Debugging Docker port conflicts
* Fixing Docker build context issues
* Updating the Dockerfile so the JAR builds inside Docker
* Handling GitHub authentication and commit history cleanup
* Moving secrets out of the repository
* Setting up IAM permissions correctly for S3 access
* Understanding the difference between AWS Region and Availability Zone

These issues made the project more valuable because they are the same types of problems that happen in real development and deployment environments.

---

## Future Improvements

Planned future improvements include:

* Add user login and authentication
* Protect document routes with Spring Security
* Store S3 object keys in PostgreSQL document records
* Add file download support
* Add document status updates through the frontend
* Add search and filtering
* Improve frontend styling
* Add Nginx reverse proxy
* Serve the app through port 80 instead of 8080
* Add HTTPS with a domain name
* Expand Terraform to fully provision EC2, security groups, IAM, and S3

---

## Resume Summary

This project demonstrates backend development, cloud deployment, containerization, and secure AWS integration.

Resume bullets:

* Built a full-stack supplier document management platform using Spring Boot, React, PostgreSQL, and REST APIs to manage supplier document metadata.
* Containerized the Spring Boot backend and PostgreSQL database using Docker Compose for consistent local and cloud deployment.
* Deployed the Dockerized application to AWS EC2 and exposed REST API endpoints for cloud-based access.
* Integrated AWS S3 document uploads using an EC2 IAM Role and AWS SDK for Java, avoiding hardcoded cloud credentials.
* Created Terraform infrastructure configuration for AWS S3, IAM Role, IAM Policy, and EC2 instance profile.

---

## Author

Mohammed Rayed
Computer Science Graduate
Backend / Cloud / DevOps-focused Software Engineer
