package edu.bbte.idde.ohim2065.hardware.spring.controller.mapper;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming.BrandIncomingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.BrandDetailsDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.BrandListingDto;
import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandListingDto modelToDto(Brand brand);

    Collection<BrandListingDto> modelsToDtos(Collection<Brand> brands);

    Brand brandFromDetailsDto(BrandDetailsDto brandDetailsDto);

    Brand brandFromIncomingDto(BrandIncomingDto brandIncomingDto);

    BrandDetailsDto modelToDetailedDto(Brand brand);
}
