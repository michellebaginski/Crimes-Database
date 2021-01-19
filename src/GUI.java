import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;

public class GUI {

    public void GUI() {}

    public CheckBox makeCheckBox(String title, int top, int right, int bottom, int left) {
        CheckBox box = new CheckBox(title);
        box.setPadding(new Insets(top, right, bottom, left));
        return box;
    }

}
