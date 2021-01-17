package xadrez.pecas;
import tabuleiro.Board;
import xadrez.Cor;
import xadrez.XadrezPeca;
public class Rei extends XadrezPeca{

	public Rei(Board board, Cor cor) {
		super(board, cor);
		
	}

@Override
public String toString() {
	return "R";
}
}
