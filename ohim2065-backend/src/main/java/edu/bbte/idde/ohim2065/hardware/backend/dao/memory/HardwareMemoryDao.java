package edu.bbte.idde.ohim2065.hardware.backend.dao.memory;

import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class HardwareMemoryDao implements HardwareDao {
    private static final Map<Long, Hardware> ENTITIES = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareMemoryDao.class);

    @Override
    public Hardware create(Hardware hardware) {
        Long id = ID_GENERATOR.getAndIncrement();
        hardware.setId(id);
        hardware.setVerzioszam(1);
        ENTITIES.put(id, hardware);
        return hardware;
    }

    @Override
    public Collection<Hardware> findAll() {
        return ENTITIES.values();
    }

    @Override
    public Hardware findById(Long id) {
        return ENTITIES.get(id);
    }

    @Override
    public Boolean update(Hardware hardware) {
        LOGGER.info("Hardware with id {} has been updated",hardware.getId());
        Hardware oldHardware = findById(hardware.getId());
        hardware.setVerzioszam(hardware.getVerzioszam() + 1);
        return ENTITIES.replace(hardware.getId(), oldHardware, hardware);
    }

    @Override
    public void delete(Hardware hardware) {
        LOGGER.info("Hardware with id {} has been deleted",hardware.getId());
        ENTITIES.remove(hardware.getId());
    }

    @Override
    public Hardware findByHardware(Hardware entity) {
        return ENTITIES.get(entity.getId());
    }
}
