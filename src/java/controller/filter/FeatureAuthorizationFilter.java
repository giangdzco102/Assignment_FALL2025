/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.filter;

/**
 *
 * @author ASUS
 */
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FeatureAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("auth") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<String> featureURLs = (List<String>) session.getAttribute("featureURLs");
        String path = req.getServletPath();

        if (featureURLs != null && featureURLs.contains(path)) {
            chain.doFilter(request, response); // có quyền
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập!");
        }
    }
}
