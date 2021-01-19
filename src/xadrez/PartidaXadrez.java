package xadrez;

import tabuleiro.Board;
import tabuleiro.Posicao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
private Board board;
	
	public PartidaXadrez() {
		board = new Board(8, 8);
		iniciarPartida();
	}
	
	public XadrezPeca[][] getPecas(){
		XadrezPeca[][] mat = new XadrezPeca[board.getLinhas()][board.getColunas()];
		for (int i = 0; i<board.getLinhas(); i++) {
			for (int j = 0; j < board.getColunas(); j++) {
				mat[i][j] = (XadrezPeca) board.peca(i, j);
			}
		}
		return mat;
	}
	
	private void iniciarPartida() {
		board.inserirPeca(new Torre(board, Cor.WHITE), new Posicao(2, 1));
		board.inserirPeca(new Rei(board, Cor.BLACK), new Posicao(0, 4));
		board.inserirPeca(new Rei(board, Cor.WHITE), new Posicao(7, 4));
	}
	
}
