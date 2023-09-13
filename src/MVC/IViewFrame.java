package MVC;

import ChessPiece.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * The view of the chess board
 * @author tienbui
 *
 */
public interface IViewFrame {
	/**
	 * Repainting the chess game, from Swing
	 */
    void repaint();
    

   /**
    * Set button listener on the bottom panel to collect mouse click activities
    * @param listener ActionListener to collect button click activities
    */
    void setButtonListener(ActionListener listener);
    
    /**
     * Set mouse listener on the JPanel to collect mouse movement activities
     * @param listener MouseListener to collect mouse activities
     */
    void setMouseListener(MouseListener listener);

    /**
     * Set mouse listener on the JPanel to collect mouse motion activities
     * @param MouseMotionListener to collect mouse motion activities
     */
    void setMouseMotionListener(MouseMotionListener listener);
    
    /**
     * Set the highlighted tiles to show possible tiles that the current moving piece can go
     * @param coords
     */
    void setHighlightedCoords(List<Coordinate> coords);
}
