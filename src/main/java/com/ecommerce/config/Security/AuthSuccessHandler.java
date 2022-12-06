package com.ecommerce.config.Security;

import com.ecommerce.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        HttpSession session= request.getSession();
        User authUser= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

     //  User authUser= (User) authentication.getPrincipal();
       /* Authentication authUser= SecurityContextHolder.getContext().getAuthentication();*/

        session.setAttribute("user", authUser);
        session.setAttribute("email",authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        response.setStatus(HttpServletResponse.SC_OK);

        SavedRequest savedRequest=new HttpSessionRequestCache().getRequest(request,response);
        response.sendRedirect(savedRequest == null ? "/index" : savedRequest.getRedirectUrl());
    }
}
