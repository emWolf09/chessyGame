package com.chessy.engine.common;

import com.chessy.engine.player.BlackPlayer;
import com.chessy.engine.player.Player;
import com.chessy.engine.player.WhitePlayer;

public enum Alliance {
	WHITE{
		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public boolean isWhite() {
			return true;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return whitePlayer;
		}
		
		@Override
		public String toString() {
			return "white";
		}
	},BLACK{
		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return blackPlayer;
		}

		@Override
		public String toString() {
			return "black";
		}
	};	
	public abstract int getDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
	@Override
	public abstract String toString();
}
