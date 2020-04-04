package ru.jschool.cache.storages;


import ru.jschool.cache.Cache;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CacheFile implements CacheInterface {

    private final static String fileExt = ".cache";
    private final int hashObj;
    private final Path pathDir;

    public CacheFile(Path pathDir, int hashObj) {
        this.hashObj = hashObj;
        this.pathDir = pathDir;
    }

    @Override
    public Object getResult(Method method, Object[] args) throws IOException, ClassNotFoundException {
        Cache cacheAnt = method.getAnnotation(Cache.class);
        Object[] tempArgs = CacheUtil.identityArgs(args, cacheAnt.identityBy());
        MethodKey methodKey = new MethodKey(method, tempArgs);
        String fileName = getFileName(methodKey, cacheAnt);
        Path pathFile = Path.of(pathDir.toString(), fileName);
        Object result = null;
        try {
            if (Files.exists(pathFile)) {
                boolean zip = cacheAnt.zip();
                if (zip) {
                    try (ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(pathFile.toFile())))) {
                        result = in.readObject();
                    }
                } else {
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathFile.toFile()))) {
                        result = in.readObject();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Exception read file: " + pathFile.toAbsolutePath(), e);
        } catch (IOException e) {
            throw new IOException("Exception read file: " + pathFile.toAbsolutePath(), e);
        }
        return result;
    }

    @Override
    public Object putResult(Method method, Object[] args, Object result) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        Cache cacheAnt = method.getAnnotation(Cache.class);
        Object[] tempArgs = CacheUtil.identityArgs(args, cacheAnt.identityBy());
        MethodKey methodKey = new MethodKey(method, tempArgs);
        String fileName = getFileName(methodKey, cacheAnt);
        Path pathFile = Path.of(pathDir.toString(), fileName);

        Object tempResult = result;
        int sizeList = cacheAnt.listList();
        if (sizeList > 1 && result != null && (result.getClass().isArray() || CacheUtil.isCollection(result))) {
            tempResult = CacheUtil.truncateCollection(result, sizeList);
        }

        boolean zip = cacheAnt.zip();
        Files.createDirectories(pathDir);
        try {
            if (zip) {
                try (ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(pathFile.toFile())))) {
                    out.writeObject(tempResult);
                    out.flush();
                }
            } else {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathFile.toFile()))) {
                    out.writeObject(tempResult);
                    out.flush();
                }
            }

        } catch (NotSerializableException e) {
            throw new IOException("Return type of the method must be serializable.", e);
        } catch (IOException e) {
            throw new IOException("Exception write file: " + pathFile.toAbsolutePath(), e);
        }
        return tempResult;
    }

    private String getFileName(MethodKey methodKey, Cache cacheAnt) {
        String prefix = cacheAnt.fileNamePrefix();
        boolean b = cacheAnt.zip();
        String tempStr = prefix + methodKey.hashCode() + hashObj + methodKey.getKey().getName() + (b ? ".zip" : fileExt);
        return tempStr;
    }


}
