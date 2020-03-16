package hw.jschool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T> void add (List<? super T> source, T t) {
       source.add(t);
    }

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }


    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> ArrayList<T> newArrayList(Iterator<? extends T> elements) {
        if (elements == null) {
            return null;
        }

        ArrayList<T> result = new ArrayList<>();
        elements.forEachRemaining(result::add);
        /*
        for(T t : elements){
            result.add(t);
        }*/
        return result;
    }

    public static <T> int indexOf(List<? extends T> source, T t) {
        return source.indexOf(t);
    }

    public static <T> void removeAll(List<T> removeFrom, List<?> c2) {
        removeFrom.removeAll(c2);
    }

    public static <T> boolean containsAll(List <T> c1, List<?> c2) {
        //true если первый лист содержит все элементы второго
        return  c1.containsAll(c2);
    }

    public static boolean containsAny(List<?> c1, List<?> c2) {
        //true если первый лист содержит хотя-бы 1 второго
        for (Object t:c2){
            if(!c1.contains(t)) return false;
        }
        return true;
    }

    public static <T extends Comparable<? super T>> List<T> range(List<? extends T> list, T min, T max) {
        //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
        // Элементы сравнивать через Comparable.
        // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
        return list.stream().filter(v -> v.compareTo(min)>=0 && v.compareTo(max)<=0).collect(Collectors.toList());
    }

    public static <T /*extends Comparator<? super T>*/ > List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
        // Элементы сравнивать через Comparable.
        // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
        return list.stream().filter(v->comparator.compare(v,min)>=0 && comparator.compare(v,max)<=0).collect(Collectors.toList());
    }

}
