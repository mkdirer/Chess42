package MVC;

import ChessPiece.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Controller of the Java program. Takes input from user, send input to model and get information from model to tell the view to display
 * @author tienbui
 *
 */
public interface IController {
	/**
	 * Get the mouse drag point from mouse
	 * @return mouse drag point
	 */
    Point getMouseDragPoint();
    
    /**
     * Get the currently selected chess piece from the mouse movement
     * @return the currently selected chess piece by the mouse movement
     */
    IPiece getMovingPiece();

    /**
     * Get the piece at a chess board tile. Null if the tile is empty
     * @param col Column of the chess board tile
     * @param row Row of the chess board tile
     * @return the Piece at location [col, row]. Null if the tile is empty
     */
    IPiece pieceAt(int col, int row);
    
    /**
     * Move a chess piece from one tile to another tile. If the tile is empty, do nothing.
     * Exceptions from chess model are handled here
     * @param fromCol Column of the initial tile 
     * @param fromRow Row of the initial tile
     * @param toCol Column of the destination tile
     * @param toRow Row of the destination tile
     */
    void movePiece(int fromCol, int fromRow, int toCol, int toRow);
    
    /**
     * Check if the game is over (when a king is captured)
     * @return if the game is over
     */
    boolean isGameOver();
}
