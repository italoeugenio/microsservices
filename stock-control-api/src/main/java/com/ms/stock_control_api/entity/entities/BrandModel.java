package com.ms.stock_control_api.entity.entities;

import com.ms.stock_control_api.dtos.v1.brand.BrandRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "brand")
@Table(name = "tb_brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BrandModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brand_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "year_founded")
    private int founded;

    public BrandModel(BrandRequestDTO data){
        this.name = data.name();
        this.country = data.country();
        this.founded = data.founded();
    }


}
