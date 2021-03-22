package blog.ricardocampos.security;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ParametrosGerarJwt {

    private final Map<String, Object> headerParams;
    private final Object body;
    private String audience;
    private String issuer;
    private String subject;
    private LocalDateTime expiration;
    private LocalDateTime issuedAt;
    private LocalDateTime notBefore;
    private byte[] chave;
    private AlgoritmoAssinatura algoritmoAssinatura;

    public ParametrosGerarJwt(Object body) {
        this.headerParams = new HashMap<>();
        this.headerParams.put("typ", "JWT");
        this.body = body;
        this.algoritmoAssinatura = AlgoritmoAssinatura.HMAC_SHA256;
    }

    public void addHeaderParam(String name, Object value) {
        headerParams.put(name, value);
    }

    public Map<String, Object> getHeaderParams() {
        return headerParams;
    }

    public Object getBody() {
        return body;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(LocalDateTime notBefore) {
        this.notBefore = notBefore;
    }

    public void setChave(byte[] chave) {
        this.chave = chave;
    }

    public byte[] getChave() {
        return chave;
    }

    public void setAlgoritmoAssinatura(AlgoritmoAssinatura algoritmoAssinatura) {
        this.algoritmoAssinatura = algoritmoAssinatura;
    }

    public AlgoritmoAssinatura getAlgoritmoAssinatura() {
        return algoritmoAssinatura;
    }

}
