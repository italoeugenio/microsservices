package com.ms.stock_control_api.entity.entities;

import com.ms.stock_control_api.entity.enums.BoxMaterial;
import com.ms.stock_control_api.entity.enums.GlassType;
import com.ms.stock_control_api.entity.enums.MovimentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "watch")
@Table(name = "tb_watchs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class WatchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandModel brand;

    @Column(name = "model")
    private String model;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "moviment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovimentType movimentType;

    @Column(name = "box_material", nullable = false)
    @Enumerated(EnumType.STRING)
    private BoxMaterial boxMaterial;

    @Column(name = "glass_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GlassType glassType;

    @Column(name = "water_resistance_m")
    private int waterResistanceM;

    @Column(name = "diameter_mm")
    private float diameterMm;

    @Column(name = "lug_to_lug_mm")
    private float lugTolugMm;

    @Column(name = "thickness_mm")
    private float thicknessMm;

    @Column(name = "lug_width_mm")
    private float lugWidthMm;

    @Column(name = "price_in_cents")
    private long priceInCents;

    @Column(name = "image_url")
    private String image;

    @Column(name = "water_resistance_label")
    private String waterResistanceLabel;

    @Column(name = "collector_score")
    private long collectorScore;
}
