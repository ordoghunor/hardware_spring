package edu.bbte.idde.ohim2065.hardware.spring.controller.mapper;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming.HardwareIncomingDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.HardwareDetailsDto;
import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.HardwareListingDto;
import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface HardwareMapper {

    HardwareListingDto modelToDto(Hardware hardware);

    Hardware hardwareFromDetailsDto(HardwareDetailsDto hardwareDetailsDto);

    Hardware hardwareFromIncomingDto(HardwareIncomingDto hardwareIncomingDto);

    Collection<HardwareListingDto> modelsToDtos(Collection<Hardware> hardwares);

    HardwareDetailsDto modelToDetailedDto(Hardware hardware);
}
