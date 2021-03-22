package blog.ricardocampos.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.NewCookie;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyToken {

    private static final Logger logger = Logger.getLogger(MyToken.class.getName());

    private static final String COOKIE_NAME = "JRICTOKEN";
    private static final byte[] CHAVE_JWT = {'0', 'k', '8', 'u', '4', 'g', '9', 'e', '7', 'l', '1', '9', 'K', '2', 'U',
            '3', 'G', '7', 'E', '8', '8', '#', ' ', 'L', 'X', 'A', '%', '$', '1', '2', '9', '8'};

    public static User getUser(HttpServletRequest request) {
        String token = getToken(request);
        User user = null;
        if (token != null && !token.isEmpty()) {
            try {
                user = Jwt.parse(User.class, token, CHAVE_JWT);
            } catch (JwtTokenExpiradoException e) {
                logger.info(e.getMessage());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao obter usuário do token", e);
            }
        }
        return user;
    }

    private static String getToken(HttpServletRequest request) {
        String token = "";
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(COOKIE_NAME)) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        return token;
    }

    public static NewCookie criarTokenCookie(HttpServletRequest httpServletRequest, User user) {
        String path = httpServletRequest.getContextPath();
        ParametrosGerarJwt params = new ParametrosGerarJwt(user);
        params.setExpiration(LocalDateTime.now().plusHours(2));
        params.setNotBefore(LocalDateTime.now().plusHours(-1));
        params.setChave(CHAVE_JWT);
        String token = Jwt.gerar(params);
        return new NewCookie(COOKIE_NAME, token, path, null, null, NewCookie.DEFAULT_MAX_AGE, false);
    }

}
