package edu.bbte.idde.ohim2065.hardware.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import edu.bbte.idde.ohim2065.hardware.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.backend.model.Hardware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet ("/hardwares")
public class HardwareServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareServlet.class);
    private HardwareDao hardwareDao;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        LOGGER.info("Initialize");
        super.init();
        try {
            hardwareDao = AbstractDaoFactory.getDaoFactory().getHardwareDao();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Could not get hardwareDao.");
        }
        objectMapper = ObjectMapperFactory.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("GET /hardwares");
        String id = req.getParameter("id");

        if (id == null) {
            resp.setHeader("Content-Type", "application/json");
            objectMapper.writeValue(resp.getOutputStream(), hardwareDao.findAll());
        } else {
            try {
                Long hardwareId = Long.parseLong(id);
                Hardware response = hardwareDao.findById(hardwareId);
                if (response == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("No hardware with Id" + hardwareId);
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
        LOGGER.info("POST /hardwares");

        Hardware hardware = null;
        try {
            hardware = objectMapper.readValue(req.getInputStream(), Hardware.class);
        } catch (JsonMappingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Cannot map object!");
        }

        assert hardware != null;
        if (hardware.getPrice() == null || hardware.getName() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Name or price missing!");
        } else {
            try {
                hardwareDao.create(hardware);
                LOGGER.info("Hardware created successfully!");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setHeader("Content-Type", "application/json");
                objectMapper.writeValue(resp.getOutputStream(), hardware);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Data format problem!");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("DELETE /hardwares");
        String id = req.getParameter("id");

        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("No id parameter was found!");
        } else {
            try {
                Long hardwareId = Long.parseLong(id);
                Hardware response = hardwareDao.findById(hardwareId);
                if (response == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Id non existent!");
                } else {
                    hardwareDao.delete(response);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Hardware deleted!");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Id non existent!");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("PUT /hardwares");
        String id = req.getParameter("id");

        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("No id parameter was found!");
        } else {
            try {
                Long hardwareId = Long.parseLong(id);
                Hardware response = hardwareDao.findById(hardwareId);
                if (response == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Id non existent!");
                } else {
                    Hardware newHardware = objectMapper.readValue(req.getInputStream(), Hardware.class);
                    newHardware.setId(hardwareId);
                    hardwareDao.update(newHardware);
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } catch (NumberFormatException | JsonMappingException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Id non existent!");
            }
        }
    }
}
