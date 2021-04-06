/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import com.paypal.base.rest.PayPalRESTException;
import danhpv.daos.FoodDAO;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Food;
import danhpv.dtos.User;
import danhpv.paypal.PaymentServices;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class AuthorizePaymentServletController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String OUT_OF_STOCK = "viewCart.jsp";
    private static final String LOGIN = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String approvalLink;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            if (user == null) {
                url = LOGIN;
            } else {
                CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
                //check food is out stock
                FoodDAO foodDao = new FoodDAO();
                boolean isOutOfStock = false;
                HashMap<String, Food> listProductOutOfStock = new HashMap<>();
                for (Food food : cartShopping.getCart().values()) {
                    Food foodExisted;
                    try {
                        foodExisted = foodDao.getFoodByFoodId(food.getFoodId());
                        if (foodExisted.getFoodQuantity() < food.getFoodQuantity()) {
                            listProductOutOfStock.put(food.getFoodId(), foodExisted);
                            isOutOfStock = true;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AuthorizePaymentServletController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!isOutOfStock) {
                    PaymentServices paymentServices = new PaymentServices();
                    approvalLink = paymentServices.authorizePayment(cartShopping);
                    url = approvalLink;
                } else {
                    url = OUT_OF_STOCK;
                    request.setAttribute("OUT_OF_STOCK", listProductOutOfStock);
                }
            }
        } catch (PayPalRESTException ex) {
            request.setAttribute("ERROR", ex.getMessage());
        } finally {
            if (!url.equals(OUT_OF_STOCK) && !url.equals(ERROR)) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
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
