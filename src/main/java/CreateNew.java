import javax.swing.*;
import java.awt.*;

public class CreateNew extends JComboBox {

    public static final Font myFont = new Font("Gisha", Font.BOLD, 30);

    public static JLabel newLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(Color.WHITE);
        label.setFont(myFont);
        return label;
    }

    public static JTextField newTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setFont(myFont);
        return textField;
    }


    public static JButton newButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(myFont);
        button.setFocusable(false);
        return button;
    }

}



