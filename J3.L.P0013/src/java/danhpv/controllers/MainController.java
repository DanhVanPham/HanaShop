/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class MainController extends HttpServlet {

    private static final String LOGIN_ACCOUNT = "LoginWithAccountController";
    private static final String ERROR = "error.jsp";
    private static final String SEARCH_FOODS = "ViewFoodsController";
    private static final String LOG_OUT = "LogoutController";
    private static final String FORM_CREATE_FOOD = "formCreateFood.jsp";
    private static final String CREATE_FOOD = "CreateFoodController";
    private static final String MANAGE_FOOD = "ViewFoodsByAdminController";
    private static final String VIEW_FOOD_DETAIL_BY_USER = "ViewFoodDetailsController";
    private static final String ADD_FOOD_TO_CART = "AddFoodToCartController";
    private static final String VIEW_CART = "ViewCartController";
    private static final String REMOVE_FOOD_FROM_CART = "RemoveFoodFromCartController";
    private static final String UPDATE_FOOD_FROM_CART = "UpdateFoodFromCartController";
    private static final String REMOVE_FOOD_ADMIN = "RemoveFoodByAdminController";
    private static final String FORM_UPDATE_FOOD = "FormUpdateFoodController";
    private static final String PAYMENT_WITH_CASH = "ProceedWithPaymentController";
    private static final String PAYMENT_WITH_PAYPAL = "AuthorizePaymentServletController";
    private static final String HISTORY_ORDER = "ViewHistoryOrderController";
    private static final String VIEW_ORDER_DETAIL = "ViewOrderDetailController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "Login Account":
                    url = LOGIN_ACCOUNT;
                    break;
                case "SearchFoods":
                    url = SEARCH_FOODS;
                    break;
                case "Log out":
                    url = LOG_OUT;
                    break;
                case "Form Create Food":
                    url = FORM_CREATE_FOOD;
                    break;
                case "CreateNewFood":
                    url = CREATE_FOOD;
                    break;
                case "Manage Foods":
                    url = MANAGE_FOOD;
                    break;
                case "ViewFoodDetails":
                    url = VIEW_FOOD_DETAIL_BY_USER;
                    break;
                case "AddFoodToCart":
                    url = ADD_FOOD_TO_CART;
                    break;
                case "ViewCart":
                    url = VIEW_CART;
                    break;
                case "RemoveFoodFromCart":
                    url = REMOVE_FOOD_FROM_CART;
                    break;
                case "UpdateFoodFromCart":
                    url = UPDATE_FOOD_FROM_CART;
                    break;
                case "RemoveFoodByAdmin":
                    url = REMOVE_FOOD_ADMIN;
                    break;
                case "FormUpdateFood":
                    url = FORM_UPDATE_FOOD;
                    break;
                case "ProceedWithPayment":
                    String checkTypePayment = request.getParameter("checkRadio");
                    if (checkTypePayment.equals("Cash Payment")) {
                        url = PAYMENT_WITH_CASH;
                    } else {
                        url = PAYMENT_WITH_PAYPAL;
                    }
                    break;
                case "ViewHistoryOrder":
                    url = HISTORY_ORDER;
                    break;
                case "ViewOrderDetail":
                    url = VIEW_ORDER_DETAIL;
                    break;
                default:
                    request.setAttribute("ERROR", "Your action is invalid!");
                    break;
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.getMessage());
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
