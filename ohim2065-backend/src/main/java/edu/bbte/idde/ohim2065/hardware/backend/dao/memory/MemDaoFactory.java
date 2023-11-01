package edu.bbte.idde.ohim2065.hardware.backend.dao.memory;

import edu.bbte.idde.ohim2065.hardware.backend.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.backend.dao.DaoFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;

public class MemDaoFactory implements DaoFactory {
    private HardwareDao hardwareDao;
    private BrandDao brandDao;

    @Override
    public HardwareDao getHardwareDao() {
        if (hardwareDao == null) {
            hardwareDao = new HardwareMemoryDao();
        }
        return hardwareDao;
    }

    @Override
    public BrandDao getBrandDao() {
        if (brandDao == null) {
            brandDao = new BrandMemoryDao();
        }
        return brandDao;
    }
}
