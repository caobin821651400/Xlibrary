package java;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2021/1/4 23:10
 * @Desc :
 * ====================================================
 */
public class InjectUtils {

    /**
     * ====================================================
     *
     * @User :caobin
     * @Date :2021/1/4 23:10
     * @Desc :
     * ====================================================
     */
    public static void bindView(Activity activity) {
        try {
            Class<? extends Activity> aClass = activity.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            if (declaredFields.length == 0) return;
            for (Field field : declaredFields) {
                //表明有BindView注解
                if (field.isAnnotationPresent(BindView.class)) {
                    BindView bindView = field.getAnnotation(BindView.class);
                    if (bindView == null) return;
                    int id = bindView.value();
                    View view = activity.findViewById(id);
                    //在给activity中的tv复制，private的要设置作用域
                    field.setAccessible(true);
                    field.set(activity, view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}