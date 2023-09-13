package MVC;

import ChessPiece.*;

import java.util.List;

/**
 * Handling the logics of the game and showing current state of the game
 * @author tienbui
 *
 */
public interface IChessModel {
	/**
	 * Resetting the chess game. Put 32 chess pieces to the intial locations
	 */
    void reset();
    
    /**
     * Move a chess piece from one tile to another tile. If the tile is empty, do nothing
     * @param fromCol Column of the initial tile 
     * @param fromRow Row of the initial tile
     * @param toCol Column of the destination tile
     * @param toRow Row of the destination tile
     * @throws IllegalStateException When attempting to move a piece after the game ends
     * @throws IllegalArgumentException When a player makes a move at the wrong player turn
     */
    void movePiece(int fromCol, int fromRow, int toCol, int toRow) throws IllegalStateException, IllegalArgumentException ;
    
    /**
     * Get the piece at a chess board tile. Null if the tile is empty
     * @param col Column of the chess board tile
     * @param row Row of the chess board tile
     * @return the Piece at location [col, row]. Null if the tile is empty
     */
    IPiece pieceAt(int col, int row);
    
    /**
     * Check if a square/tile is empty
     * @param col Column of the chess board tile
     * @param row Row of the chess board tile
     * @return if the tile is empty (no piece at the tile)
     */
    boolean isEmptySquare(int col, int row);
    
    /**
     * Print the active pieces in the Chess board. For testing purpose
     */
    void printActivePieces();
    
    /**
     * Get the cloned list of the active pieces of a chess board. To avoid modification of the chess board
     * @return a cloned list the active pieces of a chess board
     */
    List<IPiece> getActivePieces();

    /**
     * Get the winner of the game. Null if no winer
     * @return the winner of the game
     */
    Player getWinner();
    
    /**
     * Check if the game is over (when a king is captured)
     * @return if the game is over
     */
    boolean isGameOver();
    
    /**
     * Check if the currently selected piece is on valid player turn
     * @param movingPiece Currently selected piece
     * @return if the currently selected piece is on valid player turn
     */
    boolean isValidTurn(IPiece movingPiece);
    
    
}
