package edu.bbte.idde.ohim2065.hardware.backend.dao;

import edu.bbte.idde.ohim2065.hardware.backend.model.Brand;

public interface BrandDao extends Dao<Brand> {
    Brand findByBrand(Brand entity);
}
