package ru.jschool.cache.storages;


import ru.jschool.cache.Cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CacheMemory implements CacheInterface {

    private final Map<MethodKey, Object> cache = new HashMap<>();

    public CacheMemory() {

    }

    @Override
    public Object getResult(Method method, Object[] args) {
        Cache annotation = method.getAnnotation(Cache.class);
        Object[] tempArgs = CacheUtil.identityArgs(args, annotation.identityBy());
        return cache.get(new MethodKey(method, tempArgs));
    }

    @Override
    public Object putResult(Method method, Object[] args, Object result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Cache annotation = method.getAnnotation(Cache.class);
        Object[] tempArgs = CacheUtil.identityArgs(args, annotation.identityBy());

        Object tempResult = result;
        int sizeList = annotation.listList();
        if (sizeList > 1 && result != null && (result.getClass().isArray() || CacheUtil.isCollection(result))) {
            tempResult = CacheUtil.truncateCollection(result, sizeList);
        }

        return cache.put(new MethodKey(method, tempArgs), tempResult);
    }

}
