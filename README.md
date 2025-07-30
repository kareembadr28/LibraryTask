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
This command will:

Build the Java application Docker image.

Start containers for the app, MySQL, and Adminer
