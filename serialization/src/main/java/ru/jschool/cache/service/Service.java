package ru.jschool.cache.service;

import ru.jschool.cache.Cache;
import java.util.Date;
import java.util.List;

import static ru.jschool.cache.Cache.EnumCacheType.FILE;
import static ru.jschool.cache.Cache.EnumCacheType.IN_MEMORY;



public interface Service {

    @Cache(cacheType = IN_MEMORY,
            fileNamePrefix = "data",
            identityBy = {true},
            listList = 5)
    List<Integer> getList(int[] array, double value, Date date);

    @Cache(cacheType = FILE,
            fileNamePrefix = "data",
            listList = 3,
            zip = true)
    String[] getArray(String[] item);

    @Cache(cacheType = FILE,
            identityBy = {false})
    String getString(String str);

}

