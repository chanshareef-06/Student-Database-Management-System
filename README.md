# Student Database & Course Management System (SCMS)

A full-stack CRUD web application built with **Spring Boot**, **Thymeleaf**, **Spring Data JPA**, and **MySQL** for managing students, courses, and course enrollments.

**Repository:** [chanshareef-06/Student-Database-Management-System](https://github.com/chanshareef-06/Student-Database-Management-System)

## Features

- **Dashboard Overview**: Displays live counts of students, courses, and enrollments with quick-action buttons.
- **Student Management**: Create, list, search by name, view profile & enrollment history, edit, and delete students.
- **Course Management**: Create, list, search by title, view details & enrolled students, edit, and delete courses.
- **Enrollment Management**: Enroll students into courses (auto-defaults enrollment date to today), update grades and status (`ACTIVE`, `COMPLETED`, `DROPPED`), and remove enrollments.
- **Validation & Error Handling**: Server-side Jakarta Bean Validation with inline form messages plus a global exception handler (`GlobalExceptionHandler`) and custom error page.
- **Relational Integrity**:
  - Deleting a student or course cascades to its associated enrollments.
  - Prevents duplicate enrollments for the same student and course at both the application and database level.

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Web / MVC | Spring Web + Thymeleaf (with Layout Dialect 3.3.0) |
| Validation | Jakarta Bean Validation (`spring-boot-starter-validation`) |
| Persistence | Spring Data JPA (Hibernate) |
| Database | MySQL 8 (`mysql-connector-j`) |
| Build Tool | Maven |

## Project Structure

```text
student-course-management/
├── .gitignore
├── pom.xml
├── README.md
├── sample-data.sql                              # Optional seed SQL script
└── src/main/
    ├── java/com/scms/
    │   ├── ScmsApplication.java                 # Spring Boot entry point
    │   ├── config/
    │   │   ├── CourseIdToCourseConverter.java   # Form binding converter (String -> Course)
    │   │   └── StudentIdToStudentConverter.java # Form binding converter (String -> Student)
    │   ├── controller/
    │   │   ├── HomeController.java              # Dashboard route (/)
    │   │   ├── StudentController.java           # Student CRUD & search routes
    │   │   ├── CourseController.java            # Course CRUD & search routes
    │   │   ├── EnrollmentController.java        # Enrollment CRUD routes
    │   │   └── GlobalExceptionHandler.java      # Global MVC exception handler
    │   ├── model/
    │   │   ├── Student.java                     # Student entity
    │   │   ├── Course.java                      # Course entity
    │   │   └── Enrollment.java                  # Enrollment entity & EnrollmentStatus enum
    │   ├── repository/
    │   │   ├── StudentRepository.java
    │   │   ├── CourseRepository.java
    │   │   └── EnrollmentRepository.java
    │   └── service/
    │       ├── StudentService.java
    │       ├── CourseService.java
    │       └── EnrollmentService.java
    └── resources/
        ├── application.properties               # Server, MySQL, JPA & Thymeleaf config
        ├── static/css/style.css                 # Application styles
        └── templates/
            ├── fragments.html                   # Shared head, navbar, and footer
            ├── index.html                       # Dashboard view
            ├── error.html                       # Error view
            ├── students/ (list, form, view)
            ├── courses/  (list, form, view)
            └── enrollments/ (list, form)
```

## Database Model

- **Student** `(1)` ── `(many)` **Enrollment** `(many)` ── `(1)` **Course**
- `students` table: `id`, `name`, `email` (unique), `phone`, `date_of_birth`, `gender`, `address`
- `courses` table: `id`, `code` (unique), `title`, `credits`, `description`, `instructor`
- `enrollments` table: `id`, `student_id`, `course_id`, `enrollment_date`, `grade`, `status` (`ACTIVE`, `COMPLETED`, `DROPPED`) with a unique constraint on `(student_id, course_id)`

## Prerequisites

- **JDK 17+**
- **Maven 3.8+**
- **MySQL 8+** running locally (or accessible remotely)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/chanshareef-06/Student-Database-Management-System.git
cd Student-Database-Management-System
```

*(If your local copy is inside a nested `student-course-management/` folder, `cd` into the directory containing `pom.xml`.)*

### 2. Configure the database

The connection URL in `src/main/resources/application.properties` includes `createDatabaseIfNotExist=true`, so MySQL creates `scms_db` automatically on startup if your MySQL user has privileges. You can also create it manually:

```sql
CREATE DATABASE scms_db CHARACTER SET utf8mb4;
```

Update `src/main/resources/application.properties` with your MySQL credentials (and avoid committing personal/production passwords):

```properties
server.port=8000

spring.datasource.url=jdbc:mysql://localhost:3306/scms_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 3. Run the application

```bash
mvn spring-boot:run
```

Once started, open **http://localhost:8000** in your browser.

With `spring.jpa.hibernate.ddl-auto=update`, Hibernate automatically creates and updates the `students`, `courses`, and `enrollments` tables on startup.

### 4. (Optional) Load sample data

After starting the application at least once (so Hibernate creates the tables), import the sample records:

```bash
mysql -u root -p scms_db < sample-data.sql
```

## Application Routes

| Section | Endpoints | Description |
| --- | --- | --- |
| Dashboard | `GET /` | Summary counts & quick actions |
| Students | `GET /students`, `/students/new`, `/students/{id}`, `/students/{id}/edit`, `POST /students/save`, `GET /students/{id}/delete` | Manage students and search by name |
| Courses | `GET /courses`, `/courses/new`, `/courses/{id}`, `/courses/{id}/edit`, `POST /courses/save`, `GET /courses/{id}/delete` | Manage courses and search by title |
| Enrollments | `GET /enrollments`, `/enrollments/new`, `/enrollments/{id}/edit`, `POST /enrollments/save`, `GET /enrollments/{id}/delete` | Enroll students, update grades & statuses |

## Building a Runnable JAR

```bash
mvn clean package
java -jar target/student-course-management-1.0.0.jar
```
