package edu.bbte.idde.ohim2065.hardware.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.ohim2065.hardware.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.BrandDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Brand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet ("/brands")
public class BrandServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServlet.class);
    private BrandDao brandDao;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        LOGGER.info("Initialize brands");
        brandDao = AbstractDaoFactory.getDaoFactory().getBrandDao();
        objectMapper = ObjectMapperFactory.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("GET /brands");
        String id = req.getParameter("id");

        if (id == null) {
            resp.setHeader("Content-Type", "application/json");
            objectMapper.writeValue(resp.getOutputStream(), brandDao.findAll());
        } else {
            try {
                Long brandid = Long.parseLong(id);
                Brand response = brandDao.findById(brandid);
                if (response == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("No hardware with Id" + brandid);
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(resp.getOutputStream(), response);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Invalid Id");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("POST /brands");

        Brand brand = null;
        try {
            brand = objectMapper.readValue(req.getInputStream(), Brand.class);
        } catch (JsonMappingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Cannot map object!");
        }

        assert brand != null;
        if (brand.getName() == null || brand.getMotto() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Name or motto missing!");
        } else {
            try {
                brandDao.create(brand);
                LOGGER.info("Brand created successfully!");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setHeader("Content-Type", "application/json");
                objectMapper.writeValue(resp.getOutputStream(), brand);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Data format problem!");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("DELETE /brands");
        String id = req.getParameter("id");

        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("No id parameter was found!");
        } else {
            try {
                Long brandId = Long.parseLong(id);
                Brand response = brandDao.findById(brandId);
                if (response == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Id non existent!");
                } else {
                    brandDao.delete(response);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Brand deleted!");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Id non existent!");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("PUT /brands");
        String id = req.getParameter("id");

        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("No id parameter was found!");
        } else {
            try {
                Long brandId = Long.parseLong(id);
                Brand response = brandDao.findById(brandId);
                if (response == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Id non existent!");
                } else {
                    Brand newBrand = objectMapper.readValue(req.getInputStream(), Brand.class);
                    newBrand.setId(brandId);
                    brandDao.update(newBrand);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Brand updated!");
                }
            } catch (NumberFormatException | JsonMappingException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Id non existent!");
            }
        }
    }
}
