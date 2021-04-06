/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.ActionDAO;
import danhpv.daos.FoodDAO;
import danhpv.daos.RoleDAO;
import danhpv.dtos.Action;
import danhpv.dtos.Food;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class RemoveFoodByAdminController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ViewFoodsByAdminController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            if (user != null) {
                RoleDAO roleDao = new RoleDAO();
                Role checkRole;
                String roleId = user.getRoleId();
                checkRole = roleDao.getRoleByRoleId(roleId);
                if (checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                    String foodId = request.getParameter("foodId");
                    FoodDAO foodDao = new FoodDAO();
                    Food food = foodDao.getFoodByFoodId(foodId);
                    if (food == null) {
                        request.setAttribute("ERROR", "Can not find food by food id!");
                    } else {
                        ActionDAO actionDao = new ActionDAO();
                        String actionId = actionDao.getActionIdWithLastAction();
                        int count;
                        if (actionId == null) {
                            count = 1;
                            actionId = "ACTION-" + String.format("%05d", count);
                        } else {
                            String[] split = actionId.split("-");
                            try {
                                count = Integer.parseInt(split[1]);
                                count += 1;
                                actionId = split[0] + "-" + String.format("%05d", count);
                            } catch (NumberFormatException e) {
                            }
                        }
                        food.setStatus(false);
                        if (actionId != null) {
                            boolean checkSuccess = foodDao.removeFood(food);
                            if (checkSuccess) {
                                Action action = new Action(actionId, foodId, user.getUserId(), "DELETED", new Date(), true);
                                actionDao.addNewAction(action);
                                url = SUCCESS;
                            } else {
                                request.setAttribute("ERROR", "Remove food by food id failed!");
                            }
                        } else {
                            request.setAttribute("ERROR", "Remove food by food id failed!");
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "Your role is invalid!");
                    url = ERROR;
                }
            } else {
                request.setAttribute("ERROR", "Required login before remove food!");
            }
        } catch (Exception e) {
            log("Error at RemoveFoodByAdminController: " + e.getMessage());
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
