package edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing;

import lombok.Data;

import java.time.Instant;

@Data
public class HardwareSearchDto {
    Long id;
    String query;
    Instant datum;
}
