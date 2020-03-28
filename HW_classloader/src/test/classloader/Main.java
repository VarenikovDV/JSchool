package test.classloader;

    import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws Exception {
        PluginManager pm = new PluginManager("pluginRootDirectory\\pluginName\\");

        System.out.println(Main.class.getResource("Load PluginImp (class name: plugin1.PluginImp)"));
        Plugin plugin = new PluginImp();
        plugin.doUserfull();

        plugin = pm.load("TEST1","test.classloader.PluginImp1", false );
        plugin.doUserfull();

        plugin = pm.load("TEST1","test.classloader.PluginImp2", false );
        plugin.doUserfull();

     }

}
