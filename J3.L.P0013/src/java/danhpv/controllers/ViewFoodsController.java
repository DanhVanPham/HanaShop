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
public class ViewFoodsController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS_USER = "viewFoods.jsp";

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
            FoodDAO foodDao = new FoodDAO();
            RoleDAO roleDao = new RoleDAO();
            Role checkRole = null;
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            if (user != null) {
                String roleId = user.getRoleId();
                checkRole = roleDao.getRoleByRoleId(roleId);
            }
            if (checkRole != null && checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                request.setAttribute("ERROR", "Your role is invalid!");
                url = ERROR;
            } else {
                int index = 1;
                String getIndex = request.getParameter("pageIndex");
                if (getIndex != null) {
                    try {
                        index = Integer.parseInt(getIndex);
                    } catch (NumberFormatException e) {
                        index = 1;
                    }
                }
                boolean valid = true;
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
                    long countAll = foodDao.getCountAllFoodsByUser();
                    int count = (int) countAll;
                    if (count != 0) {
                        int pageSize = 8;
                        int endPage;
                        endPage = count / pageSize;
                        if (count % pageSize != 0) {
                            endPage++;
                        }
                        listPaging = foodDao.getListFoodsWithPagningByUser((index - 1) * pageSize, pageSize);
                        request.setAttribute("ENDPAGE", endPage);
                    }
                } else {
                    boolean checkPrice = true;
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
                        long countAll = foodDao.getCountAllFoodsBySearchWithUser(searchByName, category, txtMoneyFrom, txtMoneyTo);
                        int count = (int) countAll;
                        if (count != 0) {
                            int pageSize = 8;
                            int endPage;
                            endPage = count / pageSize;
                            if (count % pageSize != 0) {
                                endPage++;
                            }
                            listPaging = foodDao.getPagingBySearchWithUser(searchByName, category, txtMoneyFrom, txtMoneyTo, (index - 1) * pageSize, pageSize);
                            request.setAttribute("ENDPAGE", endPage);
                        }
                    } else {
                        request.setAttribute("ERROR", "Required input number of money valid!");
                        url = ERROR;
                    }
                }
                if (valid) {
                    request.setAttribute("LISTFOODS", listPaging);
                    url = SUCCESS_USER;
                }
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Something is wrong. Can not get list foods!");
            log("Error at ViewFoodsController: " + e.getMessage());
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
