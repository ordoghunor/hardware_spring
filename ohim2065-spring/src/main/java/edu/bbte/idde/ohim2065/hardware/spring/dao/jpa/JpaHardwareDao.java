package edu.bbte.idde.ohim2065.hardware.spring.dao.jpa;

import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;
import org.hibernate.annotations.SQLInsert;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@Profile("jpa")
public interface JpaHardwareDao extends HardwareDao, JpaRepository<Hardware, Long> {
}
