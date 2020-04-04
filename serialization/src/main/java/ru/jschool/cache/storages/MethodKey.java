package ru.jschool.cache.storages;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class MethodKey extends AbstractMap.SimpleEntry<Method, List<Object>> {

    public MethodKey(Method key, Object[] values) {
        /*crutch for equals arrays*/
        super(key, null);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null && values[i].getClass().isArray())
                list.add(arrayToList(values[i]));
            else
                list.add(values[i]);
        }
        this.setValue(list);
    }

    private static List<Object> arrayToList(Object array) {
        /*crutch for converting arrays of primitive types*/
        int length = Array.getLength(array);
        List<Object> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(Array.get(array, i));
        }
        return list;
    }
}