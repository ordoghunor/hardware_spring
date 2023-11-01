package edu.bbte.idde.ohim2065.hardware.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter("/hardwareTemplate")
public class AuthFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
    private Template template;
    private static final String PASSWORD = "admin1";
    private static final String USERNAME = "admin1";

    @Override
    public void init() throws ServletException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(HardwareTemplateServlet.class, "/");
        try {
            template = cfg.getTemplate("auth.ftl");
        } catch (IOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Filter AuthFilter");

        HttpSession session = req.getSession();

        if (session == null) {
            notSuccesfulAuth(res);
        } else {
            String pass = (String) session.getAttribute("password");
            String user = (String) session.getAttribute("username");
            LOGGER.info(pass + "  " + user);
            if (pass == null || user == null || !pass.equals(PASSWORD) || !user.equals(USERNAME)) {
                notSuccesfulAuth(res);
            } else {
                LOGGER.info("Auth succesful");
                chain.doFilter(req, res);
            }
        }
    }

    private void notSuccesfulAuth(HttpServletResponse res) throws IOException, ServletException {
        try {
            LOGGER.info("Auth unsuccesful");
            Map<String, Object> templateData = new ConcurrentHashMap<>();
            templateData.put("msg", "login");
            template.process(templateData, res.getWriter());
        } catch (TemplateException e) {
            throw new ServletException();
        }
    }
}
