package edu.bbte.idde.ohim2065.hardware.backend.dao;

import edu.bbte.idde.ohim2065.hardware.backend.config.ConfigFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.memory.MemDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDaoFactory {
    private static DaoFactory daoFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoFactory.class);

    public static DaoFactory getDaoFactory() {
        String daoType = ConfigFactory.getConfig().getDaoType();
        if ("jdbc".equalsIgnoreCase(daoType)) {
            LOGGER.info("JDBC Factory initialize");
            daoFactory = new JdbcDaoFactory();
        } else {
            LOGGER.info("Mem Factory initialize");
            daoFactory = new MemDaoFactory();
        }
        return daoFactory;
    }
}

