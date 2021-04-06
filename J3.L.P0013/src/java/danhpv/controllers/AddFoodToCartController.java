/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.FoodDAO;
import danhpv.daos.RoleDAO;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Food;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class AddFoodToCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ViewFoodsController";
    private static final String OUT_OF_STOCK = "ViewFoodsController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            boolean isAdmin = false;
            String foodId = request.getParameter("foodId");
            RoleDAO roleDao = new RoleDAO();
            Role checkRole = null;
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            if (user != null) {
                String roleId = user.getRoleId();
                checkRole = roleDao.getRoleByRoleId(roleId);
            }
            if (user != null && checkRole != null) {
                if (checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                    request.setAttribute("ERROR", "Your role is invalid!");
                    url = ERROR;
                    isAdmin = true;
                }
            }
            if (!isAdmin) {
                CartShopping shoppingCart = (CartShopping) session.getAttribute("cart");
                if (shoppingCart == null) {
                    shoppingCart = new CartShopping();
                }
                FoodDAO foodDao = new FoodDAO();
                Food checkFoodIdExisted = foodDao.getFoodByFoodId(foodId);
                if (checkFoodIdExisted != null) {
                    if (shoppingCart.getCart().isEmpty() || !shoppingCart.getCart().containsKey(checkFoodIdExisted.getFoodId())) {
                        checkFoodIdExisted.setFoodQuantity(1);
                        if (shoppingCart.addCart(checkFoodIdExisted)) {
                            url = SUCCESS;
                            session.setAttribute("cart", shoppingCart);
                        } else {
                            request.setAttribute("ERROR", "Add food/drink to cart failed!");
                        }
                    } else {
                        if (shoppingCart.getCart().get(checkFoodIdExisted.getFoodId()).getFoodQuantity() < checkFoodIdExisted.getFoodQuantity()) {
                            checkFoodIdExisted.setFoodQuantity(1);
                            if (shoppingCart.addCart(checkFoodIdExisted)) {
                                url = SUCCESS;
                                session.setAttribute("cart", shoppingCart);
                            } else {
                                request.setAttribute("ERROR", "Add food/drink to cart failed!");
                            }
                        } else {
                            url = OUT_OF_STOCK;
                            request.setAttribute("OUT_OF_STOCK", "Can not add food/drink to cart! Out of stock!"
                                    + checkFoodIdExisted.getFoodQuantity() + "!");
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "FoodId is not found!");
                }
            }
        } catch (Exception e) {
            log("Error at AddFoodToCartController: " + e.getMessage());
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
