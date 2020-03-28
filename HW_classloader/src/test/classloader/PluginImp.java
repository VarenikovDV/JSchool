package test.classloader;

public class PluginImp implements Plugin {
    @Override
    public void doUserfull() {
        System.out.println(" **** Call inner implementation PluginImp");
    }
}
