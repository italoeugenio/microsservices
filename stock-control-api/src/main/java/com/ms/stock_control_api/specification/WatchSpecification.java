package com.ms.stock_control_api.specification;

import com.ms.stock_control_api.entity.entities.WatchModel;
import com.ms.stock_control_api.entity.enums.BoxMaterial;
import com.ms.stock_control_api.entity.enums.GlassType;
import com.ms.stock_control_api.entity.enums.MovimentType;
import org.springframework.data.jpa.domain.Specification;

public class WatchSpecification {

    public static Specification<WatchModel> brand(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.equal(cb.lower(root.join("brand").get("name")), name.toLowerCase());
        };
    }

    public static Specification<WatchModel> movimentType(String movimentType){
        return(root, query,  cb) -> {
            if(movimentType == null) return null;
            return cb.equal(root.get("movimentType"), MovimentType.fromApi(movimentType));
        };
    }

    public static Specification<WatchModel> boxMaterial(String boxMaterial){
        return(root, query, cb) -> {
            if(boxMaterial == null) return null;
            return cb.equal(root.get("boxMaterial"), BoxMaterial.fromAPi(boxMaterial));
        };
    }

    public static Specification<WatchModel> glassType(String glassType){
        return (root, query, cb) ->{
            if(glassType == null) return null;
            return cb.equal(root.get("glassType"), GlassType.fromApi(glassType));
        };
    }

    public static Specification<WatchModel> minWaterResistanceM(Integer minResistance){
        return (root, query, cb) -> {
          if(minResistance == null) return null;
          return cb.greaterThanOrEqualTo(root.get("waterResistanceM"), minResistance);
        };
    }

    public static Specification<WatchModel> maxWaterResistanceM(Integer maxResistance){
        return (root, query, cb) -> {
            if(maxResistance == null) return null;
            return cb.lessThanOrEqualTo(root.get("waterResistanceM"), maxResistance);
        };
    }

    public static Specification<WatchModel> minPrice(Long price){
        return (root, query, cb) -> {
            if(price == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceInCents"), price);
        };
    }

    public static Specification<WatchModel> maxPrice(Long price){
        return (root, query, cb) -> {
            if(price == null) return null;
            return cb.lessThanOrEqualTo(root.get("priceInCents"), price);
        };
    }

    public static Specification<WatchModel> minDiameter(Float price){
        return (root, query, cb) -> {
            if(price == null) return null;
            return cb.greaterThanOrEqualTo(root.get("diameterMm"), price);
        };
    }

    public static Specification<WatchModel> maxDiameter(Float price){
        return (root, query, cb) -> {
            if(price == null) return null;
            return cb.lessThanOrEqualTo(root.get("diameterMm"), price);
        };
    }


}
