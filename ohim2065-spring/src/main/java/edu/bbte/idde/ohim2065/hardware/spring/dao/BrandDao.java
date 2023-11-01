package edu.bbte.idde.ohim2065.hardware.spring.dao;

import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;

public interface BrandDao extends Dao<Brand> {
    Brand findByName(String name);
}
