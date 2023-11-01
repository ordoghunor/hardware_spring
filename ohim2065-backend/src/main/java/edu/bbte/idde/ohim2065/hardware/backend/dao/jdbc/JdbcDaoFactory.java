package edu.bbte.idde.ohim2065.hardware.backend.dao.jdbc;

import edu.bbte.idde.ohim2065.hardware.backend.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.backend.dao.DaoFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDaoFactory implements DaoFactory {
    private HardwareDao hardwareDao;
    private BrandDao brandDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDaoFactory.class);

    @Override
    public HardwareDao getHardwareDao() {
        if (hardwareDao == null) {
            hardwareDao = new HardwareJdbcDao();
            LOGGER.info("HardwareJdbcDao Created");
        }
        return hardwareDao;
    }

    @Override
    public BrandDao getBrandDao() {
        if (brandDao == null) {
            brandDao = new BrandJdbcDao();
            LOGGER.info("brandDao Created");
        }
        return brandDao;
    }
}

