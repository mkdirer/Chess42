package MVC;

import ChessPiece.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class ViewFrame extends JFrame implements IViewFrame {
    BoardPanel boardPanel;
    BottomPanel bottomPanel;
    IController iController;

    /**
     * Represent the view of a chess board game
     * @param controller Controller of the Java program
     */
    public ViewFrame(IController controller){
        super("JavaChess");
        this.setSize(600, 630);
        this.setLocation(400, 1500);

        this.iController = controller;
        boardPanel = new BoardPanel(controller);

        // create the view frame
        this.setLayout(new BorderLayout(2, 2));

        Container container = this.getContentPane();
        // bottom panel for buttons
        bottomPanel = new BottomPanel();
        container.add(boardPanel, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.PAGE_END);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void setButtonListener(ActionListener listener){
        bottomPanel.setListener(listener);
    }

    @Override
    public void setMouseListener(MouseListener listener){
        boardPanel.setMouseListener(listener);
    }

    @Override
    public void setMouseMotionListener(MouseMotionListener listener){
        boardPanel.setMouseMotionListener(listener);
    }

    @Override
    public void repaint(){
        boardPanel.repaint();
    }

    @Override
    public void setHighlightedCoords(List<Coordinate> coords){
        boardPanel.setHighlightedCoords(coords);
    }
}
