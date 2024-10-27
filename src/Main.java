import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static int count;
    private static int max;

    public static void main(String[] args) throws Exception {
        Map<KeyClass, Integer> map = new HashMap<>();
        Class<?> clazz = map.getClass();
        Field table = clazz.getDeclaredField("table");
        table.setAccessible(true);

        for (int i = 0; i < 20_000_000; i++) {
            KeyClass keyClass = new KeyClass(i);
            map.put(keyClass, i);
        }

        Object[] o = (Object[]) table.get(map);

        System.out.print("[");

        for (Object node : o) {
            max = count > max ? count : max;
            count = 0;
            if (node == null) continue;
//            System.out.print("[");
            while (node != null) {
                Field nextField;
                try {
                    nextField = node.getClass().getDeclaredField("next");
//                    System.out.print(node);
                    count++;
                    nextField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    printTree(node);
                    break;
                }
                nextField.setAccessible(true);

                node = nextField.get(node);
                if (node != null) {
//                    System.out.print(", ");
                }
            }
//            System.out.print("]" + count);

        }
        System.out.println("]");
        System.out.println(max);
        System.out.println(o.length);
    }

    private static void printTree(Object node) throws Exception {
        if (node == null) return;
        count++;
        System.out.print(node + " ");
        Field left = node.getClass().getDeclaredField("left");
        left.setAccessible(true);
        printTree(left.get(node));
        Field right = node.getClass().getDeclaredField("right");
        right.setAccessible(true);
        printTree(right.get(node));
    }

    static class KeyClass {
        private final int data;

        public KeyClass(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "" + data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            KeyClass keyClass = (KeyClass) o;
            return data == keyClass.data;
        }

        public int hashCode1() {
            return data % 10;
        }
    }
}