package blog.ricardocampos.security;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtTokenExpiradoException extends Exception {

    private static final long serialVersionUID = -2256151994736135814L;

    public JwtTokenExpiradoException(ExpiredJwtException e) {
        super("Token expirado. " + e.getMessage(), e);
    }

}
