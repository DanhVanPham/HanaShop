/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.FoodDAO;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Food;
import danhpv.dtos.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class RemoveFoodFromCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String foodId = request.getParameter("foodId");
            HttpSession session = request.getSession();
            CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
            if (cartShopping != null) {
                if (cartShopping.remove(foodId)) {
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Remove food is failed!");
                }
            } else {
                request.setAttribute("ERROR", "Cart is not found!");
            }
            
            //get list your favorite
            User user = (User) session.getAttribute("USER");
            FoodDAO foodDao = new FoodDAO();
            if (user != null) {
                List<Food> listFoodFavourite = foodDao.getListFoodFavouritesByUserId(user);
                if (listFoodFavourite != null) {
                    request.setAttribute("LIST_FAVORITE", listFoodFavourite);
                }
            }
        } catch (Exception e) {
            log("Error at RemoveFoodFromCartController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
