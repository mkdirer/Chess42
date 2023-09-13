package ChessPiece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MVC.ChessModel;
import MVC.IChessModel;

class RookTest {

	private Piece q1;
    private Piece q2;
    private Piece r1;
    private Piece r2;
    private Piece p1;
    private Piece p2;
    private IChessModel chessModel1;
    private IChessModel chessModel2;
    List<Coordinate> legalMovesR1;
    List<Coordinate> legalMovesR2;

    @BeforeEach
    void setUp(){
        q1 = new Queen(4, 5, Player.WHITE);
        q2 = new Queen(5, 3, Player.BLACK);
        r1 = new Rook(3,2, Player.WHITE);
        r2 = new Rook(5,5, Player.BLACK);
        p1 = new Pawn(6,2, Player.WHITE);
        p2 = new Pawn(7,5, Player.WHITE);
        Piece[] piecesArray = new Piece[] {q1,q2,r1,r2,p1,p2};

        List<IPiece> activePieces = new ArrayList<IPiece>(Arrays.asList(piecesArray));

        chessModel1 = new ChessModel(activePieces);
        
        legalMovesR1 = r1.getLegalMoves(chessModel1);
        legalMovesR2 = r2.getLegalMoves(chessModel1);

    }

    @Test
    void setupPrint(){
        chessModel1.printActivePieces();
        System.out.println(chessModel1);
    }

    @Test
    void moveTestAllyCollision(){
        //TODO: write test for legal move. based on printed moves, print is correct here.
//        System.out.println(chessModel1);
        q1.printLegalMoves(chessModel1);
        q2.printLegalMoves(chessModel1);
        
        assertFalse(legalMovesR1.contains(new Coordinate(6,2)));
        assertFalse(legalMovesR2.contains(new Coordinate(5,3)));
    }
    
    @Test
    void moveTestEnemyCollision(){
        //TODO: write test for legal move. based on printed moves, print is correct here.

        assertTrue(legalMovesR2.contains(new Coordinate(7,5)));
        
        assertFalse(legalMovesR2.contains(new Coordinate(3,5)));
    }
    
    void testNoCollision() {
    	for(int i =0; i < 8; i++) {
    		if( i != r1.getRow()) {
    			assertTrue(legalMovesR1.contains(new Coordinate(r1.getCol(), i)));
    		}
    	}
    }

}
