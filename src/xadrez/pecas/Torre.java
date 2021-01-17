package xadrez.pecas;

import tabuleiro.Board;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Torre extends XadrezPeca{

	public Torre(Board board, Cor cor) {
		super(board, cor);
		
	}

	@Override
	 public String toString() {
		return "T";
	}
}
