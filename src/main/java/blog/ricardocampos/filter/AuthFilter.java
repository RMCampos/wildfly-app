package blog.ricardocampos.filter;

import blog.ricardocampos.security.JwtUtil;
import io.jsonwebtoken.Claims;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
public class AuthFilter implements Filter {

    @Inject
    JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

            // Get the HTTP Authorization header from the request
            String authorizationHeader = ((HttpServletRequest) servletRequest).getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null) {
                httpServletResponse.setStatus(401);
                return;
            }

            // Extract the token from the HTTP Authorization header
            Claims claims = jwtUtil.decodeToken(authorizationHeader);
            if (claims == null) {
                // TODO: create a failed token cache
                httpServletResponse.setStatus(401);
                return;
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
