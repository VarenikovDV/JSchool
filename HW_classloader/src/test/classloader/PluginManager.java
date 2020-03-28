package test.classloader;

import java.io.File;

public class PluginManager {

    static EncryptedClassLoader pluginCL;
    private final String pluginRootDirectory;
    private final String key = "0";

    /******************************************************************************************************************/
    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
        this.pluginCL = new EncryptedClassLoader("0", new File(pluginRootDirectory), this.getClass().getClassLoader());
    }

    /******************************************************************************************************************/
    public Plugin load(String pluginName, String pluginClassName, boolean reload) throws Exception {
        System.out.println(Main.class.getResource("Load plugin " + pluginName + " class name: " + pluginClassName));
        if (reload)
            pluginCL = new EncryptedClassLoader(key, new File("pluginRootDirectory\\pluginName\\"), Main.class.getClassLoader());
        Class<?> clazz = pluginCL.loadClass(pluginClassName, true);
        return (Plugin) clazz.getDeclaredConstructor().newInstance();
    }
    /******************************************************************************************************************/
}



