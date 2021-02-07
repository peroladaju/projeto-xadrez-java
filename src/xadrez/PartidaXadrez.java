package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Board;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

private int virar;
private Cor jodadorAtual;
private Board board;
private boolean check;
private boolean checkMate;

private List<Peca> pecasDoTabuleiro = new ArrayList<>();
private List<Peca> pecasCapturadas = new ArrayList<>();	
	
public PartidaXadrez() {
		board = new Board(8, 8);
		virar = 1;
		jodadorAtual = Cor.WHITE;
		iniciarPartida();
	}
	
	
	
	public int getVirar() {
		return virar;
	}


	public Cor getJodadorAtual() {
		return jodadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez origemPosicao){
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return board.peca(posicao).movimentoPossivel();
	}
	
	public XadrezPeca excutarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao ) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarPosicaoDestino(origem.equals(destino));
		Peca capturaPeca = makeMove(origem, destino);
		
		if (testeCheck(jodadorAtual)) {
			undoMove(origem, destino, capturaPeca);
			throw new ExcecaoXadrez("Você não pode se colocar em check!");
		}
		
		check = (testeCheck(oponente(jodadorAtual))) ? true : false;
		
		if(testeCheckMate(oponente(jodadorAtual))) {
			checkMate = true;
		}else {
			proximoJogador();
		
		}
		
		
		return (XadrezPeca)capturaPeca;
		
		
	}
	
	private Peca makeMove(Posicao origem, Posicao destino) {
		XadrezPeca p = (XadrezPeca)board.removePeca(origem);
		p.incrementaMovimentos();
		Peca capturaPeca = board.removePeca(destino);
		board.inserirPeca(p, destino);
		
		if (capturaPeca != null) {
			pecasDoTabuleiro.remove(capturaPeca);
			pecasCapturadas.add(capturaPeca);
		}
		return capturaPeca;
	}
	
	private void undoMove(Posicao origem, Posicao destino, Peca capturaPeca) {
		XadrezPeca p = (XadrezPeca)board.removePeca(destino);
		p.decrementaMovimentos();
		board.inserirPeca(p, origem);
		
		if(capturaPeca != null) {
			board.inserirPeca(capturaPeca, destino);
			pecasCapturadas.remove(capturaPeca);
			pecasDoTabuleiro.add(capturaPeca);	
		}
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if (!board.existePeca(posicao)) {
			throw new ExcecaoXadrez("Não existe peça na posição");
		}
		if(jodadorAtual != ((XadrezPeca)board.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("A peça escolhida não é sua.");
		}
				
		if (!board.peca(posicao).existeUmMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não existe movimentos possíveis para a peças escolhida");
		}
	}
	private void validarPosicaoDestino(boolean equals) {
		// TODO Auto-generated method stub
		
	}
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!board.peca(origem).movimentoPossivel(destino)) {
			throw new ExcecaoXadrez("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void proximoJogador() {
		virar++;
		jodadorAtual = (jodadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == cor.WHITE) ? cor.BLACK : cor.WHITE;
	}
	
	private XadrezPeca Rei(Cor cor) {
		List<Peca> list = pecasDoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (XadrezPeca)p;
			}
		}
		throw new IllegalStateException("Não há" + cor + "do rei no tabuleiro");
	}
	
	private boolean testeCheck(Cor cor) {
		Posicao PosicaoRei = Rei(cor).getPosicaoXadrez().toPosicao();
		List <Peca> pecasOpnente = pecasDoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p :pecasOpnente) {
			boolean [][] mat = p.movimentoPossivel();
			if(mat[PosicaoRei.getLinha()][PosicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean testeCheckMate(Cor cor) {
		if(!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasDoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean[][] mat = p.movimentoPossivel();
			for(int linha = 0; linha <board.getLinhas(); linha++) {
				for(int coluna = 0; coluna <board.getColunas(); coluna++) {
					if(mat[linha][coluna]) {
						Posicao origem = ((XadrezPeca)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(linha, coluna);
						Peca capturaPeca = makeMove(origem, destino);
						boolean testeCheck = testeCheck(cor);
						undoMove(origem, destino, capturaPeca);
						if(!testeCheck) {
							return false;
						}
					}
				}
			}
			
		}
		return true;
	}
	
	private void inserirNovaPeca(char coluna, int linha, XadrezPeca peca) {
		board.inserirPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasDoTabuleiro.add(peca);
	}
	
	private void iniciarPartida() {
		inserirNovaPeca('a', 1, new Torre(board, Cor.WHITE));
		inserirNovaPeca('b', 1, new Cavalo(board, Cor.WHITE));
		inserirNovaPeca('c', 1, new Bispo(board, Cor.WHITE));
		inserirNovaPeca('f', 1, new Bispo(board, Cor.WHITE));
		inserirNovaPeca('g', 1, new Cavalo(board, Cor.WHITE));
		inserirNovaPeca('h', 1, new Torre(board, Cor.WHITE));
		inserirNovaPeca('e', 1, new Rei(board, Cor.WHITE));
		inserirNovaPeca('a', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('b', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('c', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('d', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('e', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('f', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('g', 2, new Peao(board, Cor.WHITE));
		inserirNovaPeca('h', 2, new Peao(board, Cor.WHITE));
		
		
		
		inserirNovaPeca('a', 8, new Torre(board, Cor.BLACK));
		inserirNovaPeca('b', 8, new Cavalo(board, Cor.BLACK));
		inserirNovaPeca('c', 8, new Bispo(board, Cor.BLACK));
		inserirNovaPeca('e', 8, new Rei(board, Cor.BLACK));
		inserirNovaPeca('f', 8, new Bispo(board, Cor.BLACK));
		inserirNovaPeca('g', 8, new Cavalo(board, Cor.BLACK));
		inserirNovaPeca('h', 8, new Torre(board, Cor.BLACK));
		inserirNovaPeca('a', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('b', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('c', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('d', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('e', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('f', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('g', 7, new Peao(board, Cor.BLACK));
		inserirNovaPeca('h', 7, new Peao(board, Cor.BLACK));
		
	}
	
	
}
