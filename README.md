# 📚 Library Management Console App (with Docker)

## 📝 Project Overview
This is a Java console-based Library Management System containerized using Docker.  
The application allows users to manage books, users, and borrow operations.  
The project uses a MySQL database, and Adminer is provided for database administration.

---

## ⚙️ Prerequisites

Make sure the following are installed on your machine:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## 🚀 Setup Instructions

To build and run the application with the required containers:

```bash
docker-compose up --build
```
This command will:

Build the Java application Docker image.

Start containers for the app, MySQL, and Adminer

---

## 🌐 Access Instructions
---

✅ Application Usage
The Java console application will run and log output to your terminal once the container starts.
It connects to the MySQL database inside the Docker network.

🛠 Adminer Interface
To view or interact with the database:

Open your browser and go to: [http://localhost:8081](http://localhost:8081/)

Use these credentials:

System: MySQL/MariaDB

Server: db 

Username: root

Password: reema213

Database: library

---

🧩 Troubleshooting Tip
If you get a database connection error like:


```
Communications link failure
```
Make sure:

The database container is fully up before the Java app tries to connect.

You can restart the setup using:


```bash

docker-compose down
docker-compose up --build
```
Or you can add a wait script inside the app container to delay the startup until the DB is ready
