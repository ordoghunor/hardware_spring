package edu.bbte.idde.ohim2065.hardware.spring.dao.jpa;

import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareSearchDao;
import edu.bbte.idde.ohim2065.hardware.spring.model.HardwareSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHardwareSearchDao extends HardwareSearchDao, JpaRepository<HardwareSearch, Long> {
}
