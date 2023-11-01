package edu.bbte.idde.ohim2065.hardware.spring.dao.jdbc;

import edu.bbte.idde.ohim2065.hardware.spring.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.spring.exception.DaoException;
import edu.bbte.idde.ohim2065.hardware.spring.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

@Repository
@Profile("jdbc")
public class HardwareJdbcDao implements HardwareDao {
    @Autowired
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareJdbcDao.class);

    @PostConstruct
    protected void init() {
        LOGGER.info("{} Initialized.", HardwareJdbcDao.class);
    }

    @Override
    public Hardware saveAndFlush(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO hardware (id, name, price, manufacturer, color, brandid) "
                    + " VALUES (default, ?, ?, ?, ?, ?); ";
            PreparedStatement statement = createPrepareStmt(connection, query);
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getManufacturer());
            statement.setString(4, entity.getColor());
            statement.setLong(5, entity.getBrandId());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            }
            return entity;
        } catch (SQLException e) {
            LOGGER.error("SQLException INSERT", e);
            return null;
        }
    }

    @Override
    public Collection<Hardware> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM hardware ; ";
            PreparedStatement stmt = createPrepareStmt(connection, query);
            return getHardwaresAuxiliar(stmt);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    @Override
    public Hardware getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM Hardware WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);
            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                Hardware hardware = new Hardware(set.getString("name"),
                        set.getDouble("price"),
                        set.getString("manufacturer"),
                        set.getString("color"),
                        set.getLong("brand_id")
                );
                hardware.setId(id);
                return hardware;
            }
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
            throw new DaoException();
        }
        return null;
    }

    public Hardware update(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE hardware SET name = ? , price = ? , manufacturer = ? ,"
                    + " color = ? , brandid = ? WHERE id = ? ; ";
            PreparedStatement statement = createPrepareStmt(connection, query);

            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getManufacturer());
            statement.setString(4, entity.getColor());
            statement.setLong(5, entity.getBrandId());
            statement.setLong(6, entity.getId());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return entity;
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException UPDATE", e);
        }
        return null;
    }

    @Override
    public void delete(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM hardware WHERE id = ? ; ";
            PreparedStatement statement = createPrepareStmt(connection, query);
            statement.setLong(1, entity.getId());

            LOGGER.info("Deleting hardware " + entity.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException UPDATE", e);
        }
    }

    @Override
    public Collection<Hardware> findHardwareByPriceIsBetween(Double minprice, Double maxprice) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM hardware WHERE price > ? AND price < ?; ";
            PreparedStatement stmt = createPrepareStmt(connection, query);
            stmt.setDouble(1, minprice);
            stmt.setDouble(2, maxprice);

            return getHardwaresAuxiliar(stmt);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    private Collection<Hardware> getHardwaresAuxiliar(PreparedStatement stmt) throws SQLException {
        ResultSet result = stmt.executeQuery();

        Collection<Hardware> hardwares = new LinkedList<>();

        while (result.next()) {
            Hardware hardware = buildHardware(result.getLong("id"),
                    result.getString("color"),
                    result.getString("name"),
                    result.getDouble("price")
            );
            hardwares.add(hardware);
        }
        return hardwares;
    }

    private PreparedStatement createPrepareStmt(Connection connection, String query) throws SQLException {
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    private Hardware buildHardware(Long id, String color, String name, Double price) {
        Hardware hardware = new Hardware();
        hardware.setId(id);
        hardware.setColor(color);
        hardware.setName(name);
        hardware.setPrice(price);
        return hardware;
    }
}
