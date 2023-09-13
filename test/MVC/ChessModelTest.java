package MVC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ChessPiece.Bishop;
import ChessPiece.IPiece;
import ChessPiece.King;
import ChessPiece.Knight;
import ChessPiece.Pawn;
import ChessPiece.Piece;
import ChessPiece.Player;
import ChessPiece.Queen;
import ChessPiece.Rook;
import Utils.ChessConstants;
import MVC.*;

class ChessModelTest {
	IChessModel chessModel1; // custom board, stale mate situation
	IChessModel chessModel2; // full board


	@BeforeEach
	void setUp(){
		List<IPiece> activePieces = new ArrayList<IPiece>();
        for (int i = 0; i < 2; i++) {
            activePieces.add(new Rook(0 + i * 7, 7, Player.BLACK, ChessConstants.bRook));
            activePieces.add(new Rook(0 + i * 7, 0, Player.WHITE, ChessConstants.wRook));

            activePieces.add(new Knight(1 + i * 5, 7, Player.BLACK, ChessConstants.bKnight));
            activePieces.add(new Knight(1 + i * 5, 0, Player.WHITE, ChessConstants.wKnight));

            activePieces.add(new Bishop(2 + i * 3, 7, Player.BLACK, ChessConstants.bBishop));
            activePieces.add(new Bishop(2 + i * 3, 0, Player.WHITE, ChessConstants.wBishop));
        }

        activePieces.add(new Queen(4, 7, Player.BLACK, ChessConstants.bQueen));
        activePieces.add(new Queen(3,3, Player.WHITE, ChessConstants.wQueen)); // white queen is making stale mate to black king

        activePieces.add(new King(3, 7, Player.BLACK, ChessConstants.bKing));
        activePieces.add(new King(3, 0, Player.WHITE, ChessConstants.wKing));

		chessModel1 = new ChessModel(activePieces);

		chessModel2 = new ChessModel();
	}
	
	@Test
	void playerTurnTest() {
		assertTrue(chessModel2.isValidTurn(new Pawn(1,1, Player.WHITE))); // white player goes first
		assertTrue(chessModel1.isValidTurn(new Rook(0,0, Player.WHITE))); // white player goes first
	}
	
	@Test
	void testPieceAt() {
		assertEquals(new Knight(1,0, Player.WHITE), chessModel2.pieceAt(1,0));
		assertEquals(new Queen(4,7, Player.BLACK), chessModel2.pieceAt(4,7));
	}
	
	@Test
	void inGameMovePieceTest() {
		chessModel2.movePiece(1,1, 1, 3); // move a white piece
		assertEquals(new Pawn(1,3, Player.WHITE), chessModel2.pieceAt(1,3)); // the white pawn is moved to new location
		assertFalse(chessModel2.getActivePieces().contains(new Pawn(1,1,Player.WHITE))); // the pawn at old location is removed
		System.out.println("white pawn moved");
		System.out.println(chessModel2);
		
		// black turn move
		assertFalse(chessModel2.isValidTurn(new Pawn(6,1, Player.WHITE))); //black turn, white cannot go
//		chessModel2.movePiece(6,7, 4, 0); // invalid move, nothing happen, the piece is not moved
//		assertTrue(chessModel2.getActivePieces().contains(new Knight(6,7,Player.BLACK)));
//		System.out.println(chessModel2);
		chessModel2.movePiece(6,7, 5, 5); // valid move
		assertFalse(chessModel2.getActivePieces().contains(new Knight(6,7,Player.BLACK)));
		assertTrue(chessModel2.getActivePieces().contains(new Knight(5,5,Player.BLACK)));
		System.out.println("Black knight moved");
		System.out.println(chessModel2);
		
	}
	
	@Test
	void inValidPlayerTurnTest(){
		try {
            chessModel2.movePiece(1,6, 1, 4);
            fail("Invalid move should have thrown exception");
        } catch (IllegalArgumentException iae) {
            //assertEquals("Position occupied", iae.getMessage());
            assertTrue(iae.getMessage().length() > 0);
        }
	}
	
	@Test
	void endGameTest() {
		// queen capture the king
		chessModel1.movePiece(3, 3, 3, 7); //Queen(3,3) // King(3, 7, Player.BLACK
		assertEquals(Player.WHITE,chessModel1.getWinner());
		assertTrue(chessModel1.isGameOver());		
	}
	
	@Test
    public void testMoveAttemptAfterGameOver() {
		chessModel1.movePiece(3, 3, 3, 7);
		assertThrows(IllegalStateException.class,
		             () -> chessModel1.movePiece(0,7,0,3),
		            "Expected doThing() to throw, but it didn't"
		    );
    }
	
	@Test
	void modifyActivePieceTest() {
		List<IPiece> activePiecesClone = chessModel1.getActivePieces();
		activePiecesClone.add(new Pawn(4,4,Player.WHITE));
		
		assertFalse(chessModel1.getActivePieces().contains(new Pawn(4,4,Player.WHITE)));
	}
	
	@Test 
	public void toStringTest() {
		String expectedBoard;
		expectedBoard = "  0 1 2 3 4 5 6 7\n"
                	  + "0 r n b k q b n r\n"
                	  + "1 p p p p p p p p\n"
                	  + "2 - - - - - - - -\n"
                	  + "3 - - - - - - - -\n"
                	  + "4 - - - - - - - -\n"
                	  + "5 - - - - - - - -\n"
                	  + "6 P P P P P P P P\n"
                	  + "7 R N B K Q B N R\n";
		assertEquals(expectedBoard, chessModel2.toString());	
	}	

}
