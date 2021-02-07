package xadrez.pecas;

import tabuleiro.Board;
import tabuleiro.Posicao;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Bispo extends XadrezPeca{

		public Bispo(Board board, Cor cor) {
			super(board, cor);
			
		}

		@Override
		 public String toString() {
			return "B";
		}

		@Override
		public boolean[][] movimentoPossivel() {
			boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
			Posicao p = new Posicao(0, 0);
			//Noroeste
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setValores(p.getLinha() - 1, p.getColuna() - 1);
			}
			if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//Nordeste
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setValores(p.getLinha() - 1, p.getColuna() + 1);
			}
			if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//Sudeste
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setValores(p.getLinha() + 1, p.getColuna() + 1);
			}
			if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}	
			
			//Sudoeste
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			while(getBoard().posicaoExiste(p) && !getBoard().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
			}
			if(getBoard().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
			
			return mat;
			
			
		}
		
	}



