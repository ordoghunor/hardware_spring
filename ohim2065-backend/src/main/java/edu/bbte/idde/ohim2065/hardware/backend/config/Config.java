package edu.bbte.idde.ohim2065.hardware.backend.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Config {
    private String daoType;
    private String url;
    private String user;
    private String password;
    private Integer poolSize;
    private String driverClassName;

    private Boolean verzio;
}
