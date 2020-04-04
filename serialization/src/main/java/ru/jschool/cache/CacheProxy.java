package ru.jschool.cache;

import ru.jschool.cache.storages.CacheFile;
import ru.jschool.cache.storages.CacheInterface;
import ru.jschool.cache.storages.CacheMemory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.out;

public class CacheProxy implements InvocationHandler {
    private final Object obj;
    private final CacheInterface cacheMemory = new CacheMemory();
    private final CacheInterface cacheFile;
    private final Path cacheDir;

    public CacheProxy(Object obj) {
        this.obj = obj;
        cacheDir = Paths.get("resources");
        cacheFile = new CacheFile(cacheDir, obj.hashCode());
    }

    public CacheProxy(Object object, Path cacheDir) {
        this.obj = object;
        this.cacheDir = cacheDir;
        cacheFile = new CacheFile(cacheDir, obj.hashCode());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!method.isAnnotationPresent(Cache.class)) {
            return method.invoke(obj, args);
        }

        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        Object result;
        if (cacheAnnotation.cacheType() == Cache.EnumCacheType.IN_MEMORY) {
            result = cacheMemory.getResult(method, args);
            if (result != null) {
                out.println("RESULT FROM MEMORY");
                return result;
            } else {
                out.println("INVOKE METHOD");
                result = method.invoke(obj, args);
                cacheMemory.putResult(method, args, result);
                return result;
            }
        } else {
            result = cacheFile.getResult(method, args);
            if (result != null) {
                out.println("RESULT FROM FILE");
                return result;
            } else {
                out.println("INVOKE METHOD");
                result = method.invoke(obj, args);
                cacheFile.putResult(method, args, result);
                return result;
            }
        }
    }

    public static <T> T cache(Object o) {
        return (T) Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new CacheProxy(o));
    }


}
