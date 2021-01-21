package application;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezPeca;

public class Program {

public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	
	while(true) {
	UI.printBoard(partidaXadrez.getPecas());
	System.out.println();
	System.out.println("Origem: ");
	PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
	
	System.out.println();
	System.out.println("Destino: ");
	PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
	
	XadrezPeca capturaPeca = partidaXadrez.excutarMovimentoXadrez(origem, destino);
	}
	
}
}