package xadrez;

import tabuleiro.Board;
import tabuleiro.Peca;
import tabuleiro.Posicao;

public abstract class XadrezPeca extends Peca {
	
	private Cor cor;
	private int contarMovimentos;

	public XadrezPeca(Board board, Cor cor) {
		super(board);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	public int getContarMovimentos() {
		return contarMovimentos;
	}
	
	public void incrementaMovimentos() {
		contarMovimentos++;
	}
	
	public void decrementaMovimentos() {
		contarMovimentos--;
	}

	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	protected boolean existePecaOponente(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getBoard().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
	
}
