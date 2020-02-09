import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class Inputter extends JFrame implements KeyListener {

    private static final int FIRST_KEY_PRESS = 403;
    private static final int ENTER_PRESSED = 229;
    private static final int NEW_STRING_INPUT = 477;

    private AsyncPrinter d1;
    private Thread t1;
    private int inputSate = NEW_STRING_INPUT;
    private String newMessageValue = "";

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private JTextArea output;

    public Inputter(String name) {
        super(name);
        output = new JTextArea(20, 30); //create JTextArea in which all messages are shown.

        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);  // JTextArea always set focus on the last message appended.

        //
        add(new JScrollPane(output)); // add a Scroll bar to JFrame, scrolling associated with JTextArea object
        setSize(500, 500);            // when lines of messages exceeds the line capacity of JFrame, scroll bar scroll down.
        setVisible(true);
        output.addKeyListener(this);  // Adds the specified key listener to receive key events from this component.
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (newMessageValue.length() != 0) {
                if (newMessageValue.equals("exit")) {
                    System.exit(0);
                }
                t1 = new Thread(new AsyncPrinter(output, newMessageValue));
                newMessageValue = "";
                t1.start();
            }
        } else if (KeyEvent.getKeyText(e.getKeyCode()).length() == 1) {
            if (newMessageValue.length() == 0) {
                if (t1 != null && t1.isAlive()) {
                    t1.interrupt();
                }
                newMessageValue += e.getKeyChar();
            } else {
                newMessageValue += e.getKeyChar();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        Inputter inp = new Inputter("A JFrame and KeyListener Demo");
        inp.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
    }
}