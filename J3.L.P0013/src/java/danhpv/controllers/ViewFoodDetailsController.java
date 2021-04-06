/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.FoodDAO;
import danhpv.dtos.Food;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class ViewFoodDetailsController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS_USER = "viewFoodDetails.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            FoodDAO foodDao = new FoodDAO();
            String foodId = request.getParameter("foodId");
            if (foodId == null) {
                request.setAttribute("ERROR", "Something is wrong");
            } else {
                Food food = foodDao.getFoodByFoodId(foodId);
                if (food != null) {
                    request.setAttribute("FOOD_DETAIL", food);
                    List<Food> listFoods = foodDao.getListFoodsRelatedByFoodId(food);
                    if (listFoods != null && !listFoods.isEmpty()) {
                        request.setAttribute("LIST_RELATED", listFoods);
                    }
                    url = SUCCESS_USER;
                } else {
                    request.setAttribute("ERROR", "Can not find any list food?");
                }
            }
        } catch (Exception e) {
            log("Error at ViewFoodDetailsController: " + e.getMessage());
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
