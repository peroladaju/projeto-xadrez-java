package tabuleiro;

public abstract class Peca {
	protected Posicao posicao;
	private Board board;
	
	public Peca(Board board) {
		this.board = board;
		
		posicao = null;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] movimentoPossivel();
	
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentoPossivel()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean existeUmMovimentoPossivel() {
		boolean[][] mat = movimentoPossivel();
		for (int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
