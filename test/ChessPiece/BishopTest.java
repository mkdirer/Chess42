package ChessPiece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MVC.ChessModel;
import MVC.IChessModel;

class BishopTest {

	private Piece b1;
    private Piece b2;
    private Piece r1;
    private Piece r2;
    private Piece p1;
    private Piece p2;
    private IChessModel chessModel1;
    private IChessModel chessModel2;
    List<Coordinate> legalMovesB1;
    List<Coordinate> legalMovesB2;

    @BeforeEach
    void setUp(){
        b1 = new Bishop(3, 5, Player.WHITE);
        b2 = new Bishop(5, 3, Player.BLACK);
        
        
        r1 = new Rook(5,7, Player.WHITE);
        r2 = new Rook(7,5, Player.BLACK);
        
        p1 = new Pawn(6,2, Player.WHITE);
        p2 = new Pawn(1,3, Player.BLACK);
        Piece[] piecesArray = new Piece[] {b1,b2,r1,r2,p1,p2};

        List<IPiece> activePieces = new ArrayList<IPiece>(Arrays.asList(piecesArray));

        chessModel1 = new ChessModel(activePieces);
        
        legalMovesB1 = b1.getLegalMoves(chessModel1);
        legalMovesB2 = b2.getLegalMoves(chessModel1);

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
        b1.printLegalMoves(chessModel1);
        b2.printLegalMoves(chessModel1);
        
        assertFalse(legalMovesB1.contains(new Coordinate(5,7)));
        assertFalse(legalMovesB2.contains(new Coordinate(7,5)));
    }
    
    @Test
    void moveTestEnemyCollision(){
        //TODO: write test for legal move. based on printed moves, print is correct here.
//        System.out.println(chessModel1);
        
        assertTrue(legalMovesB1.contains(new Coordinate(5,3)));
        assertTrue(legalMovesB1.contains(new Coordinate(1,3)));
        // cant move forward
        assertFalse(legalMovesB1.contains(new Coordinate(2,0)));
        
        
        assertTrue(legalMovesB2.contains(new Coordinate(6,2)));
        assertTrue(legalMovesB2.contains(new Coordinate(3,5)));
        // cant move forward
        assertFalse(legalMovesB2.contains(new Coordinate(2,6)));
    }
    
    void testNoCollision() {
    	assertTrue(legalMovesB1.contains(new Coordinate(4, 6)));
    	assertTrue(legalMovesB1.contains(new Coordinate(4, 4)));
    	assertTrue(legalMovesB1.contains(new Coordinate(5, 3)));
    	assertTrue(legalMovesB1.contains(new Coordinate(2, 6)));
    	assertTrue(legalMovesB1.contains(new Coordinate(1, 7)));
    	assertTrue(legalMovesB1.contains(new Coordinate(2, 4)));
    	assertTrue(legalMovesB1.contains(new Coordinate(1, 3)));
    }

}
