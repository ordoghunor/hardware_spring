package edu.bbte.idde.ohim2065.hardware.spring.controller;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming.BrandIncomingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.BrandDetailsDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.BrandListingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.mapper.BrandMapper;
import edu.bbte.idde.ohim2065.hardware.spring.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.spring.exception.ResourceNotFoundException;
import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private BrandDao brandDao;

    @GetMapping
    public Collection<BrandListingDto> getBrands() {
        return brandMapper.modelsToDtos(brandDao.findAll());
    }

    @GetMapping("/{id}")
    public BrandDetailsDto getBrands(@PathVariable("id") Long id) {
        Brand auxBrand = brandDao.getById(id);
        if (auxBrand == null) {
            throw new ResourceNotFoundException();
        }
        return brandMapper.modelToDetailedDto(auxBrand);
    }

    @PostMapping
    public BrandDetailsDto create(@RequestBody @Valid BrandIncomingDto brandIncomingDto) {
        return brandMapper.modelToDetailedDto(
                brandDao.saveAndFlush(brandMapper.brandFromIncomingDto(brandIncomingDto)));
    }

    @PutMapping("/{id}")
    public BrandDetailsDto update(@PathVariable("id") Long id, @RequestBody @Valid BrandIncomingDto brandIncomingDto) {
        Brand auxBrand = brandMapper.brandFromIncomingDto(brandIncomingDto);
        auxBrand.setId(id);
        return brandMapper.modelToDetailedDto(brandDao.saveAndFlush(auxBrand));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        brandDao.delete(brandDao.getById(id));
    }
}
