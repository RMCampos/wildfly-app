package blog.ricardocampos.filter;

import blog.ricardocampos.security.MyToken;
import blog.ricardocampos.security.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean temPermissao = false;
        HttpServletRequest httpServletRequest = null;
        HttpServletResponse httpServletResponse = null;

        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            httpServletRequest = (HttpServletRequest) servletRequest;
            httpServletResponse = (HttpServletResponse) servletResponse;

            User user = MyToken.getUser(httpServletRequest);
            if (user != null) {
                temPermissao = true;
            }

            if (temPermissao) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.html");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
