package xadrez.pecas;
import tabuleiro.Board;
import tabuleiro.Posicao;
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

private boolean podeMover(Posicao posicao) {
	XadrezPeca p = (XadrezPeca)getBoard().peca(posicao);
	return p == null || p.getCor() != getCor();
}

@Override
public boolean[][] movimentoPossivel() {
	boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
	
	Posicao p = new Posicao(0, 0);
	//Acima
	p.setValores(posicao.getLinha() - 1, posicao.getColuna());
	if(getBoard().posicaoExiste(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
	}
	//Abaio
	p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if(getBoard().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
	//Esquerda
	p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
	if(getBoard().posicaoExiste(p) && podeMover(p)) {
	mat[p.getLinha()][p.getColuna()] = true;
	}	
	
	//Direita
	p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
	if(getBoard().posicaoExiste(p) && podeMover(p)) {
	mat[p.getLinha()][p.getColuna()] = true;
	}
	
	//Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if(getBoard().posicaoExiste(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}
		
	//Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if(getBoard().posicaoExiste(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}	
		
		//Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getBoard().posicaoExiste(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if(getBoard().posicaoExiste(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
				}
		return mat;
	
}
}
