package edu.bbte.idde.ohim2065.hardware.backend.dao;

public interface DaoFactory {
    HardwareDao getHardwareDao() throws ClassNotFoundException;

    BrandDao getBrandDao();
}

