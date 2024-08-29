--liquibase formatted sql

--changeset liquibase-docs:insert-data

INSERT INTO dialog_schema.accounts (first_name, last_name) VALUES
('Alice', 'Johnson'),
('Bob', 'Smith'),
('Charlie', 'Brown'),
('David', 'Williams'),
('Eve', 'Jones'),
('Frank', 'Garcia'),
('Grace', 'Martinez'),
('Hank', 'Davis'),
('Ivy', 'Miller'),
('Jack', 'Wilson');

INSERT INTO dialog_schema.dialogs
(unread_count, conversation_partner_id) VALUES
(0, 1),
(0, 2),
(0, 3),
(0, 4),
(0, 5),
(0, 6),
(0, 7),
(0, 8),
(0, 9),
(0, 10);

INSERT INTO dialog_schema.messages
("time", status, message_text, author_id, recipient_id, dialog_id)
VALUES(1724760300000, 'SENT', 'Bye!', 2, 1, 1),
(1724752800000, 'SENT', 'Hello, how are you?', 1, 2, 1),
(1724752800000, 'SENT', 'Hello, how are you?', 1, 2, 1),
(1724752860000, 'SENT', 'I am fine, thanks!', 2, 1, 1),
(1724752920000, 'READ', 'What are you doing today?', 1, 2, 1),
(1724753100000, 'SENT', 'Just working on some projects.', 2, 1, 1);






