package ChessPiece;
import MVC.*;

//import com.goldthumb.chess.MVC.ChessModel;

import java.util.List;

/**
 * Represent a chess piece on the chess board
 * @author tienbui
 *
 */
public interface IPiece {
	/**
	 * Calculate all of the legal coordiates for a chess piece on the current state
	 * @param chessModel Current state of the chess board
	 * @return list of all legal coordiates that a chess piece can move to
	 */
    List<Coordinate> getLegalMoves(IChessModel chessModel);
    
    /**
     * Retrieve link to image from resource folder according to a chess piece's rank and color
     * @return link to corresponding image representation on chess board 
     */
    String retrieveImgPath();
    
    /**
     * Each piece is represented by a letter when printing a the current state of the chess board
     * Capital letter represent black pieces, lower case letters represent the white pieces.
     * @return A letter representing the chess piece.
     */
    String getRepLetter();
    
    /**
     * Print all legal coordinates of a chess piece
     * @param chessModel current state of the game
     */
    void printLegalMoves(IChessModel chessModel);
    
    /**
     * Making a clone for a chess piece
     * @return a cloned version of the chess piece
     */
    Piece clone();
    
    /**
     * Get the player (black or white) of a chess piece
     * @return player of the chess piece
     */
    Player getPlayer();
    
    /**
     * Get the rank of a chess piece: pawn, rook, bishop, knight, queen, king
     * @return the rank of the chess piece
     */
    Rank getRank();
    
    /**
     * set the column position for the chess piece
     * @param col
     */
    void setCol(int col);
    
    /**
     * set the row position for the chess piece
     * @param row
     */
    void setRow(int row);
    
    /**
     * set the column position of the chess piece
     * @return
     */
    int getCol();
    
    /**
     * get the row position of the chess piece
     * @return
     */
    int getRow();
    
    /**
     * get the link to the image path of a piece
     * @return link to the image path of a piece
     */
	String getImgPath();
}
