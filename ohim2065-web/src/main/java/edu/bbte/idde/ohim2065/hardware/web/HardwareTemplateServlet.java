package edu.bbte.idde.ohim2065.hardware.web;

import edu.bbte.idde.ohim2065.hardware.backend.dao.HardwareDao;
import edu.bbte.idde.ohim2065.hardware.backend.dao.memory.HardwareMemoryDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/hardwareTemplate")
public class HardwareTemplateServlet extends HttpServlet {
    private Template template;

    private HardwareDao hardwareDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareTemplateServlet.class);

    @Override
    public void init() throws ServletException {
        hardwareDao = new HardwareMemoryDao();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(HardwareTemplateServlet.class, "/");
        try {
            template = cfg.getTemplate("index.ftl");
        } catch (IOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("GET /hardwareTemplate");

        Map<String, Object> templateData = new ConcurrentHashMap<>();
        templateData.put("hardwares", hardwareDao.findAll());

        try {
            template.process(templateData, resp.getWriter());
        } catch (TemplateException e) {
            throw new ServletException();
        }

    }
}
