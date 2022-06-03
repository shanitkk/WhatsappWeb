
import com.sun.prism.paint.Stop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WebWindow extends JPanel {

    public static final String CONTACT = "https://api.whatsapp.com/send?phone=";

    public static final int ENTER_BUTTON_X = 100, ENTER_BUTTON_Y = 700, ENTER_BUTTON_WIDTH = 250, ENTER_BUTTON_HEIGHT = 100;
    public static final int GENERAL_WIDTH = 400, GENERAL_HEIGHT = 50;
    public static final int PHONE_NUM_TITLE_Y = 100, PHONE_NUM_TITLE_WIDTH = 275;
    public static final int MARGIN_BETWEEN = 100;
    public static final int MESSAGE_TITLE_MARGIN = 310;
    public static final int MESSAGE_TEXT_MARGIN_X = 210, MESSAGE_TEXT_WIDTH = 500, MESSAGE_TEXT_HEIGHT = 100;
    public static final int ENTER_LABEL_X = 100, ENTER_LABEL_Y = 700, ENTER_LABEL_WIDTH = 500, ENTER_LABEL_HEIGHT = 150;
    public static final String SUCCESSFULLY_MESSAGE = "התתחברות בוצעה בהצלחה!";

    private ImageIcon background;
    private JButton enterButton;
    private JLabel successfullyEnterLabel;
    private JLabel phoneNumTitle;
    private JTextField phoneNumberTextField;
    private JLabel messageTitle;
    private JTextField messageTextField;
    private JLabel messageForUser;

    private ChromeDriver web;
    private WebElement connect;


    public WebWindow(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);

        this.enterButton = CreateNew.newButton("התחבר", ENTER_BUTTON_X, ENTER_BUTTON_Y, ENTER_BUTTON_WIDTH, ENTER_BUTTON_HEIGHT);
        this.add(this.enterButton);
        enter();

        this.phoneNumTitle = CreateNew.newLabel("הכנס מספר פלאפון: ", MainWindow.WINDOW_WIDTH - GENERAL_WIDTH,
                PHONE_NUM_TITLE_Y, PHONE_NUM_TITLE_WIDTH, GENERAL_HEIGHT);
        this.add(phoneNumTitle);

        this.phoneNumberTextField = CreateNew.newTextField(phoneNumTitle.getX() + phoneNumTitle.getWidth() - GENERAL_WIDTH,
                phoneNumTitle.getY() + phoneNumTitle.getHeight(), GENERAL_WIDTH, GENERAL_HEIGHT);
        this.add(phoneNumberTextField);

        this.messageTitle = CreateNew.newLabel("הכנס הודעה: ", MainWindow.WINDOW_WIDTH - MESSAGE_TITLE_MARGIN,
                phoneNumberTextField.getY() + MARGIN_BETWEEN, GENERAL_WIDTH, GENERAL_HEIGHT);
        this.add(messageTitle);

        this.messageTextField = CreateNew.newTextField(messageTitle.getX() + messageTitle.getWidth() - MESSAGE_TEXT_WIDTH - MESSAGE_TEXT_MARGIN_X,
                messageTitle.getY() + messageTitle.getHeight(), MESSAGE_TEXT_WIDTH, MESSAGE_TEXT_HEIGHT);
        this.add(messageTextField);

        this.messageForUser = CreateNew.newLabel("", 200, 200, 500, 80);
        this.add(this.messageForUser);

        this.successfullyEnterLabel = CreateNew.newLabel("", ENTER_BUTTON_X, ENTER_BUTTON_Y, ENTER_BUTTON_WIDTH, ENTER_BUTTON_HEIGHT);
        this.add(successfullyEnterLabel);


        this.connect = null;
        this.background = new ImageIcon("background.png");
        this.setVisible(true);
    }

    private void enter() {
        this.enterButton.addActionListener((event) -> {
            String phone = this.phoneNumberTextField.getText();
            if (this.messageTextField.getText().equals("") && this.phoneNumberTextField.getText().equals(""))
                this.messageForUser.setText("יש להכניס מספר טלפון תקין והודעה לשליחה");
            else if (this.phoneNumberTextField.getText().equals(""))
                this.messageForUser.setText("יש להכניס מספר טלפון");
            else if (!PhoneNumber.isValidPhoneNumber(phone))
                this.messageForUser.setText("יש להכניס מספר תקין.");
            else if (this.messageTextField.getText().equals(""))
                this.messageForUser.setText("יש להכניס הודעה");

            if (PhoneNumber.isValidPhoneNumber(phone) && (!this.messageTextField.getText().equals(""))) {
                this.messageForUser.setText("תקין");

                this.web = new ChromeDriver();
                this.web.get(CONTACT + PhoneNumber.formatPhoneNumber(phone));
                web.manage().window().maximize();

                WebElement chet = this.web.findElement(By.id("action-button"));
                chet.click();

                WebElement linkId = this.web.findElement(By.id("fallback_block"));
                List<WebElement> linkElement = linkId.findElements(By.tagName("a"));
                String chatLink = linkElement.get(1).getAttribute("href");

                this.web.get(chatLink);
                connection();

            }
        });
    }

    public ChromeDriver connection() {
        new Thread(() -> {
            try {
                this.connect = this.web.findElement(By.id("side"));
                if (this.connect != null) {
                    System.out.println("found");
                    this.successfullyEnterLabel.setText(SUCCESSFULLY_MESSAGE);
                    this.enterButton.setVisible(false);
                }
            } catch (Exception e) {
                connection();
            }
        }).start();
        SendMessage sendMessage = new SendMessage(this.messageTextField.getText(), this.web);
        return this.web;
    }

//                if (messageClass.contains("message-in")){
//                    WebElement comment=this.lastMessage.findElement(By.cssSelector("span[dir='rtl']"));
//                    this.comment=comment.getText();
//                    System.out.println(this.comment);
//                    break;

    public void paintComponent(Graphics graphics) {
        graphics.drawImage(this.background.getImage(), 0, 0,
                MainWindow.WINDOW_WIDTH, MainWindow.WINDOW_HEIGHT, null);
    }


}

