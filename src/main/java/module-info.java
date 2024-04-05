module assignmentThree {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;

    opens assignmentThree to javafx.fxml;
    exports assignmentThree;
}