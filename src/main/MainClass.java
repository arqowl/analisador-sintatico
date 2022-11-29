package main;

import exceptions.LexicalException;
import exceptions.semanticoException;
import exceptions.sintaxException;
import lexico.Lexico;
import parser.Parser;

public class MainClass {
	public static void main(String[] args) {
		try {
			Lexico sc = new Lexico("input");
			Parser pa = new Parser(sc);
			 
			
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
			System.out.println("ERRO LEXICO "+ex.getMessage());
		}
		catch (sintaxException ex) {
			System.out.println("ERRO SINTATICO!" + ex.getMessage());
		}
		catch (semanticoException ex) {
			System.out.println("ERRO SEMANTICO!" + ex.getMessage());
		}
		
		
	}
}
