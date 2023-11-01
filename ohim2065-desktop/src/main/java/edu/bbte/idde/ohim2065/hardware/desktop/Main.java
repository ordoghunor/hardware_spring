package edu.bbte.idde.ohim2065.hardware.desktop;

import edu.bbte.idde.ohim2065.hardware.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {
        final HardwareDao hardwareDao = AbstractDaoFactory.getDaoFactory().getHardwareDao();

        Hardware newHardware = new Hardware("Rtx 3060", 999.99, "Nvidia", "Green", 1);
        hardwareDao.create(newHardware);

        LOGGER.info("First hardware: {}",hardwareDao.findById(newHardware.getId()));

        newHardware.setPrice(1565.99);
        hardwareDao.update(newHardware);

        hardwareDao.delete(newHardware);

        LOGGER.info("First hardware: {}",hardwareDao.findById(2L));

        Hardware secondHardware = new Hardware("Rtx 3070 ti", 1199.99, "Nvidia", "Black", 1);
        hardwareDao.create(secondHardware);

        secondHardware.setPrice(1319.99);
        hardwareDao.update(secondHardware);

        Hardware thirdHardware = new Hardware("Radeon Rx 6900", 5299.99, "AMD", "Black", 1);
        hardwareDao.create(thirdHardware);

        LOGGER.info("All hardware: {}", hardwareDao.findAll());

        hardwareDao.delete(thirdHardware);

        LOGGER.info("All hardware: {}", hardwareDao.findAll());
    }
}
