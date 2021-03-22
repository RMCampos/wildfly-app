package blog.ricardocampos.security;

import blog.ricardocampos.exception.GeneralException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class Jwt {

    private static final byte[] CHAVE_PADRAO = {'@', 'K', '-', 'u', '-', 'G', '-', 'e', '-', 'L', '-', 'k', '6', 't',
            'x', ' ', 'K', '-', 'u', '-', 'G', '-', 'e', '-', 'L', '-', 'k', '6', 't', 'x', '@', '!'};

    private Jwt() {
    }

    public static String gerar(ParametrosGerarJwt params) {
        Map<String, Object> valsMap = obterValuesMap(params);

        JwtBuilder jwtBuilder = Jwts.builder();

        jwtBuilder.setClaims(valsMap);

        if (params.getHeaderParams() != null) {
            for (Map.Entry<String, Object> e : params.getHeaderParams().entrySet()) {
                jwtBuilder.setHeaderParam(e.getKey(), e.getValue());
            }
        }
        if (params.getAudience() != null && !params.getAudience().isEmpty()) {
            jwtBuilder.setAudience(params.getAudience());
        }
        if (params.getIssuer() != null && !params.getIssuer().isEmpty()) {
            jwtBuilder.setIssuer(params.getIssuer());
        }
        if (params.getSubject() != null && !params.getSubject().isEmpty()) {
            jwtBuilder.setSubject(params.getSubject());
        }
        if (params.getIssuedAt() != null) {
            Instant instant = params.getIssuedAt().atZone(ZoneId.systemDefault()).toInstant();
            jwtBuilder.setIssuedAt(Date.from(instant));
        }
        if (params.getNotBefore() != null) {
            Instant instant = params.getNotBefore().atZone(ZoneId.systemDefault()).toInstant();
            jwtBuilder.setNotBefore(Date.from(instant));
        }
        if (params.getExpiration() != null) {
            Instant instant = params.getNotBefore().atZone(ZoneId.systemDefault()).toInstant();
            jwtBuilder.setExpiration(Date.from(instant));
        }

        byte[] chave = params.getChave();
        if (chave == null || chave.length <= 0) {
            chave = CHAVE_PADRAO;
        }

        SignatureAlgorithm algorithm = (params.getAlgoritmoAssinatura() == null)
                ? SignatureAlgorithm.HS256
                : params.getAlgoritmoAssinatura().getAlgorithm();

        jwtBuilder.signWith(Keys.hmacShaKeyFor(chave), algorithm);
        return jwtBuilder.compact();
    }

    private static Map<String, Object> obterValuesMap(ParametrosGerarJwt params) {
        Map<String, Object> valsMap;
        if (params.getBody() instanceof Map) {
            valsMap = (Map) params.getBody();
        } else {
            valsMap = new LinkedHashMap<>();
            List<Field> fields = Arrays.asList(params.getBody().getClass().getDeclaredFields());
            fields.sort(Comparator.comparing(Field::getName));
            for (Field field : fields) {
                field.setAccessible(true);
                Object val = null;
                try {
                    val = field.get(params.getBody());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (val != null) {
                    String name = field.getName();
                    if (!name.contains("$")) {
                        XmlAdapter xmlAdapter = ObjectUtil.obterXmlAdapter(val.getClass());
                        if (xmlAdapter != null) {
                            try {
                                valsMap.put(name, xmlAdapter.marshal(val));
                            } catch (Exception e) {
                                throw new GeneralException(e.getMessage(), e);
                            }
                        } else if (val instanceof Boolean ||
                                val instanceof BigDecimal ||
                                val instanceof Date ||
                                val instanceof Double ||
                                val instanceof Integer ||
                                val instanceof Long ||
                                val instanceof String ||
                                val instanceof Character ||
                                val instanceof Collection) {
                            valsMap.put(name, val);
                        } else {
                            throw new GeneralException("Classe não tratada: " + val.getClass().getName() + " para o campo " + name);
                        }
                    }
                }
            }
        }
        return valsMap;
    }

    public static <T> T parse(Class<T> classe, String jwt) throws JwtTokenExpiradoException {
        return parse(classe, jwt, CHAVE_PADRAO);
    }

    public static <T> T parse(Class<T> classe, String jwt, byte[] chave) throws JwtTokenExpiradoException {
        T t;
        try {
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();
            jwtParserBuilder.setSigningKey(chave);
            Jws<Claims> claimsJws = jwtParserBuilder.build().parseClaimsJws(jwt);
            String body = JsonUtil.converterParaJson(claimsJws.getBody());
            t = JsonUtil.converterParaObjeto(body, classe);
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiradoException(e);
        } catch (SignatureException e) {
            throw new GeneralException("JWT inválido.", e);
        } catch (Exception e) {
            throw new GeneralException("Erro no parse do JWT.", e);
        }
        return t;
    }
}
