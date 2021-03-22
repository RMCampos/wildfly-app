package blog.ricardocampos.security;

import io.jsonwebtoken.SignatureAlgorithm;

public enum AlgoritmoAssinatura {
    HMAC_SHA256(SignatureAlgorithm.HS256),
    HMAC_SHA384(SignatureAlgorithm.HS384),
    HMAC_SHA512(SignatureAlgorithm.HS512);

    private final SignatureAlgorithm algorithm;

    AlgoritmoAssinatura(SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

}
