module fr.univartois.butinfo.r304.flatcraft.Flatcraft {
    exports fr.univartois.butinfo.r304.flatcraft;

    opens fr.univartois.butinfo.r304.flatcraft.controller to javafx.fxml;

    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires transitive javafx.controls;
}
