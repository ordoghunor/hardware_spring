package edu.bbte.idde.ohim2065.hardware.spring.controller.dto.incoming;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BrandIncomingDto {
    @Length(max = 100)
    private String name;
    @Length(max = 1024)
    private String motto;
}
