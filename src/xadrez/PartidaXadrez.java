package xadrez;

import tabuleiro.Board;
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
	private void inserirPrimeiraPeca(char coluna, int linha, XadrezPeca peca) {
		board.inserirPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void iniciarPartida() {
		inserirPrimeiraPeca('c', 1, new Torre(board, Cor.WHITE));
		inserirPrimeiraPeca('c', 2, new Torre(board, Cor.WHITE));
		inserirPrimeiraPeca('d', 2, new Torre(board, Cor.WHITE));
		inserirPrimeiraPeca('e', 2, new Torre(board, Cor.WHITE));
		inserirPrimeiraPeca('e', 1, new Torre(board, Cor.WHITE));
		inserirPrimeiraPeca('d', 1, new Rei(board, Cor.WHITE));
		
		inserirPrimeiraPeca('c', 7, new Torre(board, Cor.BLACK));
		inserirPrimeiraPeca('c', 8, new Torre(board, Cor.BLACK));
		inserirPrimeiraPeca('d', 7, new Torre(board, Cor.BLACK));
		inserirPrimeiraPeca('e', 7, new Torre(board, Cor.BLACK));
		inserirPrimeiraPeca('e', 8, new Torre(board, Cor.BLACK));
		inserirPrimeiraPeca('d', 8, new Rei(board, Cor.BLACK));
	}
	
	
}
