--liquibase formatted sql

--changeset liquibase-docs:create-table

CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    roles VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user_roles_accounts FOREIGN KEY (user_id) REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles)
);

CREATE TABLE dialogs (
    id BIGSERIAL PRIMARY KEY,
    unread_count BIGINT NOT NULL,
    conversation_partner_id UUID NOT NULL,
    CONSTRAINT fk_dialog_conversation_partner FOREIGN KEY (conversation_partner_id) REFERENCES accounts(id) ON DELETE CASCADE
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