package ru.jschool.cache;


import ru.jschool.cache.service.Service;
import ru.jschool.cache.service.ServiseImpl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // write your code here
        Service service = CacheProxy.cache(new ServiseImpl());

        List<?> list;
        out.println("***************************************************");
        out.println("    @Cache(cacheType = IN_MEMORY,\n" +
                "            fileNamePrefix = \"data\",\n" +
                "            identityBy = {true},\n" +
                "            listList = 5)\n" +
                "    List<Integer> getList(int[] array, double value, Date date);\n");
        out.println("   getList(new int[]{1,2,3,4,5,6,7,8,9},    123d,   new Date(121231123)): " + service.getList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 123d, new Date(121231123)));
        out.println("   getList(new int[]{1,2,3,4,5,6,7,8,9}, 123123d,   new Date(121411123)): " + service.getList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 123123d, new Date(1214231123)));

        out.println("***************************************************");
        out.println("    @Cache(cacheType = FILE,\n" +
                "            fileNamePrefix = \"data\",\n" +
                "            listList = 3,\n" +
                "            zip = true)\n" +
                "    String[] getArray(String[] item);\n");
        out.println("   getArray(\"1 1 1 1 1 1\".split(\"\\\\s\")): " + Arrays.asList(service.getArray("1 1 1 1 1 1".split("\\s"))));
        out.println("   getArray(\"1 1 1 1 1 1\".split(\"\\\\s\")): " + Arrays.asList(service.getArray("1 1 1 1 1 1".split("\\s"))));
        out.println("   getArray(\"2 2 2 2 2\".split(\"\\\\s\")): " + Arrays.asList(service.getArray("2 2 2 2 2".split("\\s"))));

        out.println("***************************************************");
        out.println("    @Cache(cacheType = FILE,\n" +
                "            identityBy = {false})\n" +
                "    String getString(String str)\n");

        out.println("   getString(\"111111\"): " + service.getString("111111"));
        out.println("   getString(\"222 31\"): " + service.getString("222222"));
        out.println("***************************************************");
    }
}
