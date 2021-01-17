package xadrez;

import tabuleiro.Board;
import tabuleiro.Peca;

public class XadrezPeca extends Peca {
	
	private Cor cor;

	public XadrezPeca(Board board, Cor cor) {
		super(board);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	
	
	
}
