package xadrez.pecas;

import tabuleiro.Board;
import tabuleiro.Posicao;
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

	@Override
	public boolean[][] movimentoPossivel() {
		boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
		Posicao p = new Posicao(0, 0);
		//Acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Para baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}	
		
		//Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		p.setColuna(p.getColuna() + 1);
		}
		if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
		
		
	}
	
}
