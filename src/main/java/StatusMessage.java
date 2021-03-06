import javafx.scene.control.cell.ChoiceBoxTreeCellBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class StatusMessage extends JPanel {

    private boolean isSend; // הודעה שנשלחה
    private boolean isAccepted; // הודעה שנשלחה והתקבלה
    private boolean isSeen; // וי כחול - הודעה שנקראה
    private boolean isMessageAccepted;
    private String messageAccepted;

    private JLabel status;

    public StatusMessage(JLabel status, ChromeDriver web) {
        this.isSend = true;
        this.isAccepted = false;
        this.isSeen = false;
        this.isMessageAccepted = false;
        status(web);
        this.status = status;
    }

    public void status(ChromeDriver web) {
        try {
            new Thread(() -> {
                while (!this.isSeen) {
                    WebElement messageBox = web.findElement(By.id("main"));
                    List<WebElement> tagMessage = messageBox.findElements(By.className("_1beEj"));
                    List<WebElement> afterTag = tagMessage.get(tagMessage.size() - 1).findElements(By.tagName("span"));
                    String statusWeb = afterTag.get(1).getAttribute("aria-label");
                    new Thread(() -> {
                        if (statusWeb.contains("נמסרה")) {
                            this.isAccepted = true;
                            this.status.setText("נמסרה");
                        } else if (statusWeb.contains("נקראה")) {
                            this.isSeen = true;
                            this.status.setText("נקראה");
                            this.status.setForeground(Color.BLUE);
                            incomingMessage(web);
                        }
                    }).start();
                }
            }).start();
            Thread.sleep(1000);
        } catch (Exception e) {
            status(web);
        }
    }

    public void incomingMessage(ChromeDriver web) {
        try {
            new Thread(() -> {
                while (!this.isMessageAccepted) {
                    returnElement(web);
                }
            }).start();
        } catch (Exception e) {
            incomingMessage(web);
        }
    }


    public WebElement returnElement(ChromeDriver web) {
        WebElement span2 = null;
        try {
            List<WebElement> messagesList = web.findElements(By.className("_22Msk"));
            List<WebElement> lastMessage = messagesList.get(messagesList.size() - 1).findElements(By.className("_1Gy50"));
            List<WebElement> span = lastMessage.get(0).findElements(By.tagName("span"));
            WebElement span1 = span.get(0).findElement(By.cssSelector("span"));
            String messageAccepted = span1.getText();
            System.out.println(messageAccepted);
            if (span.get(0).getAttribute("tabindex").contains("0")) {
                span2 = span.get(0).findElement(By.cssSelector("span"));
                this.messageAccepted = span2.getText();
            }
        } catch (Exception e) {
            returnElement(web);
        }
        return span2;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public boolean isMessageAccepted() {
        return isMessageAccepted;
    }

    public void setMessageAccepted(boolean messageAccepted) {
        isMessageAccepted = messageAccepted;
    }

    public String getMessageAccepted() {
        return messageAccepted;
    }

    public void setMessageAccepted(String messageAccepted) {
        this.messageAccepted = messageAccepted;
    }

    public JLabel getStatus() {
        return status;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }
}


