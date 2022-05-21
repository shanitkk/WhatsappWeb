
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;


import javax.swing.*;
import java.awt.*;

import java.util.List;

public class WebWindow extends JPanel {

    Font font = new Font("Gisha", Font.BOLD, 30);
    Font textFiledFont = new Font("Gisha", Font.BOLD, 24);

    public static final String WEB = "https://web.whatsapp.com/";

    public static final int ENTER_BUTTON_X = 100, ENTER_BUTTON_Y = 700, ENTER_BUTTON_WIDTH = 250, ENTER_BUTTON_HEIGHT = 100;
    public static final int ENTER_LABEL_Y = 0, ENTER_LABEL_WIDTH = 200, ENTER_LABEL_HEIGHT = 150;
    public static final int LENGTH_PHONE_NUMBER = 10;
    public static final String PHONE_START = "05", ISRAELI_AREA_CODE = "972";
    public static final int NUM_TITLE_Y = 100, NUM_TITLE_WIDTH = 275;
    public static final int MESSAGE_LABEL_MARGIN = 310;
    public static final int MESSAGE_TEXT_MARGIN_X = 610, MESSAGE_TEXT_MARGIN_Y = 100, MESSAGE_TEXT_HEIGHT = 100;
    public static final int GENERAL_WIDTH = 400, GENERAL_HEIGHT = 50;


    private ImageIcon background;
    private JButton enterButton;
    private JLabel successfullyEnterLabel;
    private JLabel phoneNumTitle;
    private JTextField phoneNumberTextField;
    private JLabel messageTitle;
    private JTextField messageTextField;


    public WebWindow(int x, int y, int width, int height) {
        System.setProperty("webdriver.chrome.driver", "C:\\1234\\driver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-data-air=C:\\Users\\shani\\AppData\\Local\\Temp\\scoped_dir4008_2001821348\\Default");
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\adarm\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("user-data-dir=c:C:\\Users\\adarm\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1\n");

        this.setBounds(x, y, width, height);
        this.setLayout(null);

//        this.enterButton = new MyJButton("התחבר", ENTER_BUTTON_X, ENTER_BUTTON_Y, ENTER_BUTTON_WIDTH, ENTER_BUTTON_HEIGHT).getButton();
//        this.add(this.enterButton);
//        enter();
//
        this.phoneNumTitle = newLabel("הכנס מספר פלאפון: ", MainWindow.WINDOW_WIDTH - GENERAL_WIDTH,
                NUM_TITLE_Y, NUM_TITLE_WIDTH, GENERAL_HEIGHT);
        this.add(phoneNumTitle);

        this.phoneNumberTextField = newTextField(phoneNumTitle.getX() + phoneNumTitle.getWidth() - GENERAL_WIDTH,
                phoneNumTitle.getY() + phoneNumTitle.getHeight(), GENERAL_WIDTH, GENERAL_HEIGHT);
        this.add(phoneNumberTextField);

        this.messageTitle = newLabel("הכנס הודעה: ", MainWindow.WINDOW_WIDTH - MESSAGE_LABEL_MARGIN,
                phoneNumberTextField.getY() + MESSAGE_TEXT_MARGIN_Y, GENERAL_WIDTH, GENERAL_HEIGHT);
        this.add(messageTitle);

        this.messageTextField = newTextField(messageTitle.getX() + messageTitle.getWidth() - MESSAGE_TEXT_MARGIN_X,
                messageTitle.getY() + messageTitle.getHeight(), GENERAL_WIDTH, MESSAGE_TEXT_HEIGHT);
        this.add(messageTextField);


//        this.phoneNumberTextField = newTextField(this.phoneNumTitle.getX(),
//                this.phoneNumTitle.getY() + this.phoneNumTitle.getHeight(), 500, 500);
//        this.add(this.phoneNumberTextField);

        this.background = new ImageIcon("background.png");
        this.setVisible(true);
    }

    private void enter() { //TODO action listener++++++++++++++++++++++++
        this.enterButton.addActionListener((event) -> {
            this.enterButton.setVisible(false);
            repaint();
            ChromeDriver web = new ChromeDriver();
            web.get(WEB);
            web.manage().window().maximize();
            List<WebElement> pic = web.findElements(By.className("web"));
            if (pic.size() > 0) {
                System.out.println("find");
//                enterLabel();
//                repaint();
                this.successfullyEnterLabel = newLabel("התתחברות בוצעה בהצלחה", MainWindow.WINDOW_WIDTH - ENTER_LABEL_WIDTH,
                        ENTER_LABEL_Y, ENTER_LABEL_WIDTH, ENTER_LABEL_HEIGHT);

            }
        });
    }

    public JLabel newLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);
        return label;
    }

    public JTextField newTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setFont(textFiledFont);
        return textField;
    }

    public boolean phoneNumber1(String phone) {
        boolean validPhoneNumber = false;
        try {
            if (phone.length() != 0) {
                if (phone.length() < 10 || phone.length() > 12) {
                    System.out.println("Invalid value");
                } else {
                    if (phone.substring(0, 2).equals("05") && phone.length() == 10) {
                        validPhoneNumber = true;
                    } else if (phone.substring(0, 3).equals("972") && phone.length() == 12)
                        validPhoneNumber = true;
                }
            } else
                System.out.println("יש להכניס מספר טלפון - לא תיבה ריקה");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return validPhoneNumber;
    }

    public boolean phoneNumber(String phoneNumber) {
        int counterValidNumber = 0;
        int counterAreaCodeNum = 0;
        boolean validLength = false;
        boolean validAreaCode = false;
        boolean validPhoneNumber = false;
        if (phoneNumber.length() != LENGTH_PHONE_NUMBER) {
            System.out.println("Invalid value");
        } else {
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (Character.isDigit(phoneNumber.charAt(i)))
                    counterValidNumber++;
            }
            if (counterValidNumber == LENGTH_PHONE_NUMBER)
                validLength = true;
            for (int i = 0; i < PHONE_START.length(); i++) {
                if (phoneNumber.charAt(i) == PHONE_START.charAt(i))
                    counterAreaCodeNum++;
            }
            if (counterAreaCodeNum == PHONE_START.length()) {
                validAreaCode = true;
            }
            if (validLength && validAreaCode)
                validPhoneNumber = true;
            else System.out.println("Invalid value");
        }
        return validPhoneNumber;
    }


    public void paintComponent(Graphics graphics) {
        graphics.drawImage(this.background.getImage(), 0, 0,
                MainWindow.WINDOW_WIDTH, MainWindow.WINDOW_HEIGHT, null);
    }
}





