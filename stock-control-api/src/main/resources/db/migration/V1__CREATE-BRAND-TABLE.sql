CREATE TABLE TB_BRANDS(
    brand_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(55) not null,
    country VARCHAR(55),
    year_founded INTEGER,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
)