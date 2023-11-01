package edu.bbte.idde.ohim2065.hardware.backend.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseEntity {
    private String name;
    private String motto;
}
