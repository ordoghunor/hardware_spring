package edu.bbte.idde.ohim2065.hardware.spring.controller;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming.HardwareIncomingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.mapper.HardwareMapper;
import edu.bbte.idde.ohim2065.hardware.spring.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.spring.exception.ResourceNotFoundException;
import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;
import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Objects;

@Profile("jpa")
@RestController
@RequestMapping("/brand/{id}/hardwares")
public class BrandHardwareController {
    @Autowired
    private HardwareMapper hardwareMapper;

    @Autowired
    private BrandDao brandDao;

    @GetMapping
    public Collection<Hardware> hardwaresById(@PathVariable("id") Long id) {
        Brand brand = brandDao.getById(id);
        if (brand == null) {
            throw new ResourceNotFoundException();
        }
        return brand.getHardwareCollection();
    }

    @PostMapping
    public Collection<Hardware> hardwareToBrand(@PathVariable("id") Long id,
                                              @RequestBody @Valid HardwareIncomingDto hardwareIncomingDto) {
        Hardware hardware = hardwareMapper.hardwareFromIncomingDto(hardwareIncomingDto);
        hardware.setBrandId(id);
        Brand brand = brandDao.getById(id);
        brand.getHardwareCollection().add(hardware);
        brandDao.saveAndFlush(brand);
        return brand.getHardwareCollection();
    }

    @DeleteMapping("/{hardware_id}")
    public void delete(@PathVariable("id") Long id, @PathVariable("hardware_id") Long hardwareId) {
        Brand brand = brandDao.getById(id);
        brand.getHardwareCollection().removeIf(
                selectedHardware -> Objects.equals(selectedHardware.getId(), hardwareId));
        brandDao.saveAndFlush(brand);
    }

}
