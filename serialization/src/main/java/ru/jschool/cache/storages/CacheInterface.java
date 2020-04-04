package ru.jschool.cache.storages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface CacheInterface {
    public Object getResult(Method method, Object[] args) throws IOException, ClassNotFoundException;

    public Object putResult(Method method, Object[] args, Object result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException;
}
