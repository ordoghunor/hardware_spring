package edu.bbte.idde.ohim2065.hardware.spring.dao.jdbc;

import edu.bbte.idde.ohim2065.hardware.spring.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.spring.exception.DaoException;
import edu.bbte.idde.ohim2065.hardware.spring.model.Brand;
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
public class BrandJdbcDao implements BrandDao {
    @Autowired
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandJdbcDao.class);

    @PostConstruct
    protected void init() {
        LOGGER.info("{} Initialized.", BrandJdbcDao.class);
    }

    @Override
    public Brand saveAndFlush(Brand entity) {
        if (entity.getId() == null) {
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
        return update(entity);
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
    public Brand getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM brand WHERE id = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);

            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return buildBrand(id, set.getString("name"), set.getString("motto"));
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
            throw new DaoException();
        }
    }

    public Brand update(Brand entity) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE brand SET name = ? , motto = ? WHERE id = ? ; ";
            PreparedStatement statement = createPrepareStmt(connection, query);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getMotto());
            statement.setLong(3, entity.getId());

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

    @Override
    public Brand findByName(String name) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM brand WHERE name = ? ";
            PreparedStatement prep = createPrepareStmt(connection, query);

            prep.setString(1, name);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return buildBrand(set.getLong("id"), set.getString("name"), set.getString("motto"));
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error("Hiba: {}", e.toString());
            throw new DaoException();
        }
    }
}
