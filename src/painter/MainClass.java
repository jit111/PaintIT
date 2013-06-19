package painter;

import javax.swing.*;

public class MainClass {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PaintWindow().setVisible(true);
            }
        });
    }
}
