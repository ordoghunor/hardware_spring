package edu.bbte.idde.ohim2065.hardware.spring.dao;

import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;

import java.util.Collection;

public interface HardwareDao extends Dao<Hardware> {
    Collection<Hardware> findHardwareByPriceIsBetween(Double minprice, Double maxprice);
}
