/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.OrderDAO;
import danhpv.daos.RoleDAO;
import danhpv.dtos.Order;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class ViewHistoryOrderController extends HttpServlet {

    private static final String INVALID = "error.jsp";
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewHistoryOrder.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            OrderDAO orderDao = new OrderDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            String txtSearchByFoodName;
            txtSearchByFoodName = request.getParameter("txtSearchByFoodName");
            if (txtSearchByFoodName == null) {
                txtSearchByFoodName = "";
            }
            String txtSearchDate = request.getParameter("txtSearchByDate");
            String nextDate = null;
            if (txtSearchDate != null) {
                if (txtSearchDate.length() != 0) {
                    try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(txtSearchDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    nextDate = format.format(calendar.getTime());
                    }catch(ParseException e) {
                        url = INVALID;
                        request.setAttribute("ERROR", "Date is invalid! Required selec date valid.");
                    }
                } else {
                    txtSearchDate = null;
                }
            }
            if (user != null) {
                RoleDAO roleDao = new RoleDAO();
                Role role = roleDao.getRoleByRoleId(user.getRoleId());
                switch (role.getRoleName()) {
                    case "Admin":
                        {
                            List<Order> listOrders = orderDao.viewListHistoryOrdersOfUserByAdmin(txtSearchByFoodName, txtSearchDate, nextDate);
                            request.setAttribute("LIST_HISTORY_ORDERS", listOrders);
                            url = SUCCESS;
                            break;
                        }
                    case "User":
                        {
                            List<Order> listOrders = orderDao.viewListHistoryOrdersByUserId(user, txtSearchByFoodName, txtSearchDate, nextDate);
                            request.setAttribute("LIST_HISTORY_ORDERS", listOrders);
                            url = SUCCESS;
                            break;
                        }
                    default:
                        url = ERROR;
                        request.setAttribute("ERROR", "Your role is invalid!");
                        break;
                }
            } else {
                url = ERROR;
                request.setAttribute("ERROR", "Required login account before view history order!");
            }
        } catch (Exception e) {
            log("Error at ViewHistoryOrderController: " + e.getMessage());
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
