package edu.bbte.idde.ohim2065.hardware.spring.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "hardware")
public class Hardware extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(length = 100)
    private String manufacturer;
    @Column(length = 50)
    private String color;

    @Column(name = "brandid")
    private Long brandId;
}
