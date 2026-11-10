-- Optional: run this AFTER the app has started once (so Hibernate creates the tables).
-- Sample data for quick testing.

USE scms_db;

INSERT INTO students (name, email, phone, date_of_birth, gender, address) VALUES
('Aarav Sharma', 'aarav.sharma@example.com', '+91 98765 43210', '2003-05-14', 'Male', 'Vijayawada, AP'),
('Diya Patel', 'diya.patel@example.com', '+91 91234 56789', '2004-02-21', 'Female', 'Hyderabad, TS'),
('Rohan Mehta', 'rohan.mehta@example.com', '+91 99887 66554', '2002-11-09', 'Male', 'Bengaluru, KA');

INSERT INTO courses (code, title, credits, description, instructor) VALUES
('CS101', 'Introduction to Programming', 4, 'Fundamentals of programming using Java.', 'Dr. Rao'),
('CS201', 'Data Structures', 4, 'Arrays, linked lists, trees, graphs and more.', 'Dr. Iyer'),
('MA101', 'Calculus I', 3, 'Limits, derivatives, and integrals.', 'Prof. Nair');

INSERT INTO enrollments (student_id, course_id, enrollment_date, grade, status) VALUES
(1, 1, '2026-01-10', 'A', 'ACTIVE'),
(1, 2, '2026-01-10', NULL, 'ACTIVE'),
(2, 1, '2026-01-12', 'B+', 'ACTIVE'),
(3, 3, '2026-01-15', NULL, 'ACTIVE');
