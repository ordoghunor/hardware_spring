package edu.bbte.idde.ohim2065.hardware.spring.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "hardwaresearch")
public class HardwareSearch extends BaseEntity {
    @Column(nullable = false)
    private String query;
    @Column(nullable = false)
    private Instant datum;
}
