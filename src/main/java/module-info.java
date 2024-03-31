module projecta3.cpsc_219_w2024_mcdb_a3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;

    opens projecta3 to javafx.fxml;
    exports projecta3;
}