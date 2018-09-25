package client;

import java.awt.Dimension;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
public class TimeDialog {
    private int secends = 0;
    private JLabel label = new JLabel();
    private JDialog dialog = null;
    
    public void showDialog(JFrame father, String message, int sec) {
        secends = sec;
        label.setText(message);
        label.setBounds(80,6,200,20);
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();

        dialog = new JDialog(father, true);
        dialog.setLayout(null);
        dialog.add(label);
        s.scheduleAtFixedRate(new Runnable() {          
            @Override
            public void run() {
                
                TimeDialog.this.secends--;
                if(TimeDialog.this.secends == 0) {
                    TimeDialog.this.dialog.dispose();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
        dialog.pack();
        dialog.setSize(new Dimension(350,100));
        dialog.setLocationRelativeTo(father);
        dialog.setVisible(true);
    }
}