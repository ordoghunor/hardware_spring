package edu.bbte.idde.ohim2065.hardware.spring.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "brand")
public class Brand extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 1024)
    private String motto;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brandid")
    private Collection<Hardware> hardwareCollection;
}
