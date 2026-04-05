# 📘 Smart Invigilate - Online Exam Proctoring System

Smart Invigilate is a scalable and secure **online examination and proctoring system** built using **Spring Boot** and **PostgreSQL**. It enables institutions to conduct exams, monitor students in real-time, and detect suspicious activities.

---

## 🚀 Features

### 🔐 Authentication & Security

* JWT-based authentication
* Role-based access (Admin, Student)
* Refresh token support
* Password encryption (BCrypt)
* Forgot password & OTP verification

---

### 👨‍💼 Admin Module

* Manage students (CRUD + bulk upload)
* Create and manage exams
* Add and manage questions (MCQ)
* Activate/deactivate exams
* Monitor ongoing exams
* View results and analytics
* Detect and flag cheating

---

### 🎓 Student Module

* View active and upcoming exams
* Start and submit exams
* Auto-save answers
* View results and exam history

---

### 🧠 Proctoring System

* Tab switch detection
* Window focus tracking
* Activity logs per student
* Cheating flag system

---

### 📊 Dashboard & Analytics

* Total students, exams, submissions
* Performance reports
* Cheating statistics

---

### 📩 Notifications

* Email/SMS simulation
* Exam alerts & reminders

---

### 📂 File Upload

* Profile image upload
* Exam material upload

---

## 🏗️ Tech Stack

* **Backend:** Spring Boot (Java)
* **Database:** PostgreSQL
* **Security:** Spring Security + JWT
* **ORM:** Hibernate (JPA)
* **Documentation:** Swagger / OpenAPI
* **Build Tool:** Maven

---

## 📁 Project Structure

```
com.smartinvigilate
│
├── config          # Security & App Config
├── controller      # REST Controllers
├── dto             # Request/Response DTOs
├── entity          # JPA Entities
├── repository      # Database Repositories
├── service         # Business Logic
│   ├── impl        # Service Implementations
├── security        # JWT & Auth Logic
├── exception       # Global Exception Handling
└── util            # Utility Classes
```

---

## ⚙️ API Endpoints

### 🔐 Authentication

* POST `/auth/login`
* POST `/auth/register`
* POST `/auth/logout`
* POST `/auth/refresh-token`
* POST `/auth/forgot-password`
* POST `/auth/verify-otp`

---

### 👨‍💼 Admin APIs

#### Student Management

* POST `/admin/students`
* POST `/admin/students/bulk`
* GET `/admin/students`
* GET `/admin/students/{id}`
* PUT `/admin/students/{id}`
* PATCH `/admin/students/{id}/status`
* DELETE `/admin/students/{id}`
* GET `/admin/students/search`

#### Exam Management

* POST `/admin/exams`
* GET `/admin/exams`
* GET `/admin/exams/{id}`
* PUT `/admin/exams/{id}`
* DELETE `/admin/exams/{id}`
* PATCH `/admin/exams/{id}/activate`
* PATCH `/admin/exams/{id}/deactivate`

#### Question Management

* POST `/admin/exams/{examId}/questions`
* GET `/admin/exams/{examId}/questions`
* PUT `/admin/questions/{id}`
* DELETE `/admin/questions/{id}`

#### Monitoring & Results

* GET `/admin/exams/{id}/monitor`
* GET `/admin/exams/{id}/results`
* GET `/admin/exams/{id}/logs`
* GET `/admin/exams/{id}/cheating`

#### Cheating Control

* PATCH `/admin/submissions/{id}/cheating`
* PATCH `/admin/submissions/{id}/flag`

#### Dashboard

* GET `/admin/dashboard`
* GET `/admin/analytics/exams`
* GET `/admin/analytics/students`

---

### 🎓 Student APIs

#### Exam Flow

* GET `/student/exams`
* GET `/student/exams/active`
* GET `/student/exams/{id}`
* POST `/student/exams/{id}/start`
* POST `/student/exams/{id}/submit`
* GET `/student/exams/{id}/result`
* GET `/student/exams/history`

#### Answer Handling

* POST `/student/exams/{id}/answers`
* GET `/student/exams/{id}/answers`
* PATCH `/student/exams/{id}/answers`

#### Proctoring Logs

* POST `/student/exams/{id}/logs`
* GET `/student/exams/{id}/logs`

#### Webcam

* POST `/student/webcam/start`
* POST `/student/webcam/stop`

---

## 🗄️ Database Tables

* users
* roles
* students
* exams
* questions
* submissions
* answers
* proctor_logs
* notifications
* cheating_flags
* departments
* colleges

---

## ⚙️ Configuration (application.yml)


spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/smart_invigilate
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8082
```

---

## ▶️ Running the Project

1. Clone the repository
2. Configure PostgreSQL database
3. Update `application.yml`
4. Run the application:

```
mvn spring-boot:run
```

---

## 📌 Future Enhancements

* AI-based face detection
* Eye tracking
* Voice detection
* Live WebSocket monitoring
* Mobile app integration
