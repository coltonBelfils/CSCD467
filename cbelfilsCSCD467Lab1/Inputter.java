import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class Inputter extends JFrame implements KeyListener {

    private DisplayPrinter d1;
    private DisplayPrinter d2;
    private Thread t1;
    private Thread t2;
    private int pressCount = 0;

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private JTextArea output;

    public Inputter(String name) {
        super(name);
        //JTextArea output = new JTextArea(20,30) // Can this statement replace the next one?

        output = new JTextArea(20, 30); //create JTextArea in which all messages are shown.

		d1 = new DisplayPrinter(output);
		d2 = new DisplayPrinter(output);
		t1 = new Thread(d1);
		t2 = new Thread(d2);
		t1.start();
		t2.start();

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
        if (pressCount == 0) {
            t1.interrupt();
        } else if (pressCount == 1) {
            t2.interrupt();
        } else {
            output.append("All threads interrupted");
        }
        pressCount++;
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
