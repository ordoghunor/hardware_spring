package edu.bbte.idde.ohim2065.hardware.spring.controller;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.HardwareSearchDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.mapper.HardwareSearchMapper;
import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareSearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collection;

@RestController
@RequestMapping("/hardwaresearches")
public class HardwareSearchController {
    @Autowired
    private HardwareSearchDao hardwareSearchDao;

    @Autowired
    private HardwareSearchMapper hardwareSearchMapper;

    @GetMapping("/{kezd}/{vege}")
    public Collection<HardwareSearchDto> getHarwareSearches(@RequestParam("kezd") Instant kezd,
                                                            @RequestParam("Vege") Instant vege) {
        if (kezd == null || vege == null) {
            return hardwareSearchMapper.modelsToDtos(hardwareSearchDao.findAll());
        }

        return hardwareSearchMapper.modelsToDtos(hardwareSearchDao.findHardwareSearchByDatumIsBetween(kezd, vege));
    }

}
