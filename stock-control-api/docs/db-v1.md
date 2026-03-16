# Stock Control API

This service handles watch and brand inventory management within the microservices architecture.

---

## Database Schema

### Table: `tb_brands`

Stores watch brand information.

| Column | Type | Description | Notes |
|:---|:---|:---|:---|
| `brand_id` | `uuid` | Unique identifier for each brand | **Primary Key** |
| `name` | `string` | Brand name | Not null |
| `country` | `string` | Country of origin | Not null |
| `year_founded` | `int` | Year the brand was founded | |
| `created_at` | `timestamp` | Timestamp when the record was created | Default: now |
| `updated_at` | `timestamp` | Timestamp when the record was last updated | Default: now |

---

### Table: `tb_watches`

Stores watch inventory and technical specifications.

| Column | Type | Description | Notes |
|:---|:---|:---|:---|
| `watch_id` | `uuid` | Unique identifier for each watch | **Primary Key** |
| `brand_id` | `uuid` | Reference to the watch brand | **Foreign Key → tb_brands** |
| `model` | `string` | Watch model name | |
| `reference` | `string` | Watch reference code | Not null |
| `moviment_type` | `enum` | Movement type (`MovimentType`) | Not null |
| `box_material` | `enum` | Case/box material (`BoxMaterial`) | Not null |
| `glass_type` | `enum` | Crystal/glass type (`GlassType`) | Not null |
| `water_resistance_m` | `int` | Water resistance in meters | |
| `water_resistance_label` | `string` | Human-readable water resistance label | |
| `diameter_mm` | `float` | Case diameter in millimeters | |
| `lug_to_lug_mm` | `float` | Lug-to-lug distance in millimeters | |
| `thickness_mm` | `float` | Case thickness in millimeters | |
| `lug_width_mm` | `float` | Lug width in millimeters | |
| `price_in_cents` | `long` | Price stored in cents | |
| `collector_score` | `long` | Collector desirability score | |
| `image_url` | `string` | URL of the watch image | |
| `created_at` | `timestamp` | Timestamp when the record was created | Default: now |
| `updated_at` | `timestamp` | Timestamp when the record was last updated | Default: now |

---

## Relationships

```
tb_brands 1 ——— N tb_watches
```

> Each watch belongs to one brand (`@ManyToOne`).  
> Fetch type: `EAGER` — brand data is loaded together with the watch.

---

## Enums

| Enum | Used in |
|:---|:---|
| `MovimentType` | `tb_watches.moviment_type` |
| `BoxMaterial` | `tb_watches.box_material` |
| `GlassType` | `tb_watches.glass_type` |

---
