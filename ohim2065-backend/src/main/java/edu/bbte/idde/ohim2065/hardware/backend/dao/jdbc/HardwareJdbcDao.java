package edu.bbte.idde.ohim2065.hardware.backend.dao.jdbc;

import edu.bbte.idde.ohim2065.hardware.backend.config.Config;
import edu.bbte.idde.ohim2065.hardware.backend.config.ConfigFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class HardwareJdbcDao implements HardwareDao {
    private final DataSource dataSource;
    private final Config config;
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareJdbcDao.class);

    public HardwareJdbcDao() {
        dataSource = DataSourceFactory.getDataSource();
        config = ConfigFactory.getConfig();
    }

    @Override
    public Hardware create(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO hardware (id, name, price, manufacturer, color, brandid, verzioszam) "
                    + " VALUES (default, ?, ?, ?, ?, ?, ?); ";
            PreparedStatement statement = createPrepareStmt(connection, query);
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getManufacturer());
            statement.setString(4, entity.getColor());
            statement.setInt(5, entity.getBrandid());
            statement.setInt(6, 1);

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            }
            entity.setVerzioszam(1);
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
            ResultSet result = stmt.executeQuery();

            Collection<Hardware> hardwares = new LinkedList<>();

            while (result.next()) {
                Hardware hardware;
                if (config.getVerzio()) {
                    hardware = buildHardwareWithVerzioszam(
                            result.getLong("id"),
                            result.getInt("brandid"),
                            result.getString("color"),
                            result.getString("name"),
                            result.getDouble("price"),
                            result.getInt("verzioszam")
                    );
                } else {
                    hardware = buildHardware(result.getLong("id"),
                            result.getInt("brandid"),
                            result.getString("color"),
                            result.getString("name"),
                            result.getDouble("price")
                    );
                }
                hardwares.add(hardware);
            }

            return hardwares;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    @Override
    public Hardware findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM Hardware WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);
            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                if (config.getVerzio()) {
                    return buildHardwareWithVerzioszam(
                            set.getLong("id"),
                            set.getInt("brandid"),
                            set.getString("color"),
                            set.getString("name"),
                            set.getDouble("price"),
                            set.getInt("verzioszam")
                    );
                } else {
                    return buildHardware(
                            set.getLong("id"),
                            set.getInt("brandid"),
                            set.getString("color"),
                            set.getString("name"),
                            set.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
        }
        return null;
    }

    @Override
    public Boolean update(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE hardware SET name = ? , price = ? , manufacturer = ? ,"
                    + " color = ? , brandid = ? , verzioszam = ? WHERE id = ? ; ";
            PreparedStatement statement = createPrepareStmt(connection, query);

            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getManufacturer());
            statement.setString(4, entity.getColor());
            statement.setInt(5, entity.getBrandid());
            Integer newVerzioszam = getVerzioszam(entity.getId());
            statement.setInt(6, Objects.requireNonNullElse(newVerzioszam, 1) + 1);
            statement.setLong(7, entity.getId());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            return keys.next();
        } catch (SQLException e) {
            LOGGER.error("SQLException UPDATE", e);
            return false;
        }
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
    public Hardware findByHardware(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM Hardware WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);

            prep.setLong(1, entity.getId());
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return new Hardware(set.getString("name"),
                        set.getDouble("price"),
                        set.getString("manufacturer"),
                        set.getString("color"),
                        set.getInt("brandid")
                );
            }
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
        }
        return null;
    }

    private PreparedStatement createPrepareStmt(Connection connection, String query) throws SQLException {
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    private Hardware buildHardware(Long id, Integer brandid, String color, String name, Double price) {
        Hardware hardware = new Hardware();
        hardware.setId(id);
        hardware.setBrandid(brandid);
        hardware.setColor(color);
        hardware.setName(name);
        hardware.setPrice(price);
        return hardware;
    }

    private Hardware buildHardwareWithVerzioszam(Long id, Integer brandid, String color, String name, Double price,
                                                 Integer verzioszam) {
        Hardware hardware = new Hardware();
        hardware.setId(id);
        hardware.setBrandid(brandid);
        hardware.setColor(color);
        hardware.setName(name);
        hardware.setPrice(price);
        hardware.setVerzioszam(verzioszam);
        return hardware;
    }

    private Integer getVerzioszam(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT verzioszam FROM Hardware WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);

            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return set.getInt("verzioszam");
            }
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
        }
        return null;
    }
}
