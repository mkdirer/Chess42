package MVC;

import Utils.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import ChessPiece.*;

/**
 * Handling the logics of the game and showing current state of the game
 * @author tienbui
 *
 */
public class ChessModel implements IChessModel{

    private List<IPiece> activePieces;
    private Player playerInTurn;
    private Iterator<IPiece> it; // = piecesBox.iterator();
    private Pawn enPassantPawn = null;

    /**
     * Initialize a chess model with initial state and 32 chess pieces at the inital location
     */
    public ChessModel(){
        reset();
        it = activePieces.iterator();
    }

    /**
     * Initialize a chess board with custom locations of the chess pieces
     * @param activePieces List of active pieces on the chess board with positions
     */
    public ChessModel(List<IPiece> activePieces){
        this.activePieces = activePieces;
        this.playerInTurn = Player.WHITE;
        it = activePieces.iterator();
    }


    @Override
    public void reset(){
        activePieces = new ArrayList<IPiece>();
        for (int i = 0; i < 2; i++) {
            activePieces.add(new Rook(0 + i * 7, 7, Player.BLACK, ChessConstants.bRook));
            activePieces.add(new Rook(0 + i * 7, 0, Player.WHITE, ChessConstants.wRook));

            activePieces.add(new Knight(1 + i * 5, 7, Player.BLACK, ChessConstants.bKnight));
            activePieces.add(new Knight(1 + i * 5, 0, Player.WHITE, ChessConstants.wKnight));

            activePieces.add(new Bishop(2 + i * 3, 7, Player.BLACK, ChessConstants.bBishop));
            activePieces.add(new Bishop(2 + i * 3, 0, Player.WHITE, ChessConstants.wBishop));
        }

        for (int i = 0; i < 8; i++) {
            activePieces.add(new Pawn(i, 6, Player.BLACK, ChessConstants.bPawn));
            activePieces.add(new Pawn(i, 1, Player.WHITE, ChessConstants.wPawn));
        }

        activePieces.add(new Queen(4, 7, Player.BLACK, ChessConstants.bQueen));
        activePieces.add(new Queen(4, 0, Player.WHITE, ChessConstants.wQueen));

        activePieces.add(new King(3, 7, Player.BLACK, ChessConstants.bKing));
        activePieces.add(new King(3, 0, Player.WHITE, ChessConstants.wKing));

        playerInTurn = Player.WHITE;
//        for(ChessPiece.ChessPiece p:piecesBox) System.out.println(p);
    }


