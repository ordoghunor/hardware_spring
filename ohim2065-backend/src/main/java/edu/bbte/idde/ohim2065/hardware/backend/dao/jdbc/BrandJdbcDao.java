package edu.bbte.idde.ohim2065.hardware.backend.dao.jdbc;

import edu.bbte.idde.ohim2065.hardware.backend.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class BrandJdbcDao implements BrandDao {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandJdbcDao.class);

    public BrandJdbcDao() {
        dataSource = DataSourceFactory.getDataSource();
    }

    @Override
    public Brand create(Brand entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO brand (id, name, motto) VALUES (default, ?, ?); ";
            PreparedStatement statement = createPrepareStmt(connection, query);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getMotto());

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
    public Collection<Brand> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM brand ; ";
            PreparedStatement stmt = createPrepareStmt(connection, query);

            ResultSet result = stmt.executeQuery();

            Collection<Brand> brands = new LinkedList<>();

            while (result.next()) {
                Brand brand = buildBrand(result.getLong("id"),
                        result.getString("name"),
                        result.getString("motto"));
                brands.add(brand);
            }

            return brands;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    @Override
    public Brand findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM brand WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);

            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return new Brand(set.getString("name"),
                        set.getString("motto")
                );
            }
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
        }
        return null;
    }

    @Override
    public Boolean update(Brand entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE brand SET name = ? , motto = ? WHERE id = ? ; ";
            PreparedStatement statement = createPrepareStmt(connection, query);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getMotto());
            statement.setLong(3, entity.getId());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            return keys.next();
        } catch (SQLException e) {
            LOGGER.error("SQLException UPDATE", e);
            return false;
        }
    }

    @Override
    public void delete(Brand entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM brand WHERE id = ? ; ";
            PreparedStatement statement = createPrepareStmt(connection, query);

            statement.setLong(1, entity.getId());

            LOGGER.info("Deleting Brand " + entity.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException UPDATE", e);
        }
    }

    @Override
    public Brand findByBrand(Brand entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM brand WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);

            prep.setLong(1, entity.getId());
            ResultSet set = prep.executeQuery();

            if (set.next()) {
                return new Brand(set.getString("name"),
                        set.getString("motto")
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

    private Brand buildBrand(Long id, String name, String motto) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(name);
        brand.setMotto(motto);
        return brand;
    }
}
