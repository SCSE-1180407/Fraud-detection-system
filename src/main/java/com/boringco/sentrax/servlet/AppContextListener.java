package com.boringco.sentrax.servlet;
import com.boringco.sentrax.service.AlertService;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
@WebListener
public class AppContextListener implements ServletContextListener {
    @Override public void contextDestroyed(ServletContextEvent e) { AlertService.shutdown(); }
}
