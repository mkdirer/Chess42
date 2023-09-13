package MVC;

import Utils.*;
import ChessPiece.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Represent the chess board as a JPanel component
 * @author tienbui
 *
 */
public class BoardPanel extends JPanel {
    protected static final int CELL_SIZE = 65;
    protected static final int X0 = 30;
    protected static final int Y0 = 20;
    private List<Coordinate> highlightedCoords;

    //    private static ViewFrame viewFrame;
    private static IController iController;
    private static HashMap<String, Image> filePath2Img;

    /**
     * Initialize the Board Panel with controller
     * @param iController Controller of the software that connects view and model
     */
    public BoardPanel(IController iController){
        super();

        this.iController = iController;

        filePath2Img = ViewUtils.loadAllPieceImg();
    }


    @Override
    public void paint(Graphics g){
        drawBoard(g);
        drawPiecesAll(g);
        drawPieceOnMouseDrag(g);
    }

    /**
     * Set mouse listener on the JPanel to collect mouse click activities
     * @param listener ActionListener to collect mouse activities
     */
    public void setMouseListener(MouseListener listener){
        // public void actionPerformed(ActionEvent e) on controller
        this.addMouseListener(listener);
    }

    /**
     * Set mouse listener on the JPanel to collect mouse movement activities
     * @param listener
     */
    public void setMouseMotionListener(MouseMotionListener listener){
        // public void actionPerformed(ActionEvent e) on controller
        this.addMouseMotionListener(listener);
    }

    /**
     * Draw chess pieces to follow mouse drag movement on the chess board
     * @param g current graphics
     */
    private void drawPieceOnMouseDrag(Graphics g){
        IPiece movingPiece = iController.getMovingPiece();
        Point p = iController.getMouseDragPoint();
        if (p == null || movingPiece == null) return;

        Image img = filePath2Img.get(movingPiece.getImgPath());
        g.drawImage(img, p.x - CELL_SIZE/2, p.y - CELL_SIZE/2, CELL_SIZE, CELL_SIZE, null);
    }

    /**
     * Draw all chess pieces on the chess board that follow the state of the game
     * @param g current Swing graphics
     */
    private void drawPiecesAll(Graphics g){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                IPiece p = iController.pieceAt(c, r);

                if(p != null){
//                    System.out.println(p);
                    drawImgAtTile(g, c, r, p.getImgPath());
                }

            }
        }

    }


    /**
     * Draw the image of a chess piece at a given tile
     * @param g current graphics
     * @param col column position of the tile to draw
     * @param row row position of the tile to draw
     * @param imgPath path to the image of the chess piece to draw on tile
     */
    private void drawImgAtTile(Graphics g, int col, int row, String imgPath){
        Image img = filePath2Img.get(imgPath);
        g.drawImage(img, col* CELL_SIZE + X0, row* CELL_SIZE + Y0, CELL_SIZE, CELL_SIZE, null);
    }



    /**
     * Draw the chess board with black and white tiles
     * @param g Current graphics
     */
    private void drawBoard(Graphics g){
        for(int r = 0; r < 8; r ++){
            for(int c = 0; c < 8; c++){
                drawSquare(g, c, r);
            }
        }

        if(highlightedCoords != null){
            for(Coordinate c:highlightedCoords){
                drawSquare(g, c.getCol(), c.getRow(), true);
            }
        }
    }

    /**
     * Draw one square (not highlighted by default) or tile of the chess board. Size of the tile is fixed
     * @param g Current graphics
     * @param col column position of the tile to draw
     * @param row row position of the tile to draw
     */
    private void drawSquare(Graphics g, int col, int row){
        if((col + row) % 2 == 1) {
            g.setColor(new Color(102,51,0));
        } else{
            g.setColor(Color.white);
        }
        g.fillRect(X0 + col* CELL_SIZE, Y0 + row* CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    /**
     * Draw one square (can be highlighted to show the possible moves of a chess piece) of the chess board. Size of the tile is fixed
     * @param g Current graphics
     * @param col column position of the tile to draw
     * @param row row position of the tile to draw
     * @param isHighlighted if the given tile should be highlighted
     */
    private void drawSquare(Graphics g, int col, int row, boolean isHighlighted){
        drawSquare(g, col, row);
        int higlightSize = CELL_SIZE * 9 /10;
        if(isHighlighted){
            Color c = new Color(0.2f, 0.5f, 0.2f, 0.5f);
            g.setColor(c);
            g.fillRect(X0 + col* CELL_SIZE + CELL_SIZE/2 - higlightSize/2, Y0 + row* CELL_SIZE + CELL_SIZE/2 - higlightSize/2, higlightSize, higlightSize);
        }
    }

    /**
     * Set the coordinates of the tiles to be highlighted to show the possible moves of the currently selected piece on the chess board
     * @param coords Coordinates of the possible legal moves of the currently selected chess piece
     */
    public void setHighlightedCoords(List<Coordinate> coords){
        this.highlightedCoords = coords;
    }
}
