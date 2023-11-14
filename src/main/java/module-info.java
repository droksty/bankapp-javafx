module io.droksty.bankappfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens io.droksty.bankappfx to javafx.fxml;
    exports io.droksty.bankappfx;
    exports io.droksty.bankappfx.model;
    exports io.droksty.bankappfx.controller;
    exports io.droksty.bankappfx.view;
}