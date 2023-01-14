import mcs.plugin.app.core.BasicService;
import mcs.plugin.app.core.Service;

module core {
    requires java.desktop;
    exports mcs.plugin.app.core;

    requires javafx.controls;
    requires javafx.fxml;

    opens mcs.plugin.app.core to javafx.fxml;

    uses Service;
    provides Service with BasicService;
}