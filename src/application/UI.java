package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezPeca;

public class UI {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0);
		int linha = Integer.parseInt(s.substring(1));
		return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo a posição do xadrez: valores válidos são de a1 até h8.");
		}
	}
	public static void printPartida(PartidaXadrez partidaXadrez, List<XadrezPeca> captura) {
		printBoard(partidaXadrez.getPecas());
		System.out.println();
		printCapturaPeca(captura);
		System.out.println("Jogada: " + partidaXadrez.getVirar());
		System.out.println("Esperando jogador: " + partidaXadrez.getJodadorAtual());
	}
	
	public static void printBoard(XadrezPeca[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPecas(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(XadrezPeca[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPecas(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPecas(XadrezPeca peca, boolean background) {
		if(background) {
			System.out.print(ANSI_GREEN_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		}else {
			if(peca.getCor() == Cor.WHITE) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_PURPLE + peca + ANSI_RESET);
			}
		}
	    System.out.print(" ");	
		}
	
	private static void printCapturaPeca(List<XadrezPeca> captura) {
		List<XadrezPeca> white = captura.stream().filter(x -> x.getCor() == Cor.WHITE).collect(Collectors.toList());
		List<XadrezPeca> black = captura.stream().filter(x -> x.getCor() == Cor.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Roxas:   ");
		System.out.print(ANSI_PURPLE);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}
}
