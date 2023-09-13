package ChessPiece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import MVC.ChessModel;
import MVC.IChessModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
	private Piece n1;
	private Piece n2;
	private Piece r1;
	private Piece r2;
	private Piece p1;
	private Piece p2;
	private IChessModel chessModel1;

	@BeforeEach
	void setUp(){
		n1 = new Knight(3, 3, Player.WHITE);
		n2 = new Knight(4, 5, Player.BLACK);
		r1 = new Rook(3,4, Player.WHITE);
		r2 = new Rook(5,5, Player.BLACK);
		p1 = new Pawn(5,2, Player.WHITE);
		p2 = new Pawn(6,4, Player.BLACK);
		Piece[] piecesArray = new Piece[] {n1,n2,r1,r2, p1, p2};

		List<IPiece> piecesBox = new ArrayList<IPiece>(Arrays.asList(piecesArray));

		chessModel1 = new ChessModel(piecesBox);

	}

	@Test
	void setupPrint(){
		chessModel1.printActivePieces();
		System.out.println(chessModel1);

		assertTrue(chessModel1.getActivePieces().contains(new Knight(3, 3, Player.WHITE) ));
		assertTrue(chessModel1.getActivePieces().contains(new Knight(4, 5, Player.BLACK) ));
		assertTrue(chessModel1.getActivePieces().contains(new Rook(3,4, Player.WHITE) ));
		assertTrue(chessModel1.getActivePieces().contains(new Rook(5,5, Player.BLACK) ));
		assertTrue(chessModel1.getActivePieces().contains(new Pawn(5,2, Player.WHITE) ));
		assertTrue(chessModel1.getActivePieces().contains(new Pawn(6,4, Player.BLACK) ));
		assertTrue(chessModel1.getActivePieces().size() == 6);
	}

	@Test
	void moveTestN1(){
		n1.printLegalMoves(chessModel1);

		List<Coordinate> legalMovesN1 = n1.getLegalMoves(chessModel1);

		assertFalse(legalMovesN1.contains(new Coordinate(5,2))); 
		
		assertTrue(legalMovesN1.contains(new Coordinate(2,1 )));
		assertTrue(legalMovesN1.contains(new Coordinate(1,2 )));
		assertTrue(legalMovesN1.contains(new Coordinate(2,5 )));
		assertTrue(legalMovesN1.contains(new Coordinate(1,4 )));
		assertTrue(legalMovesN1.contains(new Coordinate(4,1 )));
		assertTrue(legalMovesN1.contains(new Coordinate(4,5 )));
		assertTrue(legalMovesN1.contains(new Coordinate(5,4 )));
		assertTrue(legalMovesN1.size() == 7);

	
	}
	
	@Test
	void moveTestN2(){
		n2.printLegalMoves(chessModel1);
		List<Coordinate> legalMovesN2 = n2.getLegalMoves(chessModel1);
		assertFalse(legalMovesN2.contains(new Coordinate(6,4)));

		assertTrue(legalMovesN2.contains(new Coordinate(3,3 )));
		assertTrue(legalMovesN2.contains(new Coordinate(2,4 )));
		assertTrue(legalMovesN2.contains(new Coordinate(3,7 )));
		assertTrue(legalMovesN2.contains(new Coordinate(2,6 )));
		assertTrue(legalMovesN2.contains(new Coordinate(5,3 )));
		assertTrue(legalMovesN2.contains(new Coordinate(5,7 )));
		assertTrue(legalMovesN2.contains(new Coordinate(6,6 )));
		
		assertTrue(legalMovesN2.size() == 7);
	
	}
	
}
