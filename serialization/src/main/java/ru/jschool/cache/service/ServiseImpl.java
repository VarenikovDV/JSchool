package ru.jschool.cache.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiseImpl implements Service {

    public ServiseImpl() {
    }

    @Override
    public List<Integer> getList(int[] array, double value, Date date) {
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        return list;
    }

    @Override
    public String[] getArray(String[] array) {
        return array;
    }

    @Override
    public String getString(String str) {
        return str;
    }

}
