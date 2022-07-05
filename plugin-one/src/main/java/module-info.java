import mcs.plugin.app.core.Service;
import mcs.plugin.app.plugin.one.PluginOne;

module plugin.one {
    requires core;

    provides Service with PluginOne;
}