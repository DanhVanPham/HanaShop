/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.FoodDAO;
import danhpv.daos.RoleDAO;
import danhpv.dtos.Food;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import java.io.IOException;
import java.util.ArrayList;
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
public class ViewFoodsByAdminController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS_ADMIN = "manageFoods.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String searchByName = request.getParameter("txtSearchByName");
            String category = request.getParameter("checkRadioCategory");
            String txtMoneyFrom = request.getParameter("txtSearchMoneyFrom");
            String txtMoneyTo = request.getParameter("txtSearchMoneyTo");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            RoleDAO roleDao = new RoleDAO();
            FoodDAO foodDao = new FoodDAO();
            Role checkRole = null;

            if (user != null) {
                String roleId = user.getRoleId();
                checkRole = roleDao.getRoleByRoleId(roleId);
            }
            int index = 1;
            String getIndex = request.getParameter("pageIndex");
            if (getIndex != null) {
                try {
                    index = Integer.parseInt(getIndex);
                } catch (NumberFormatException e) {
                    index = 1;
                }
            }
            List<Food> listPaging = null;
            if (category != null && category.length() != 0) {
                if (category.equals("All")) {
                    category = null;
                }
            } else {
                if (category != null && category.length() == 0) {
                    category = null;
                }
            }
            if (((searchByName == null) || searchByName.length() == 0) && (txtMoneyFrom == null || txtMoneyFrom.isEmpty())
                    && (txtMoneyTo == null || txtMoneyTo.isEmpty()) && category == null) {
                //tìm số lượng cần phân trang
                long countAll = foodDao.getCountAllFoodsByAdmin();
                int count = (int) countAll;
                if (count == 0) {
                    listPaging = new ArrayList<>();
                } else {
                    int pageSize = 8;
                    int endPage;
                    endPage = count / pageSize;
                    if (count % pageSize != 0) {
                        endPage++;
                    }
                    listPaging = foodDao.getListFoodsWithPagningByAdmin((index - 1) * pageSize, pageSize);
                    request.setAttribute("ENDPAGE", endPage);
                }
            } else {
                boolean checkPrice = true, valid = true;
                if (searchByName != null) {
                    if (searchByName.length() == 0) {
                        searchByName = null;
                    }
                }
                if (txtMoneyFrom != null) {
                    if (txtMoneyFrom.length() == 0) {
                        txtMoneyFrom = null;
                        checkPrice = false;
                    }
                }
                if (txtMoneyTo != null) {
                    if (txtMoneyTo.length() == 0) {
                        txtMoneyTo = null;
                        checkPrice = false;
                    }
                }
                if (checkPrice) {
                    try {
                        Float.parseFloat(txtMoneyFrom);
                        Float.parseFloat(txtMoneyTo);
                    } catch (NumberFormatException e) {
                        valid = false;
                        request.setAttribute("ERROR", "Required input number of money valid!");
                    }
                }
                if (valid) {
                    long countAll = foodDao.getCountAllFoodsBySearchWithAdmin(searchByName, category, txtMoneyFrom, txtMoneyTo);
                    int count = (int) countAll;
                    if (count == 0) {
                        listPaging = new ArrayList<>();
                    } else {
                        int pageSize = 8;
                        int endPage;
                        endPage = count / pageSize;
                        if (count % pageSize != 0) {
                            endPage++;
                        }
                        listPaging = foodDao.getPagingBySearchWithAdmin(searchByName, category,
                                txtMoneyFrom, txtMoneyTo, (index - 1) * pageSize, pageSize);
                        request.setAttribute("ENDPAGE", endPage);
                    }
                } else {
                    url = ERROR;
                }
            }
            if (user != null && checkRole != null) {
                if (checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                    url = SUCCESS_ADMIN;
                    request.setAttribute("LISTFOODS", listPaging);
                } else {
                    request.setAttribute("ERROR", "Your role is invalid!");
                    url = ERROR;
                }
            } else {
                request.setAttribute("ERROR", "Your role is invalid!");
                url = ERROR;
            }
        } catch (Exception e) {
            log("Error at ViewFoodsByAdminController: " + e.getMessage());
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
