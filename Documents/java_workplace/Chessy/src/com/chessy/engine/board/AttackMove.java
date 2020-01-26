package com.chessy.engine.board;

import com.chessy.engine.pieces.Piece;

public class AttackMove extends Move{
	final Piece attackedPiece;
	public AttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
		super(board, movedPiece, destinationCordinate);
		this.attackedPiece = attackedPiece;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attackedPiece == null) ? 0 : attackedPiece.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttackMove other = (AttackMove) obj;
		if (attackedPiece == null) {
			if (other.attackedPiece != null)
				return false;
		} else if (!attackedPiece.equals(other.attackedPiece))
			return false;
		return true;
	}

	
	@Override
	public boolean isAttack() {
		return true;
	}
	
	 @Override
	 public Piece getAttackPiece() {
			return attackedPiece;
	 }
	 
	 
	 @Override
		public String toString() {
			return "AttackMove";
		}
	 
	 public static class PawnAttackMove extends AttackMove{
		final Piece attackedPiece;
		public PawnAttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCordinate,attackedPiece);
			this.attackedPiece = attackedPiece;
		}
		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}	
		@Override
		public String toString() {
			return "PawnAttack";
		}
			
	}
		
		
	public static class PawnEnPassantAttackMove extends PawnAttackMove{
		final Piece attackedPiece;
		public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCordinate,attackedPiece);
			this.attackedPiece = attackedPiece;
		}
		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String toString() {
			return "EnPassantMove";
		}
	}
}
