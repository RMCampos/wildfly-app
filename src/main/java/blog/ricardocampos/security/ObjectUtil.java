package blog.ricardocampos.security;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class ObjectUtil {

    public static XmlAdapter obterXmlAdapter(Class<?> aClass) {
        XmlAdapter adapter = null;
        XmlJavaTypeAdapter xmlJavaTypeAdapter = aClass.getAnnotation(XmlJavaTypeAdapter.class);
        if (xmlJavaTypeAdapter != null) {
            Class<? extends XmlAdapter> xmlAdapterClass = xmlJavaTypeAdapter.value();
            try {
                adapter = xmlAdapterClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adapter;
    }

}
