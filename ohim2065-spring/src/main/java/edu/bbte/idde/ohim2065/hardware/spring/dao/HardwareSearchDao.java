package edu.bbte.idde.ohim2065.hardware.spring.dao;

import edu.bbte.idde.ohim2065.hardware.spring.model.HardwareSearch;

import java.time.Instant;
import java.util.Collection;

public interface HardwareSearchDao extends Dao<HardwareSearch> {
    Collection<HardwareSearch> findHardwareSearchByDatumIsBetween(Instant kezd, Instant vege);
}
