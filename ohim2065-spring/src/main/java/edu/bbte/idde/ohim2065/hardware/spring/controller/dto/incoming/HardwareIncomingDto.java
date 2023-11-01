package edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;

@Data
public class HardwareIncomingDto {
    @Length(max = 100)
    private String name;
    @Positive
    private Double price;
    @Length(max = 100)
    private String manufacturer;
    @Length(max = 50)
    private String color;
    private Integer brandId;
}
