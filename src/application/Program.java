package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezPeca;

public class Program {

public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	
	while(true) {
	try {
		UI.clearScreen();	
		UI.printBoard(partidaXadrez.getPecas());
		System.out.println();
		System.out.println("Origem: ");
		PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
		
		System.out.println();
		System.out.println("Destino: ");
		PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
		
		XadrezPeca capturaPeca = partidaXadrez.excutarMovimentoXadrez(origem, destino);
		
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
	
}
}