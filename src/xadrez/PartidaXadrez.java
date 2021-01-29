package xadrez;

import tabuleiro.Board;
import tabuleiro.Peca;
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
	public XadrezPeca excutarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao ) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		Peca capturaPeca = makeMove(origem, destino);
		return (XadrezPeca)capturaPeca;
	}
	
	private Peca makeMove(Posicao origem, Posicao destino) {
		Peca p = board.removePeca(origem);
		Peca capturaPeca = board.removePeca(destino);
		board.inserirPeca(p, destino);
		return capturaPeca;
	}
	private void validarOrigemPosicao(Posicao posicao) {
		if (!board.existePeca(posicao)) {
			throw new ExcecaoXadrez("Não existe peça na posição");
		}
		if (!board.peca(posicao).existeUmMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não existe movimentos possíveis para a peças escolhida");
		}
	}
	
	private void inserirNovaPeca(char coluna, int linha, XadrezPeca peca) {
		board.inserirPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void iniciarPartida() {
		inserirNovaPeca('c', 1, new Torre(board, Cor.WHITE));
		inserirNovaPeca('c', 2, new Torre(board, Cor.WHITE));
		inserirNovaPeca('d', 2, new Torre(board, Cor.WHITE));
		inserirNovaPeca('e', 2, new Torre(board, Cor.WHITE));
		inserirNovaPeca('e', 1, new Torre(board, Cor.WHITE));
		inserirNovaPeca('d', 1, new Rei(board, Cor.WHITE));
		
		inserirNovaPeca('c', 7, new Torre(board, Cor.BLACK));
		inserirNovaPeca('c', 8, new Torre(board, Cor.BLACK));
		inserirNovaPeca('d', 7, new Torre(board, Cor.BLACK));
		inserirNovaPeca('e', 7, new Torre(board, Cor.BLACK));
		inserirNovaPeca('e', 8, new Torre(board, Cor.BLACK));
		inserirNovaPeca('d', 8, new Rei(board, Cor.BLACK));
	}
	
	
}
