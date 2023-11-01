package edu.bbte.idde.ohim2065.hardware.backend.dao.memory;

import edu.bbte.idde.ohim2065.hardware.backend.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BrandMemoryDao implements BrandDao {
    private static final Map<Long, Brand> ENTITIES = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandMemoryDao.class);

    @Override
    public Brand create(Brand entity) {
        Long id = ID_GENERATOR.getAndIncrement();
        entity.setId(id);
        ENTITIES.put(id, entity);
        return entity;
    }

    @Override
    public Collection<Brand> findAll() {
        return ENTITIES.values();
    }

    @Override
    public Brand findById(Long id) {
        return ENTITIES.get(id);
    }

    @Override
    public Boolean update(Brand entity) {
        LOGGER.info("Brand with id {} has been updated",entity.getId());
        Brand oldBrand = findById(entity.getId());
        return ENTITIES.replace(entity.getId(), oldBrand, entity);
    }

    @Override
    public void delete(Brand entity) {
        LOGGER.info("Hardware with id {} has been deleted",entity.getId());
        ENTITIES.remove(entity.getId());
    }

    @Override
    public Brand findByBrand(Brand entity) {
        return ENTITIES.get(entity.getId());
    }
}
