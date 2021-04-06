/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.FoodDAO;
import danhpv.daos.OrderDAO;
import danhpv.daos.OrderDetailsDAO;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Food;
import danhpv.dtos.Order;
import danhpv.dtos.User;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class ProceedWithPaymentController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "login.jsp";
    private static final String SUCCESS = "receipt.jsp";
    private static final String OUT_OF_STOCK = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            if (user == null) {
                url = LOGIN;
            } else {
                CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
                if (cartShopping != null) {
                    OrderDAO orderDao = new OrderDAO();
                    OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
                    int count;
                    String orderId = orderDao.getLastOrderRecord();
                    if (orderId == null) {
                        count = 1;
                        orderId = "ORDER-" + String.format("%05d", count);
                    } else {
                        String[] split = orderId.split("-");
                        try {
                            count = Integer.parseInt(split[1]);
                            count += 1;
                            orderId = split[0] + "-" + String.format("%05d", count);
                        } catch (NumberFormatException e) {
                            orderId = null;
                        }
                    }
                    if (orderId != null) {
                        //check food is out stock
                        FoodDAO foodDao = new FoodDAO();
                        boolean isOutOfStock = false;
                        HashMap<String, Food> listProductOutOfStock = new HashMap<>();
                        for (Food food : cartShopping.getCart().values()) {
                            Food foodExisted = foodDao.getFoodByFoodId(food.getFoodId());
                            if (foodExisted.getFoodQuantity() < food.getFoodQuantity() || food.getFoodQuantity() <= 0) {
                                listProductOutOfStock.put(food.getFoodId(), foodExisted);
                                isOutOfStock = true;
                            }
                        }
                        if (!isOutOfStock) {
                            Order order = new Order(orderId, user.getUserId(), user.getFullName(), user.getPhoneNumber(), user.getAddress(), new Date(), cartShopping.getTotalOrder(), 1);
                            if (orderDao.createNewOrder(order)) {
                                boolean checkCreateOrderAndOrderDetails;
                                checkCreateOrderAndOrderDetails = orderDetailsDao.createNewOrderAndOrderDetails(cartShopping.getCart(), user, order);
                                if (checkCreateOrderAndOrderDetails) {
                                    session.removeAttribute("cart");
                                    url = SUCCESS;
                                } else {
                                    request.setAttribute("ERROR", "Order failed");
                                }
                            } else {
                                request.setAttribute("ERROR", "Order failed");
                            }
                        } else {
                            url = OUT_OF_STOCK;
                            request.setAttribute("OUT_OF_STOCK", listProductOutOfStock);
                        }
                    } else {
                        request.setAttribute("ERROR", "Order failed");
                    }
                } else {
                    request.setAttribute("ERROR", "Can not find your cart!");
                }
            }
        } catch (Exception e) {
            log("Error at ProceedWithPaymentController: " + e.getMessage());
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
