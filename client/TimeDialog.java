package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
public class TimeDialog {
	private String message = null;
    private int secends = 0;
    private JLabel label = new JLabel();
    private JButton confirm; 
    private JDialog dialog = null;
    int result = -5;
    public int showDialog(JFrame father, String message, int sec) {
    	
        this.message = message;
        secends = sec;
        label.setText(message);
        label.setBounds(80,6,200,20);
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        confirm = new JButton("OK");
        confirm.setBounds(100,40,60,20);
        confirm.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				result = 0;
				TimeDialog.this.dialog.dispose();
			}
		});

        dialog = new JDialog(father, true);
        dialog.setLayout(null);
        dialog.add(label);
        dialog.add(confirm);
        s.scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                
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
        return result;
    }
}