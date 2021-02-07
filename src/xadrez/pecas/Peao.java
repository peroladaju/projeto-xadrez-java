package xadrez.pecas;

import tabuleiro.Board;
import tabuleiro.Posicao;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {

	public Peao(Board board, Cor cor) {
		super(board, cor);
	}

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.WHITE) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getBoard().posicaoExiste(p) && !getBoard().existePeca(p) && getBoard().posicaoExiste(p2)
					&& !getBoard().existePeca(p2) && getContarMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getBoard().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getBoard().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} else {

			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getBoard().posicaoExiste(p) && !getBoard().existePeca(p) && getBoard().posicaoExiste(p2)
					&& !getBoard().existePeca(p2) && getContarMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getBoard().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getBoard().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}

		return mat;
	}

@Override
public String toString() {
	return "p";
}
}
