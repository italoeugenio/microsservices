package com.ms.stock_control_api.specification;

import com.ms.stock_control_api.entity.entities.WatchModel;
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
            return cb.equal(root.get("movimentType"), MovimentType.valueOf(movimentType.toUpperCase()));
        };
    }
}
