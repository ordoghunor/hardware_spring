package edu.bbte.idde.ohim2065.hardware.spring.dao.mem;

import edu.bbte.idde.ohim2065.hardware.spring.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public class BrandMemoryDao implements BrandDao {
    private static final Map<Long, Brand> ENTITIES = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandMemoryDao.class);

    @Override
    public Brand saveAndFlush(Brand entity) {
        if (entity.getId() == null) {
            Long id = ID_GENERATOR.getAndIncrement();
            entity.setId(id);
            ENTITIES.put(id, entity);
            return entity;
        } else {
            return update(entity);
        }
    }

    @Override
    public Collection<Brand> findAll() {
        return ENTITIES.values();
    }

    @Override
    public Brand getById(Long id) {
        return ENTITIES.get(id);
    }

    public Brand update(Brand entity) {
        LOGGER.info("Brand with id {} has been updated",entity.getId());
        Brand oldBrand = getById(entity.getId());
        ENTITIES.replace(entity.getId(), oldBrand, entity);
        return entity;
    }

    @Override
    public void delete(Brand entity) {
        LOGGER.info("Hardware with id {} has been deleted",entity.getId());
        ENTITIES.remove(entity.getId());
    }

    @Override
    public Brand findByName(String name) {
        for (Brand brand : ENTITIES.values()) {
            if (Objects.equals(brand.getName(), name)) {
                return brand;
            }
        }
        return null;
    }
}
