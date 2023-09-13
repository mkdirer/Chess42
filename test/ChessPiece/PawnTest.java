package ChessPiece;

import MVC.ChessModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class PawnTest {
    private Piece n1;
    private Piece n2;
    private Piece r1;
    private Piece r2;
    private Piece p1;
    private Piece p2;
    private Piece p3;
    private Piece p4;
    private Piece p5;
    private Piece p6;
    private Piece p7;
    private Piece p8;
    
    private ChessModel chessModel1;
    private ChessModel chessModel2;

    @BeforeEach
    void setUp(){
        n1 = new Knight(5, 3, Player.WHITE);
        n2 = new Knight(1, 2, Player.BLACK);
        r1 = new Rook(1,5, Player.WHITE);
        r2 = new Rook(5,5, Player.BLACK);

        p1 = new Pawn(5,1, Player.WHITE);
        p2 = new Pawn(0,6, Player.BLACK);

        p3 = new Pawn(6,2, Player.WHITE);
        p4 = new Pawn(6,4, Player.BLACK);
        

        p5 = new Pawn(2,1, Player.WHITE);
        p6 = new Pawn(2,6, Player.BLACK);
        p7 = new Pawn(3,3, Player.BLACK); // en passant pawn
        p8 = new Pawn(7,6, Player.WHITE); // to be promoted to queen
        
        
        Piece[] piecesArray = new Piece[] {n1, n2, r1, r2, p1, p2, p3, p4, p5, p6, p7, p8};

        List<IPiece> activePieces = new ArrayList<IPiece>(Arrays.asList(piecesArray));

		chessModel1 = new ChessModel(activePieces);

        chessModel2 = new ChessModel();
    }

    @Test
    void setupPrint(){
        chessModel1.printActivePieces();
        System.out.println(chessModel1);
    }

    @Test
    void firstMoveTest(){
    	assertTrue(((Pawn)p1).isFirstMove());
    	assertTrue(((Pawn)p2).isFirstMove());
    	assertFalse(((Pawn)p3).isFirstMove());
    	assertFalse(((Pawn)p4).isFirstMove());
    	assertTrue(((Pawn)p5).isFirstMove());
    	assertTrue(((Pawn)p6).isFirstMove());
    	assertFalse(((Pawn)p7).isFirstMove());
    }
    
    @Test
    void pawnJumpTest() {
    	assertTrue(p2.getLegalMoves(chessModel1).contains(new Coordinate(0,5)));
    	assertTrue(p2.getLegalMoves(chessModel1).contains(new Coordinate(0,4)));
    	
    	assertTrue(p5.getLegalMoves(chessModel1).contains(new Coordinate(2,2)));
    	assertTrue(p5.getLegalMoves(chessModel1).contains(new Coordinate(2,3)));
    	
    }

    @Test
    void pawnAttackTest() {
    	assertTrue(p4.getLegalMoves(chessModel1).contains(new Coordinate(5,3)));
    	assertTrue(p4.getLegalMoves(chessModel1).contains(new Coordinate(6,3)));
    	
    	assertTrue(p5.getLegalMoves(chessModel1).contains(new Coordinate(1,2)));
    }
    
    @Test
    void enPassantTest() {
    	chessModel1.movePiece(2,1, 2, 3); // move p5 for the first jump
    	assertTrue(p7.getLegalMoves(chessModel1).contains(new Coordinate(2,2))); // enPassent move
    	chessModel1.movePiece(3,3, 2, 2); // perform the en passent attack move
    	assertFalse(chessModel1.getActivePieces().contains(p5.clone())); // p5 is attacked, removed from the board    	
    }
    
    @Test
    void queenPromotionTest() {
    	chessModel1.movePiece(7, 6, 7, 7);
    	assertTrue(chessModel1.getActivePieces().contains(new Queen(7,7,Player.WHITE)));
    	assertFalse(chessModel1.getActivePieces().contains(new Pawn(7,7,Player.WHITE)));
    }
    
    @Test
    void testCollision() {
    	assertFalse(p1.getLegalMoves(chessModel1).contains(new Coordinate(5,3)));
    }

    @Test
    void moveTestSinglePieces(){
        //TODO: write test for legal move. based on printed moves, print is correct here.
    	

        p1.printLegalMoves(chessModel1);
        p2.printLegalMoves(chessModel1);
        p3.printLegalMoves(chessModel1);
        p4.printLegalMoves(chessModel1);
        p5.printLegalMoves(chessModel1);
    }

    @Test
    void moveTestFullBoard(){
        //TODO: write test for legal move. based on printed moves, print is correct here.
        System.out.println(chessModel2);

        List<IPiece> piecesBoxClone = chessModel2.getActivePieces();
        for(IPiece p:piecesBoxClone){
            if(p instanceof Pawn){
                System.out.println(p);
                p.printLegalMoves(chessModel2);
            }
        }
    }
}