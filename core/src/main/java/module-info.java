import mcs.plugin.app.core.BasicService;
import mcs.plugin.app.core.Service;

module core {
    exports mcs.plugin.app.core;

    uses Service;
    provides Service with BasicService;
}