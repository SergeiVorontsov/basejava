import com.basejava.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("");
        Method method = resume.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));
    }
}