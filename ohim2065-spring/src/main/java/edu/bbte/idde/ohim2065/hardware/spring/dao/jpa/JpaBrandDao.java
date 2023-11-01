package edu.bbte.idde.ohim2065.hardware.spring.dao.jpa;

import edu.bbte.idde.ohim2065.hardware.spring.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Profile("jpa")
public interface JpaBrandDao extends BrandDao, JpaRepository<Brand, Long> {
    @Query("UPDATE Brand SET name=:name WHERE id=:id")
    @Transactional
    @Modifying
    void updateName(@Param("id") Long id, @Param("name") String name);
}
