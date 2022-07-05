package mcs.plugin.app.plugin.one;

import mcs.plugin.app.core.Service;

public class PluginOne implements Service {

    @Override
    public void doJob() {
        System.out.println("Plugin one");
    }
}
