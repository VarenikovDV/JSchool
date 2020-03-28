package test.classloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;
    private final String extentionFile  = ".eclass";
    private Map<String,Class<?>>  classHash = new HashMap<>();

    /******************************************************************************************************************/
    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }
    /******************************************************************************************************************/
    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = findClass(name);
        if (clazz!= null ){
            if(resolve ==true) resolveClass(clazz);
            return clazz;
        }
        return super.loadClass(name, resolve);
    }

    /******************************************************************************************************************/
    @Override
    protected Class<?> findClass(String nameClass) throws ClassNotFoundException{
        Class<?> result = this.findLoadedClass(nameClass);
        if(result!=null)  return  result;

        File fileClass =findFile(nameClass);
        if(fileClass==null) return  null;

        try {
            byte[] classArray = readAndEncryptedClass(fileClass);
            return defineClass(nameClass,classArray,0, classArray.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("in EncryptedClassLoader.findClass",e);
        }
    }
    /******************************************************************************************************************/
    private File findFile(String nameFile){
        String  strPath=  dir.getPath()
                        + File.separatorChar
                        + nameFile.replace('.','\\').concat(extentionFile);
        File classFile = new File(strPath);
        if (classFile.exists()) return classFile;
        return null;
    }
    /******************************************************************************************************************/
    private byte[] readAndEncryptedClass(File fileEClass) throws IOException {
        byte[] bArray = Files.readAllBytes(fileEClass.toPath());
        for(int i = 0; i<bArray.length;i++)
            bArray[i]=(byte)(bArray[i]+Integer.valueOf(key));
        return bArray;
    }
}
