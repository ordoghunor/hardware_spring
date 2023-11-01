package edu.bbte.idde.ohim2065.hardware.spring.controller;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming.HardwareIncomingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.HardwareDetailsDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.HardwareListingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.mapper.HardwareMapper;
import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.spring.exception.ResourceNotFoundException;
import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;
import edu.bbte.idde.ohim2065.hardware.spring.service.HardwareSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/hardwares")
public class HardwareController {
    @Autowired
    private HardwareMapper hardwareMapper;

    @Autowired
    private HardwareSearchService hardwareSearchService;
    @Autowired
    private HardwareDao hardwareDao;

    @GetMapping
    public Collection<HardwareListingDto> getHardwares(@RequestParam(required = false) Double minprice,
                                                       @RequestParam(required = false) Double maxprice) {
        if (minprice == null || maxprice == null) {
            return hardwareMapper.modelsToDtos(hardwareDao.findAll());
        }

        hardwareSearchService.insertQueryAndTime(minprice, maxprice);
        return hardwareMapper.modelsToDtos(hardwareDao.findHardwareByPriceIsBetween(minprice, maxprice));
    }

    @PostMapping
    public HardwareDetailsDto create(@RequestBody @Valid HardwareIncomingDto hardwareIncomingDto) {
        return hardwareMapper.modelToDetailedDto(
                hardwareDao.saveAndFlush(hardwareMapper.hardwareFromIncomingDto(hardwareIncomingDto)));
    }

    @GetMapping("/{id}")
    public HardwareDetailsDto getHardware(@PathVariable("id") Long id) {
        Hardware auxHardware = hardwareDao.getById(id);
        if (auxHardware == null) {
            throw new ResourceNotFoundException();
        }
        return hardwareMapper.modelToDetailedDto(auxHardware);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        hardwareDao.delete(hardwareDao.getById(id));
    }

    @PutMapping("/{id}")
    public HardwareDetailsDto update(@PathVariable("id") Long id,
                                  @RequestBody @Valid HardwareIncomingDto hardwareIncomingDto) {
        Hardware auxHardware = hardwareMapper.hardwareFromIncomingDto(hardwareIncomingDto);
        auxHardware.setId(id);
        return hardwareMapper.modelToDetailedDto(hardwareDao.saveAndFlush(auxHardware));
    }
}
