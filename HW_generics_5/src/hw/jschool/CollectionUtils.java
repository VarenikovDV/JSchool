package hw.jschool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {

    public static <T> void add (List<? extends T> source, T t) {
        source.add(t);
    }

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }


    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> int indexOf(List<? extends T> source, T t) {
        return source.indexOf(t);
    }
    /*
    ??? не понятно что надо return
    public static <T> List<T> limit (List<T> source, int size) {
        return
    }
    */


    public static<T> void removeAll(List<T> removeFrom, List<?> c2) {
        removeFrom.removeAll(c2);
    }

    public static <T> boolean containsAll(List <? extends T> c1, List<?> c2) {
        //true если первый лист содержит все элементы второго
        return  c1.containsAll(c2);
    }

    public static <T> boolean containsAny(List<? extends T> c1, List<?> c2) {
        //true если первый лист содержит хотя-бы 1 второго
        for (Object t:c2){
            if(c1.contains(t)) return true;
        }
        return false;
    }

    public static List range(List list, Object min, Object max) {
        //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
        // Элементы сравнивать через Comparable.
        // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}

    }

    public static List range(List list, Object min, Object max, Comparator comparator) {
        //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
        // Элементы сравнивать через Comparable.
        // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}

    }

}
