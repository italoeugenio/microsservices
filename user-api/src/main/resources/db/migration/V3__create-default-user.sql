INSERT INTO TB_USERS (
    user_id,
    full_name,
    email,
    password_hash,
    role,
    is_verified,
    created_at,
    updated_at,
    verification_code,
    verification_token_expires_at
)
VALUES (
           '550e8400-e29b-41d4-a716-446655440000',
           'Administrador',
           'admin@application.com',
           '$2a$10$idpbHJlAiyQtsnftJX/LeOBOAn1zlrF25TalEEMtPD0YAv70K4eFi',
           'ADMIN',
           TRUE,
           '2026-02-17 12:00:00',
           '2026-02-17 12:00:00',
           NULL,
           NULL
);