package ru.jschool.cache.storages;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class CacheUtil {
    protected static Object truncateCollection(Object result, int sizeList) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object tempResult = result;
        if (result.getClass().isArray()) {
            int length = Array.getLength(result);
            if (length > sizeList) {
                tempResult = Array.newInstance(result.getClass().getComponentType(), sizeList);
                for (int i = 0; i < sizeList; i++) {
                    Array.set(tempResult, i, Array.get(result, i));
                }
            }
        } else if (result instanceof Collection<?>) {
            Collection tempCollection = (Collection) result.getClass().getConstructor().newInstance();
            Collection resultCollection = (Collection) result;
            if (resultCollection.size() > sizeList) {
                int count = 0;
                for (Object o : resultCollection) {
                    if (count >= sizeList) break;
                    tempCollection.add(o);
                    count++;
                }
                tempResult = tempCollection;
            }
        } else if (result instanceof Map<?, ?>) {
            Map tempMap = (Map) result.getClass().getConstructor().newInstance();
            Map resultMap = (Map) result;
            if (resultMap.size() > sizeList) {
                int count = 0;
                Map.Entry entry;
                for (Object o : resultMap.entrySet()) {
                    if (count >= sizeList) break;
                    entry = (Map.Entry) o;
                    tempMap.put(entry.getKey(), entry.getValue());
                    count++;
                }
                tempResult = tempMap;
            }
        }
        return tempResult;
    }

    protected static Object[] identityArgs(Object[] args, boolean[] identityBy) {
        Object[] tempArgs = Arrays.copyOf(args, args.length);
        for (int i = 0; identityBy.length != 0 && i < tempArgs.length; i++) {
            if (i >= identityBy.length || identityBy[i] == false) {
                tempArgs[i] = null;
            }
        }
        return tempArgs;
    }

    protected static boolean isCollection(Object o) {
        if (o instanceof Collection<?> || o instanceof Map<?, ?>) {
            return true;
        }
        return false;
    }
}
