package edu.bbte.idde.ohim2065.hardware.spring.controller.dto.outgoing;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class BrandDetailsDto {
    @NotNull
    private Long id;
    @NotNull
    @Length(max = 100)
    private String name;
    @Length(max = 1024)
    private String motto;
}