    @Override
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow) throws IllegalStateException, IllegalArgumentException {
    	
        if( isGameOver() ) throw new IllegalStateException("Game is over, cannot play");

        IPiece movingPiece = pieceAt(fromCol, fromRow);
//        System.out.println("line43 movePiece model " + movingPiece);
        if (movingPiece == null || (fromCol == toCol && fromRow == toRow)) {
            return; 
        }

        // if not in turn, player cannot move
        if(!isValidTurn(movingPiece)){
//            return;\
        	throw new IllegalArgumentException("Invalid player turn. Please wait for the other side to go first");
        }      
        

        // get possible moves of moving Piece
        // mutating the activePieces only done here
        // only check if toCol, toRow is allowed (contained in legalMoves)
        
        List<Coordinate> legalMoves = movingPiece.getLegalMoves(this);
        Coordinate destCoord = new Coordinate(toCol, toRow);
        if (!legalMoves.contains(destCoord)){
            System.out.print("moving piece " + movingPiece.toString() );
            System.out.println("not legal move at col " + toCol + " row " + toRow);
            return;
        }

        IPiece target = pieceAt(toCol, toRow);
        if (target != null) {
            if (target.getPlayer() == movingPiece.getPlayer()) {
                return;
            } else {
                activePieces.remove(target);
            }
        }
        movingPiece.setCol(toCol);
        movingPiece.setRow(toRow);

        // EN PASSANT HERE
        // a pawn capture an enemy's pawn with en passant move
        if(this.enPassantPawn != null &&
                movingPiece instanceof Pawn &&
                movingPiece.getPlayer() != this.enPassantPawn.getPlayer() &&
                toCol == enPassantPawn.getCol() &&
                toRow == (enPassantPawn.getRow() - 1*enPassantPawn.getPlayer().getDirection())
        )   activePieces.remove(enPassantPawn);

        // check Queen promotion of a pawn
        if(movingPiece instanceof Pawn ){
            if(checkPawnPromotion(movingPiece, toRow) ){
                activePieces.remove(movingPiece);
                activePieces.add(new Queen(toCol, toRow,
                        movingPiece.getPlayer() ));
            }
        }

        updateEnPassantPawn(movingPiece, fromCol, fromRow, toCol, toRow);
        playerInTurn = (playerInTurn == Player.WHITE ? Player.BLACK : Player.WHITE);
    }

    @Override
    public IPiece pieceAt(int col, int row) { // use recursion to get the chess piece at location col, row
    	int nActivePieces = activePieces.size();
    	return pieceAtHelper(col, row, 0, nActivePieces-1);
    }
    
    /**
     * Recursively check the piece at location [col, row] in the chess board tile.  Null if the tile is empty
     * @param col Column of the chess board tile
     * @param row Row of the chess board tile
     * @param left Left index of the activePieces list from which the search is carried out 
     * @param right Right index of the activePieces list until which the search is carried out 
     * @return The piece at location [col, row] in the chess board tile. Null if the tile is empty
     */
    private IPiece pieceAtHelper(int col, int row, int left, int right) {
    	 if (right < left)
            return null;
         if (activePieces.get(left).getCol() == col && activePieces.get(left).getRow() == row)
            return activePieces.get(left);
         if (activePieces.get(right).getCol() == col && activePieces.get(right).getRow() == row)
            return activePieces.get(right);
         return pieceAtHelper(col, row, left + 1, right - 1);
    }

    @Override
    public boolean isValidTurn(IPiece movingPiece){
        return movingPiece.getPlayer() == this.playerInTurn ;
    }

    @Override
    public boolean isEmptySquare(int col, int row){
        return pieceAt(col, row) == null;
    }

    @Override
    public String toString() {
        String out = "";

        out += "  0 1 2 3 4 5 6 7\n";
        for (int row = 0; row < 8; row++) {
            out += "" + row;
            for (int col = 0; col < 8; col++) {
                IPiece p = pieceAt(col, row);
                if (p == null) {
                    out += " -";
                } else {
                    out += " ";
                    out += p.getRepLetter();
                }
            }
            out += "\n";
        }


        return out;
    }

    @Override
    public void printActivePieces() {
        if (!it.hasNext()) return; // hasNext
        System.out.println(it.next());
        printActivePieces(); // recursion
    }


    @Override
    public List<IPiece> getActivePieces(){
        List<IPiece> piecesBoxClone = new ArrayList<IPiece>();
        for(IPiece p: activePieces){
            piecesBoxClone.add((p.clone()));
        }
        return piecesBoxClone;
    }


    @Override
    public Player getWinner() {
        boolean existWhiteKing = false;
        boolean existBlackKing = false;

        for(IPiece p: activePieces){
            if((p instanceof King) && p.getPlayer() == Player.WHITE) existWhiteKing = true;
            if((p instanceof King) && p.getPlayer() == Player.BLACK) existBlackKing = true;
        }

        if (existWhiteKing & !existBlackKing ) return Player.WHITE;
        if (!existWhiteKing & existBlackKing ) return Player.BLACK;
        return null;
    }

    // Game over when a king is captured
    @Override
    public boolean isGameOver() {
        return getWinner() != null;
    }

    // handle special moves of a pawn
    private boolean checkPawnPromotion(IPiece movingPiece, int toRow){
        if(! (movingPiece instanceof Pawn)) return false;
        if((movingPiece.getPlayer() == Player.WHITE && toRow == 7) ||
                (movingPiece.getPlayer() == Player.BLACK && toRow == 0) ) return true;
        return false;
    }

    private boolean isPawnJump(IPiece movingPiece, int fromCol, int fromRow, int toCol, int toRow){
        if (! (movingPiece instanceof Pawn)) return false;
        return (fromCol == toCol && Math.abs(toRow - fromRow) == 2);
    }

    private void updateEnPassantPawn(IPiece movingPiece, int fromCol, int fromRow, int toCol, int toRow){
        if(! (movingPiece instanceof Pawn)){
            this.enPassantPawn = null;
            return;
        }
        if(isPawnJump(movingPiece, fromCol, fromRow, toCol, toRow)){
            this.enPassantPawn = (Pawn)movingPiece;
            return;
        } else{
            this.enPassantPawn = null;
        }
        return;
    }

//    @Override
    public Pawn getEnPassantPawn(){
        return this.enPassantPawn;
    }
}
