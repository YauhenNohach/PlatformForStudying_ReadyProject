INSERT INTO groups (id, name)
VALUES (2, 'Group A'), (3, 'Group B'), (4, 'Group C');
INSERT INTO tests (id, access, duration, end_time, start_time, created_by, test_name)
VALUES (1, TRUE, 3600, '2023-01-01 00:00:00', '2023-01-01 00:00:00', 'User1', 'Test 1'),
       (2, FALSE, 1800, '2023-01-02 00:00:00', '2023-01-02 00:00:00', 'User2', 'Test 2');
INSERT INTO assignments (id, rating, status, test_id)
VALUES (1, 80, 'Completed', 1),
       (2, 90, 'In Progress', 2);

INSERT INTO users (id, email, enabled, firstname, lastname, password, type, role, username)
VALUES (1, 'user1@example.com', TRUE, 'John', 'Doe', 'password1', 'TEACHER', 'ADMIN', 'john_doe'),
       (2, 'user2@example.com', TRUE, 'Jane', 'Smith', 'password2', 'STUDENT', 'USER', 'jane_smith')
    RETURNING id;
INSERT INTO teachers (user_id)
VALUES (1);
INSERT INTO teacher_tests (teacher_id, test_id)
VALUES (1, 1);
INSERT INTO students (user_id)
VALUES (2);
INSERT INTO students_tests (student_id, test_id)
VALUES (2, 2);
INSERT INTO teacher_groups (teacher_id, group_id)
VALUES (1, 1);
INSERT INTO student_assignments (student_id, assignment_id)
VALUES (2, 2);
INSERT INTO questions (right_answer, text, type)
VALUES ('A', 'Question 1', 'Multiple Choice'), ('B', 'Question 2', 'True/False');
INSERT INTO profile (user_id)
VALUES (1), (2);
INSERT INTO possible_answers (option_question_id, possible_answers)
VALUES (1, 'A,B,C,D'), (2, 'True,False');
INSERT INTO groups_students (group_id, student_id)
VALUES (1, 1), (2, 2);
INSERT INTO confirmation_token_entity (token, created_at, expires_at, user_id)
VALUES ('token1', '2023-01-01 00:00:00', '2023-01-02 00:00:00', 1), ('token2', '2023-01-02 00:00:00', '2023-01-03 00:00:00', 2);
INSERT INTO assignments_answers (answer_id, user_answers)
VALUES (1, 'A,C'), (2, 'True');
INSERT INTO tests_questions (test_id, question_id)
VALUES (1, 1), (2, 2);