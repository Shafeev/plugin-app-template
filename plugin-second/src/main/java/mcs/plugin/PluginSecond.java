package mcs.plugin;

import mcs.plugin.app.core.Service;

public class PluginSecond implements Service {

    @Override
    public void doJob() {
        System.out.println("Plugin second");
    }
}
