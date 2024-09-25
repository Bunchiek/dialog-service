--liquibase formatted sql

--changeset liquibase-docs:create-table

CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE dialogs (
    id BIGSERIAL PRIMARY KEY,
    unread_count BIGINT NOT NULL,
    participant_one_id UUID NOT NULL,
    participant_two_id UUID NOT NULL,
    FOREIGN KEY (participant_one_id) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (participant_two_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    time BIGINT NOT NULL CHECK (time > 0),
    author_id UUID NOT NULL,
    recipient_id UUID NOT NULL,
    message_text TEXT NOT NULL,
    status VARCHAR(255) NOT NULL,
    dialog_id BIGINT,
    CONSTRAINT fk_message_author FOREIGN KEY (author_id) REFERENCES accounts(id) ON DELETE SET NULL,
    CONSTRAINT fk_message_recipient FOREIGN KEY (recipient_id) REFERENCES accounts(id) ON DELETE SET NULL,
    CONSTRAINT fk_message_dialog FOREIGN KEY (dialog_id) REFERENCES dialogs(id) ON DELETE CASCADE,
    CONSTRAINT chk_status CHECK (status IN ('SENT', 'DELIVERED', 'READ'))

);