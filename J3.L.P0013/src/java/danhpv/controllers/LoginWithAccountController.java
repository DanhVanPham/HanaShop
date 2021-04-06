/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.FoodDAO;
import danhpv.daos.RoleDAO;
import danhpv.daos.UserDAO;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Food;
import danhpv.dtos.LoginErrorObj;
import danhpv.dtos.Role;
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
public class LoginWithAccountController extends HttpServlet {

    private static final String INVALID = "login.jsp";
    private static final String SUCCESS_USER = "ViewFoodsController";
    private static final String SUCCESS_ADMIN = "ViewFoodsByAdminController";
    private static final String CART_USER = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID;
        try {
            String userName = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            LoginErrorObj error = new LoginErrorObj();
            boolean valid = true;
            if (userName.length() == 0) {
                valid = false;
                error.setUsernameError("Username is not null!");
            }
            if (password.length() == 0) {
                valid = false;
                error.setPasswordError("Password is not null!");
            }
            if (valid) {
                UserDAO userDao = new UserDAO();
                RoleDAO roleDao = new RoleDAO();
                User userDTO = userDao.checkLogin(userName, password);
                if (userDTO != null) {
                    if (userDTO.getStatus() == 0) {
                        request.setAttribute("ERROR", "Your account have been blocked!");
                    } else {
                        Role role = roleDao.getRoleByRoleId(userDTO.getRoleId());
                        if (role != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("USER", userDTO);
                            if (role.getRoleName().equalsIgnoreCase("Admin")) {
                                url = SUCCESS_ADMIN;
                            } else if (role.getRoleName().equalsIgnoreCase("User")) {
                                CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
                                if (cartShopping != null) {
                                    FoodDAO foodDao = new FoodDAO();
                                    List<Food> listFoodFavourite = foodDao.getListFoodFavouritesByUserId(userDTO);
                                    if (listFoodFavourite != null) {
                                        request.setAttribute("LIST_FAVORITE", listFoodFavourite);
                                    }
                                    url = CART_USER;
                                } else {
                                    url = SUCCESS_USER;
                                }
                            } else {
                                request.setAttribute("ERROR", "Your role is invalid!");
                            }
                        } else {
                            request.setAttribute("ERROR", "Your role is invalid!");
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "Username or password is not correct!");
                }
            } else {
                request.setAttribute("INVALID", error);
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Username or password is not correct!");
            log("Error at LoginWithAccountController: " + e.getMessage());
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
