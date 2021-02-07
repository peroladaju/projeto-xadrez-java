package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezPeca;

public class Program {

public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	List<XadrezPeca> captura = new ArrayList<>();
	
	while(!partidaXadrez.getCheckMate()) {
	try {
		UI.clearScreen();	
		UI.printPartida(partidaXadrez, captura);
		System.out.println();
		System.out.println("Origem: ");
		PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
		
		boolean [][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
		UI.clearScreen();
		UI.printBoard(partidaXadrez.getPecas(), movimentosPossiveis);
		
		System.out.println();
		System.out.println("Destino: ");
		PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
		
		XadrezPeca capturaPeca = partidaXadrez.excutarMovimentoXadrez(origem, destino);
		
		if (capturaPeca != null) {
			captura.add(capturaPeca);
		}
	
		}
	
	catch (ExcecaoXadrez e) {
		System.out.println(e.getMessage());
		sc.nextLine();
	}
	catch (InputMismatchException e) {
		System.out.println(e.getMessage());
		sc.nextLine();
	}
	}
	UI.clearScreen();
	UI.printPartida(partidaXadrez, captura);
}
}