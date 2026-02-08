CREATE TABLE TB_USERS(
    user_id UUID PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL ,
    role VARCHAR(255) NOT NULL,
    is_verified BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    verification_code VARCHAR(6),
    verification_token_expires_at TIMESTAMP
)