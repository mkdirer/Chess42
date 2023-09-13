package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Constructing the panel at the bottom to contain the buttons
 * @author tienbui
 *
 */
public class BottomPanel extends JPanel {
	/**
	 * To reset the game
	 */
    JButton resetButton;
    
    /**
     * For server to listen to the client
     */
    JButton listenButton;
    
    /**
     * For the client to connect to the server
     */
    JButton connectButton;

    /**
     * Initlize the bottom panels with 3 buttons
     */
    public BottomPanel(){
        super(new FlowLayout(FlowLayout.CENTER)); // create a new JPanel
//        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        resetButton = new JButton("Reset");
        resetButton.setActionCommand("Reset Button");
        this.add(resetButton);

        listenButton = new JButton("Listen");
        listenButton.setActionCommand("Listen Button");
        this.add(listenButton);

        connectButton = new JButton("Connect");
        connectButton.setActionCommand("Connect Button");
        this.add(connectButton);

//        this.setVisible( true);
    }

    /**
     * Set action listener to the buttons. Actions are received in the controller.
     * @param listener ActionListener handled in the controller
     */
    public void setListener(ActionListener listener){
        resetButton.addActionListener(listener);
        listenButton.addActionListener(listener);
        connectButton.addActionListener(listener);
    }

}
