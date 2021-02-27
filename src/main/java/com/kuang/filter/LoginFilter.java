package com.kuang.filter;

import com.kuang.pojo.User;
import com.kuang.util.Contants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        User user = (User)request.getSession().getAttribute(Contants.USER_SSION);
        if(user == null){
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }else{
            chain.doFilter(req,resp);
        }

    }

    public void destroy() {

    }
}
