import java.lang.reflect.Field;

public class Debug {

    public static void fields(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldType = field.getType().getName();

            try {
                Object fieldValue = field.get(obj);
                System.out.println("Pole: " + fieldName + " => " + fieldType + ", " + fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Point p = new Point(3, 4, "test");
        Debug.fields(p);
    }
}

class Point {
    private int x;
    private int y;
    private String a;

    public Point(int x, int y, String a) {
        this.x = x;
        this.y = y;
        this.a = a;
    }
}
