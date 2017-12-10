/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.AccountService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.RequestContext;

/**
 *
 * @author 721292
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //when getting the page check the uuid value, determind which page to go
        String uuid = request.getParameter("uuid");
        String url = "/WEB-INF/reset.jsp";
        
        if(uuid != null){
            request.setAttribute("uuid", uuid);
            url="/WEB-INF/resetnewpassword.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = request.getRequestURL().toString();
        String action = request.getParameter("action");
        String path = getServletContext().getRealPath("/WEB-INF");
        AccountService as = new AccountService();
        String insiteurl = "/WEB-INF/login.jsp";
        if(action !=null && action.equals("passEmail"))
        {
            String email = request.getParameter("passResetEmail");
            boolean res = as.resetPassword(email, path, url);
            if(res==false)
            {
                request.setAttribute("message", "user does not exist!");
            }
            else
            {
                request.setAttribute("message", "Check your email!");
            }
            insiteurl = "/WEB-INF/reset.jsp";
        }
        if(action !=null && action.equals("setnewpassword"))
        {
            String newpassword = request.getParameter("newpassword");
            String uuid=request.getParameter("uuid");
            try{
                as.changePassword(uuid, newpassword);
                request.setAttribute("message", "reset password succefully!");
            }catch (Exception ex)
            {
                request.setAttribute("message", "Invalid operation!");
            }
            insiteurl = "/WEB-INF/resetNewPassword.jsp";
        }
        getServletContext().getRequestDispatcher(insiteurl).forward(request, response);
    }

}
