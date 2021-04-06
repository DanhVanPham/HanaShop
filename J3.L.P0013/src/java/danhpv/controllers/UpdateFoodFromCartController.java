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
import java.util.HashMap;
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
public class UpdateFoodFromCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    private static final String OUT_OF_STOCK = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String[] listFoodId = request.getParameterValues("foodId");
            String[] listFoodQuantity = request.getParameterValues("foodQuantity");
            HttpSession session = request.getSession();
            CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
            if (cartShopping != null) {
                FoodDAO foodDao = new FoodDAO();
                boolean isOutOfStock = false;
                HashMap<String, Food> listProductOutOfStock = new HashMap<>();
                for (int i = 0; i < listFoodId.length; i++) {
                    Food foodExisted = foodDao.getFoodByFoodId(listFoodId[i]);
                    if (foodExisted.getFoodQuantity() < Integer.parseInt(listFoodQuantity[i])) {
                        listProductOutOfStock.put(listFoodId[i], foodExisted);
                        isOutOfStock = true;
                    }
                }
                if (!isOutOfStock) {
                    for (int i = 0; i < listFoodId.length; i++) {
                        try {
                            int quantity = Integer.parseInt(listFoodQuantity[i]);
                            if (quantity > 0) {
                                cartShopping.update(listFoodId[i], quantity);
                            } else {
                                request.setAttribute("INVALID", "Required input quantity is number greater than 0!");
                            }
                        } catch (Exception e) {
                            request.setAttribute("INVALID", "Required input quantity is number greater than 0!");
                        }
                    }
                    session.setAttribute("cart", cartShopping);
                    url = SUCCESS;
                } else {
                    url = OUT_OF_STOCK;
                    request.setAttribute("OUT_OF_STOCK", listProductOutOfStock);
                }
            } else {
                request.setAttribute("ERROR", "Can not find cart");
            }
            
            User user = (User) session.getAttribute("USER");
            FoodDAO foodDao = new FoodDAO();
            if (user != null) {
                List<Food> listFoodFavourite = foodDao.getListFoodFavouritesByUserId(user);
                if (listFoodFavourite != null) {
                    request.setAttribute("LIST_FAVORITE", listFoodFavourite);
                }
            }
        } catch (Exception e) {
            log("Error UpdateFoodFromCartController: " + e.getMessage());
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
