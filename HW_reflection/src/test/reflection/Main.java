package test.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

    static class A {
        private int v1;
        private Integer v2;

        public A(int v1, Integer v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public int getV1() {
            return v1;
        }

        public Integer getV2() {
            return v2;
        }

        public void setV1(int v1) {
            this.v1 = v1;
        }

        public void setV2(Integer v2) {
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "  v1=" + v1 + "  v2=" + v2;
        }
    }

    static class AA {
        private Number v2;

        public AA(Number v2) {
            this.v2 = v2;
        }

        public void setV2(Number v2) {
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "  v2=" + v2;
        }
    }

    static class B extends A {
        private Byte v3;

        public B(Byte v3) {
            super(0, 0);
            this.v3 = v3;
        }

        public Byte getV3() {
            return v3;
        }

        public void setV3(Byte v3) {
            this.v3 = v3;
        }

        @Override
        public String toString() {
            return super.toString() + "  v3=" + v3;
        }
    }


    public static void main(String[] args) {
        // write your code here
        Main.A a = new A(5, 5);
        Main.B b = new B((byte) 1);
        System.out.println(Main.BeanUtils.getGetter(a.getClass()));
        System.out.println(Main.BeanUtils.getGetter(b.getClass()));

        System.out.println("********************************************");
        System.out.println(Main.BeanUtils.getSetter(a.getClass()));
        System.out.println(Main.BeanUtils.getSetter(b.getClass()));

        System.out.println("********************************************");
        System.out.println("A: " + a);
        System.out.println("B: " + b);

        System.out.println("********************************************");
        System.out.println("BeanUtils.assert(to: A,from: B)");
        BeanUtils.assign(a, b);
        System.out.println("A: " + a);
        System.out.println("B: " + b);
        AA aa = new AA(1000);
        System.out.println("AA: " + aa);
        System.out.println("BeanUtils.assert(to: AA,from: B)");
        BeanUtils.assign(aa, b);
        System.out.println("AA: " + aa);
    }

    public static class BeanUtils {
        /*NOT FOR GENERIC*/

        /**
         * Scans object "from" for all getters. If object "to"
         * contains correspondent setter, it will invoke it
         * to set property value for "to" which equals to the property
         * of "from".
         * <p/>
         * The type in setter should be compatible to the value returned
         * by getter (if not, no invocation performed).
         * Compatible means that parameter type in setter should
         * be the same or be superclass of the return type of the getter.
         * <p/>
         * The method takes care only about public methods.
         *
         * @param to   Object which properties will be set.
         * @param from Object which properties will be used to get values.
         */
        public static void assign(Object to, Object from) {
            List<Method> listGetter = getGetter(from.getClass());
            List<Method> listSetter = getSetter(to.getClass());
            listGetter.forEach(mG -> {
                Optional<Method> opt = listSetter.stream().filter(mS -> mS.getName().substring(2).equalsIgnoreCase(mG.getName().substring(2)) && mS.getParameterTypes()[0].isAssignableFrom(mG.getReturnType())).findAny();
                if (!opt.isEmpty()) {
                    try {
                        opt.get().invoke(to, mG.invoke(from));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println(e.getStackTrace());
                    }
                }
            });
        }

        private static List<Method> getSetter(Object to) {
            return new ArrayList<Method>();
        }

        private static List<Method> getGetter(Class<?> from) {
            List<Method> arrayM = new ArrayList<Method>();
            List<Field> arrayF = Arrays.asList(from.getDeclaredFields());
            for (Method method : from.getMethods()) {
                if (method.getParameterCount() == 0
                        && !arrayF.stream().filter(f -> ("GET" + f.getName()).equalsIgnoreCase(method.getName()) && method.getReturnType() == f.getType()).findAny().isEmpty())
                    arrayM.add(method);
            }
            if (from.getSuperclass() != Object.class) {
                System.out.println(from.getSuperclass());
                arrayM.addAll(getGetter(from.getSuperclass()));
            }
            return arrayM;
        }

        private static List<Method> getSetter(Class<?> to) {
            List<Method> arrayM = new ArrayList<Method>();
            List<Field> arrayF = Arrays.asList(to.getDeclaredFields());
            for (Method method : to.getMethods()) {
                if (method.getParameterCount() == 1
                        && method.getReturnType() == void.class
                        && !arrayF.stream().filter(f -> ("SET" + f.getName()).equalsIgnoreCase(method.getName()) && method.getParameterTypes()[0] == f.getType()).findAny().isEmpty())
                    arrayM.add(method);
            }
            if (to.getSuperclass() != Object.class) {
                arrayM.addAll(getSetter(to.getSuperclass()));
            }
            return arrayM;
        }


    }


}
