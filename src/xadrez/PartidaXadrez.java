package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Board;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int virar;
	private Cor jodadorAtual;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private XadrezPeca enPassantVulnerable;

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
	
	public XadrezPeca getEnPassantVunerable() {
		return enPassantVulnerable;
	}

	public XadrezPeca[][] getPecas() {
		XadrezPeca[][] mat = new XadrezPeca[board.getLinhas()][board.getColunas()];
		for (int i = 0; i < board.getLinhas(); i++) {
			for (int j = 0; j < board.getColunas(); j++) {
				mat[i][j] = (XadrezPeca) board.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return board.peca(posicao).movimentoPossivel();
	}

	public XadrezPeca excutarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturaPeca = makeMove(origem, destino);

		if (testeCheck(jodadorAtual)) {
			undoMove(origem, destino, capturaPeca);
			throw new ExcecaoXadrez("Você não pode se colocar em check!");
		}

		XadrezPeca moverPeca = (XadrezPeca)board.peca(destino);
		
		check = (testeCheck(oponente(jodadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jodadorAtual))) {
			checkMate = true;
		} else {
			proximoJogador();

		}
		
		//Jogada especial: en passant
		if(moverPeca instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulnerable = moverPeca;
		}else {
			enPassantVulnerable = null;
		}
		
		
		return (XadrezPeca) capturaPeca;

	}

	private Peca makeMove(Posicao origem, Posicao destino) {
		XadrezPeca p = (XadrezPeca) board.removePeca(origem);
		p.incrementaMovimentos();
		Peca capturaPeca = board.removePeca(destino);
		board.inserirPeca(p, destino);

		if (capturaPeca != null) {
			pecasDoTabuleiro.remove(capturaPeca);
			pecasCapturadas.add(capturaPeca);
		}
		// Movimento especiao: rock pequino
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca torre = (XadrezPeca) board.removePeca(origemT);
			board.inserirPeca(torre, destinoT);
			torre.incrementaMovimentos();
		}

		// Movimento especiao: rock grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca torre = (XadrezPeca) board.removePeca(origemT);
			board.inserirPeca(torre, destinoT);
			torre.incrementaMovimentos();
		}
		
		//movimento especial: en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && capturaPeca == null) {
				Posicao peaoPosicao;
				if(p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturaPeca = board.removePeca(peaoPosicao);
				pecasCapturadas.add(capturaPeca);
				pecasDoTabuleiro.remove(capturaPeca);
				
			}
		}
		
		return capturaPeca;
	}

	private void undoMove(Posicao origem, Posicao destino, Peca capturaPeca) {
		XadrezPeca p = (XadrezPeca) board.removePeca(destino);
		p.decrementaMovimentos();
		board.inserirPeca(p, origem);

		if (capturaPeca != null) {
			board.inserirPeca(capturaPeca, destino);
			pecasCapturadas.remove(capturaPeca);
			pecasDoTabuleiro.add(capturaPeca);
		}

		// Movimento especiao: rock pequino
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca torre = (XadrezPeca) board.removePeca(destinoT);
			board.inserirPeca(torre, origem);
			torre.decrementaMovimentos();
		}

		// Movimento especiao: rock grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca torre = (XadrezPeca) board.removePeca(destinoT);
			board.inserirPeca(torre, origemT);
			torre.decrementaMovimentos();
		}
		// Movimento especiao: en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && capturaPeca == enPassantVulnerable) {
				XadrezPeca peao = (XadrezPeca)board.removePeca(destino);
				Posicao peaoPosicao;
				if(p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				}else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				board.inserirPeca(peao, peaoPosicao);
				
				
			}
		}
	}

	private void validarOrigemPosicao(Posicao posicao) {
		if (!board.existePeca(posicao)) {
			throw new ExcecaoXadrez("Não existe peça na posição");
		}
		if (jodadorAtual != ((XadrezPeca) board.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("A peça escolhida não é sua.");
		}

		if (!board.peca(posicao).existeUmMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não existe movimentos possíveis para a peças escolhida");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!board.peca(origem).movimentoPossivel(destino)) {
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
		List<Peca> list = pecasDoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (XadrezPeca) p;
			}
		}
		throw new IllegalStateException("Não há" + cor + "do rei no tabuleiro");
	}

	private boolean testeCheck(Cor cor) {
		Posicao PosicaoRei = Rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOpnente = pecasDoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOpnente) {
			boolean[][] mat = p.movimentoPossivel();
			if (mat[PosicaoRei.getLinha()][PosicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	public boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasDoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentoPossivel();
			for (int linha = 0; linha < board.getLinhas(); linha++) {
				for (int coluna = 0; coluna < board.getColunas(); coluna++) {
					if (mat[linha][coluna]) {
						Posicao origem = ((XadrezPeca) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(linha, coluna);
						Peca capturaPeca = makeMove(origem, destino);
						boolean testeCheck = testeCheck(cor);
						undoMove(origem, destino, capturaPeca);
						if (!testeCheck) {
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
		inserirNovaPeca('d', 1, new Dama(board, Cor.WHITE));
		inserirNovaPeca('f', 1, new Bispo(board, Cor.WHITE));
		inserirNovaPeca('g', 1, new Cavalo(board, Cor.WHITE));
		inserirNovaPeca('h', 1, new Torre(board, Cor.WHITE));
		inserirNovaPeca('e', 1, new Rei(board, Cor.WHITE, this));
		inserirNovaPeca('a', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('b', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('c', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('d', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('e', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('f', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('g', 2, new Peao(board, Cor.WHITE, this));
		inserirNovaPeca('h', 2, new Peao(board, Cor.WHITE, this));

		inserirNovaPeca('a', 8, new Torre(board, Cor.BLACK));
		inserirNovaPeca('b', 8, new Cavalo(board, Cor.BLACK));
		inserirNovaPeca('c', 8, new Bispo(board, Cor.BLACK));
		inserirNovaPeca('d', 8, new Dama(board, Cor.BLACK));
		inserirNovaPeca('e', 8, new Rei(board, Cor.BLACK, this));
		inserirNovaPeca('f', 8, new Bispo(board, Cor.BLACK));
		inserirNovaPeca('g', 8, new Cavalo(board, Cor.BLACK));
		inserirNovaPeca('h', 8, new Torre(board, Cor.BLACK));
		inserirNovaPeca('a', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('b', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('c', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('d', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('e', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('f', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('g', 7, new Peao(board, Cor.BLACK, this));
		inserirNovaPeca('h', 7, new Peao(board, Cor.BLACK, this));

	}

}
