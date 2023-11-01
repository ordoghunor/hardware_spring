package edu.bbte.idde.ohim2065.hardware.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    protected Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer verzioszam;
}
