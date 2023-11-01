package edu.bbte.idde.ohim2065.hardware.spring.dao.mem;

import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public class HardwareMemoryDao implements HardwareDao {
    private static final Map<Long, Hardware> ENTITIES = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareMemoryDao.class);

    @Override
    public Hardware saveAndFlush(Hardware hardware) {
        if (hardware.getId() == null) {
            Long id = ID_GENERATOR.getAndIncrement();
            hardware.setId(id);
            ENTITIES.put(id, hardware);
            return hardware;
        } else {
            return update(hardware);
        }
    }

    @Override
    public Collection<Hardware> findAll() {
        return ENTITIES.values();
    }

    @Override
    public Hardware getById(Long id) {
        return ENTITIES.get(id);
    }

    public Hardware update(Hardware hardware) {
        LOGGER.info("Hardware with id {} has been updated",hardware.getId());
        Hardware oldHardware = getById(hardware.getId());
        ENTITIES.replace(hardware.getId(), oldHardware, hardware);
        return hardware;
    }

    @Override
    public void delete(Hardware hardware) {
        LOGGER.info("Hardware with id {} has been deleted",hardware.getId());
        ENTITIES.remove(hardware.getId());
    }

    @Override
    public Collection<Hardware> findHardwareByPriceIsBetween(Double minprice, Double maxprice) {
        Collection<Hardware> collection = new ArrayList<>();
        for (Hardware hardware : ENTITIES.values()) {
            Double price = hardware.getPrice();
            if (price >= minprice && price <= maxprice) {
                collection.add(hardware);
            }
        }
        return collection;
    }
}
