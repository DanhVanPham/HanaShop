/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.listener;

import danhpv.daos.CategoryDAO;
import danhpv.dtos.Category;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author DELL
 */
public class FirstServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            ServletContext context = event.getServletContext();
            CategoryDAO categoryDao = new CategoryDAO();
            List<Category> listCategories = categoryDao.getAllCategory();
            context.setAttribute("LIST_CATEGORY", listCategories);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.removeAttribute("LIST_CATEGORY");
    }
}
