package edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class HardwareDetailsDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Positive
    private Double price;
    @Length(max = 100)
    private String manufacturer;
    @Length(max = 50)
    private String color;
    private Long brandId;
}
