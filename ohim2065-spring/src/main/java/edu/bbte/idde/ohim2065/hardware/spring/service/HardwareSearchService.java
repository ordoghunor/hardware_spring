package edu.bbte.idde.ohim2065.hardware.spring.service;

import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareSearchDao;
import edu.bbte.idde.ohim2065.hardware.spring.model.HardwareSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HardwareSearchService {
    @Autowired
    private HardwareSearchDao hardwareSearchDao;

    public void insertQueryAndTime(Double minprice, Double maxprice) {
        String query = "SELECT * FROM hardware WHERE price > "
                + minprice.toString() + " and price < " + maxprice.toString();
        HardwareSearch hardwareSearch = new HardwareSearch(query, Instant.now());
        hardwareSearchDao.saveAndFlush(hardwareSearch);
    }

}
