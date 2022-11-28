package main;

import exceptions.LexicalException;
import exceptions.semanticoException;
import exceptions.sintaxException;
import lexico.Lexico;
import parser.isiParser;

public class MainClass {
	public static void main(String[] args) {
		try {
			Lexico sc = new Lexico("input");
			isiParser pa = new isiParser(sc);
			 
			
			pa.S();
			
//			Token token = null;
//			do {
//				token = sc.nextToken();
//				if (token != null) {
//					System.out.println(token);
//				}
//			} while (token != null);	
			System.out.println("compilation Sucessful");
			
		} 
		catch (LexicalException ex) {
			System.out.println("ERRO L�XICO "+ex.getMessage());
		}
		catch (sintaxException ex) {
			System.out.println("ERRO SINTATICO!" + ex.getMessage());
		}
		catch (semanticoException ex) {
			System.out.println("ERRO SEM�NTICO!" + ex.getMessage());
		}
		
		
	}
}
