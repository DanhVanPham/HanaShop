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
import danhpv.dtos.FoodError;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author DELL
 */
public class UpdateFoodFromAdminController extends HttpServlet {

    private static final String ERROR = "formUpdateFood.jsp";
    private static final String SUCCESS = "ViewFoodsByAdminController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
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
                    boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
                    if (isMultiPart) {
                        FileItemFactory factory = new DiskFileItemFactory();
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        List items = null;
                        try {
                            items = upload.parseRequest(request);
                        } catch (FileUploadException e) {
                            e.printStackTrace();
                        }
                        Iterator iterator = items.iterator();
                        Hashtable params = new Hashtable();
                        String fileName = null;
                        while (iterator.hasNext()) {
                            FileItem item = (FileItem) iterator.next();
                            if (item.isFormField()) {
                                params.put(item.getFieldName(), item.getString());
                            } else {
                                try {
                                    String itemName = item.getName();
                                    fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                                    String realPath = getServletContext().getRealPath("/") + "images\\" + fileName;
                                    File savedFile = new File(realPath);
                                    item.write(savedFile);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        String foodId = (String) params.get("foodId");
                        FoodDAO foodDao = new FoodDAO();
                        Food checkFoodExisted = foodDao.getFoodByFoodId(foodId);
                        if (checkFoodExisted != null) {
                            if (fileName.isEmpty()) {
                                fileName = (String) params.get("txtImageUrl");
                            }
                            String txtFoodName = (String) params.get("txtFoodName");
                            String txtFoodDescription = (String) params.get("txtFoodDescription");
                            String txtQuantity = (String) params.get("txtQuantity");
                            int quantity = 0;
                            String txtPrice = (String) params.get("txtPrice");
                            float price = 0;
                            String category = (String) params.get("categories");

                            FoodError foodError = new FoodError();
                            boolean valid = true;
                            if (txtFoodName.trim() == null || txtFoodName.trim().length() == 0) {
                                valid = false;
                                foodError.setFoodNameError("Food name is not null!");
                            } else {
                                if (txtFoodName.trim().length() > 50) {
                                    valid = false;
                                    foodError.setFoodNameError("Required length of foodName smaller or equal 50!");
                                }
                            }

                            if (txtFoodDescription.trim() == null || txtFoodDescription.trim().length() == 0) {
                                valid = false;
                                foodError.setFoodDescriptionError("Description is not null!");
                            } else {
                                if (txtFoodDescription.trim().length() > 100) {
                                    valid = false;
                                    foodError.setFoodDescriptionError("Rquired length of food description smaller or equal 100!");
                                }
                            }

                            if (txtQuantity == null || txtQuantity.length() == 0) {
                                valid = false;
                                foodError.setFoodQuantityError("Quantity is not null");
                            } else {
                                try {
                                    quantity = Integer.parseInt(txtQuantity);
                                    if (quantity <= 0) {
                                        valid = false;
                                        foodError.setFoodQuantityError("Quantity mus be number greater than 0!");
                                    }
                                } catch (NumberFormatException e) {
                                    valid = false;
                                    foodError.setFoodQuantityError("Quantity mus be number greater than 0!");
                                }
                            }

                            if (txtPrice == null || txtPrice.length() == 0) {
                                valid = false;
                                foodError.setFoodPriceError("Price is not null!");
                            } else {
                                try {
                                    price = Float.parseFloat(txtPrice);
                                    if (price <= 0) {
                                        valid = false;
                                        foodError.setFoodPriceError("Price must be number greater than 0!");
                                    }
                                } catch (NumberFormatException e) {
                                    valid = false;
                                    foodError.setFoodPriceError("Price must be number greater than 0!");
                                }
                            }
                            if (category == null || category.length() == 0) {
                                valid = false;
                                foodError.setCategoryError("Required select the category!");
                            }
                            if (valid) {
                                txtFoodName = new String(txtFoodName.getBytes("iso-8859-1"), "UTF-8");
                                txtFoodDescription = new String(txtFoodDescription.getBytes("iso-8859-1"), "UTF-8");
                                ActionDAO actionDao = new ActionDAO();
                                checkFoodExisted.setCategoryId(category);
                                checkFoodExisted.setFoodDescription(txtFoodDescription);
                                checkFoodExisted.setFoodName(txtFoodName);
                                checkFoodExisted.setFoodPrice(price);
                                checkFoodExisted.setFoodQuantity(quantity);
                                checkFoodExisted.setImageUrl(fileName);
                                boolean check = foodDao.updateFoodByFoodExisted(checkFoodExisted);
                                int count;
                                String actionId = actionDao.getActionIdWithLastAction();
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
                                        actionId = null;
                                    }
                                }
                                if (check) {
                                    Action action = new Action(actionId, foodId, user.getUserId(), "UPDATED", new Date(), true);
                                    actionDao.addNewAction(action);
                                    url = SUCCESS;
                                } else {
                                    request.setAttribute("ERROR", "Create food is failed!");
                                    url = ERROR;
                                }
                            } else {
                                request.setAttribute("INVALID", foodError);
                            }
                        } else {
                            request.setAttribute("ERROR", "Can not find food by food id!");
                        }
                    } else {
                        request.setAttribute("ERROR", "Some thing is wrong!");
                        url = ERROR;
                    }
                } else {
                    request.setAttribute("ERROR", "Your role is invalid!");
                    url = ERROR;
                }
            } else {
                request.setAttribute("ERROR", "Required login before update!");
            }
        } catch (Exception e) {
            log("Error at UpdateFoodFromAdminController: " + e.getMessage());
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
