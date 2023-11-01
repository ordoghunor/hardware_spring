package edu.bbte.idde.ohim2065.hardware.backend.dao;

import edu.bbte.idde.ohim2065.hardware.backend.model.Hardware;

public interface HardwareDao extends Dao<Hardware> {
    Hardware findByHardware(Hardware entity);
}

