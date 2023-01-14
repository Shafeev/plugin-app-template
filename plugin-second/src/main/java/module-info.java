import mcs.plugin.PluginSecond;
import mcs.plugin.app.core.Service;

module plugin.second {
    requires core;

    provides Service with PluginSecond;
}