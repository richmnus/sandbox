package gui;

import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Dialog {

    // Resizing Swing components to their contents:
    // for java.awt.Window subclasses, call pack() (e.g. JDialog)
    // for javax.swing.JComponent subclasses, call revalidate() and repaint() (e.g. JPanel)
    // Note that JDialogs size according to their contents, not their title
    
    public Dialog() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600,400));
        frame.setVisible(true);

        JLabel label = new JLabel("Label text");

        // Without resizing code, the JDialog's width is sized to its contents,
        // not to the width of its title
        JOptionPane pane = new JOptionPane(label, JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(frame,
                "The quick brown fox jumps over the lazy dog");

        FontMetrics fm = dialog.getFontMetrics(dialog.getFont());
        // Allow additional margin for the close dialog 'X'
        int width = fm.stringWidth(dialog.getTitle()) + 60;
        width = width > dialog.getPreferredSize().width ? width : dialog
                .getPreferredSize().width;
        // Use setSize(), as setPreferredSize() doesn't work here
        dialog.setSize(width, dialog.getPreferredSize().height);
        // Don't call pack() as this will resize the dialog to its contents, not
        // the width of its title
        dialog.setVisible(true);
    }

}
