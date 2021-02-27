package com.kuang.servlet.user;

import com.kuang.pojo.User;
import com.alibaba.fastjson.JSONArray;
import com.kuang.service.user.UserServiceImpl;
import com.kuang.util.Contants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(method.equals("savepwd")) {
            updatePassword(req, resp);
        }else if(method.equals("pwdmodify"))
            pwdmodify(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void updatePassword(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getSession().getAttribute(Contants.USER_SSION);
        String newPassword = req.getParameter("newpassword");

        boolean flag = false;
        if (o != null && !StringUtils.isEmptyOrWhitespaceOnly(newPassword)){
            UserServiceImpl userService = new UserServiceImpl();
            flag = userService.updatePassword(((User)o).getId(),newPassword);
            if(flag){
                req.setAttribute("message","密码修改成功，请用新密码重新登录。");
                req.getSession().removeAttribute(Contants.USER_SSION);
            }else{
                req.setAttribute("message","密码修改失败");
            }
        }else{
            req.setAttribute("message","新密码有问题");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }

    protected void pwdmodify(HttpServletRequest req,HttpServletResponse resp){
        Object o=req.getSession().getAttribute(Contants.USER_SSION);
        String oldPassword = req.getParameter("oldpassword");
        Map<String,String> resultMap = new HashMap<String,String>();
        if(o==null){
            resultMap.put("result","sessionerror");
        }else if(StringUtils.isEmptyOrWhitespaceOnly(oldPassword)){
            resultMap.put("result","error");
        }else{
            String userPassword = ((User)o).getUserPassword();
            if(oldPassword.equals(userPassword))
                resultMap.put("result","true");
            else
                resultMap.put("result","false");
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
