INSERT INTO TB_BRANDS(name, country, year_founded)
VALUES
('Rolex', 'Switzerland', 1905),
('Casio', 'Japan', 1946),
('Omega', 'Switzerland', 1848),
('Seiko', 'Japan', 1881),
('TAG Heuer', 'Switzerland', 1860);

INSERT INTO TB_WATCHES (brand_id, model, reference, moviment_type, box_material, glass_type, water_resistance_m, diameter_mm, lug_to_lug_mm, thickness_mm, lug_width_mm, price_in_cents, image_url, water_resistance_label, collector_score)
VALUES
    (
        (SELECT brand_id FROM TB_BRANDS WHERE name = 'Rolex' ),
        'Rolex Submariner', 'SUB-116610LN', 'AUTOMATIC', 'STEEL', 'SAPPHIRE',
        300, 40.0, 48.0, 12.5, 20.0, 950000,
        'https://picsum.photos/seed/relogio_rolex/800/800', 'daily use', 63
    ),
    (
        (SELECT brand_id FROM TB_BRANDS WHERE name = 'Casio'),
        'Casio G-Shock', 'DW-5600E', 'QUARTZ', 'RESIN', 'MINERAL',
        200, 42.0, 44.0, 13.5, 22.0, 15000,
        'https://picsum.photos/seed/relogio_casio/800/800', 'daily use', 33
    ),
    (
        (SELECT brand_id FROM TB_BRANDS WHERE name = 'Omega'),
        'Omega Speedmaster', 'ST-145.022', 'MANUAL', 'STEEL', 'SAPPHIRE',
        50, 42.0, 48.0, 14.0, 20.0, 450000,
        'https://picsum.photos/seed/relogio_omega/800/800', 'daily use', 18
    ),
    (
        (SELECT brand_id FROM TB_BRANDS WHERE name = 'Seiko'),
        'Seiko Prospex', 'SBDX001', 'AUTOMATIC', 'STEEL', 'SAPPHIRE',
        200, 44.0, 50.0, 13.5, 22.0, 120000,
        'https://picsum.photos/seed/relogio_seiko/800/800', 'daily use', 55
    ),
    (
        (SELECT brand_id FROM TB_BRANDS WHERE name = 'TAG Heuer'),
        'TAG Heuer Carrera', 'CBM2110', 'AUTOMATIC', 'STEEL', 'SAPPHIRE',
        100, 41.0, 49.0, 12.5, 21.0, 350000,
        'https://picsum.photos/seed/relogio_tag/800/800', 'suitable for swimming', 53
    );