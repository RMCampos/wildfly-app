package blog.ricardocampos.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtil {

    private JsonUtil() {
    }

    public static String converterParaJson(Object o) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        String json = objectMapper.writeValueAsString(o);
        return json;
    }

    public static <K> K converterParaObjeto(String json, Class<K> classe) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        K obj = objectMapper.readValue(json, classe);
        return obj;
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }
}
