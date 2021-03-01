package com.kuang.servlet.user;

import com.kuang.pojo.User;
import com.kuang.util.Contants;
import com.kuang.service.user.UserService;
import com.kuang.service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet---start......");
        String userCode = req.getParameter("userCode");
        String password = req.getParameter("userPassword");

        //此外体现了面向接口的思想，声明变量时如下
        //接口名称 变量名 = new 实现了接口的类名称()
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode,password);
        if (user!=null){
            req.getSession().setAttribute(Contants.USER_SSION, user);
            //使用重定向转到登录页面
            resp.sendRedirect("jsp/frame.jsp");
        }else{
            req.setAttribute("error","用户名或密码错误");
            //这里用转发而不是用重定向，是因为前端是调用request里的error变量
            //转发可以将请求转发出去，保证了error的存活
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
