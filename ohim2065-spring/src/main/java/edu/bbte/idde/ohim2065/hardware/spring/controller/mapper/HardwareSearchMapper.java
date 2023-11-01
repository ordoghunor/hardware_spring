package edu.bbte.idde.ohim2065.hardware.spring.controller.mapper;

import edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing.HardwareSearchDto;
import edu.bbte.idde.ohim2065.hardware.spring.model.HardwareSearch;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface HardwareSearchMapper {
    Collection<HardwareSearchDto> modelsToDtos(Collection<HardwareSearch> searches);
}
