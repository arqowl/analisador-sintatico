package parser;
import java.util.ArrayList;

import exceptions.semanticoException;
import exceptions.sintaxException;
import lexico.Lexico;
import lexico.Token;

public class isiParser {
	private Lexico scanner;
	private Token token;
	public String x;
	int k;
	ArrayList<String> listaId = new ArrayList<>();
	
	public isiParser(Lexico scanner) {
		this.scanner = scanner;
	}
	
	public void S() {
		token = scanner.nextToken();
		if(!token.getText().equals("int")) {
			throw new sintaxException("Coloque o int no início");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals("main")) {
			throw new sintaxException("Coloque main!");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals("(")) {
			throw new sintaxException("Abra o parêntese do main");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals(")")) {
			throw new sintaxException("Feche o parêntese do main!");
		}
		token = scanner.nextToken();
		
		B();
		
		if(token.getType() == Token.TK_FIM_CODIGO) {
			System.out.println("Ok");
			
		}
		else {
			System.out.println(" Ops algum erro foi encontrado no final do código");
		}
	}
	
	public void B() {
		if(!token.getText().equals("{")) {
			throw new sintaxException(" Precisa abrir chaves -> { Foi encontrado -> " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		DecVar();
		
		
		
		while(token.getType() == Token.TK_IDENTIFIER || token.getText().equals("{") || (token.getText().equals("while"))
				|| token.getText().equals("if")) {
			C();
		}
		
		if(!token.getText().equals("}")) {
			throw new sintaxException(" Precisa fechar a chave -> } Foi encontrado -> "+Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
	}
	
	public void C() {
		if(token.getType() == Token.TK_IDENTIFIER || token.getText().equals("{")) {
			CB();
		}else if(token.getText().equals("while")) {
			It();
		}else if(token.getText().equals("if")) {
			token = scanner.nextToken();
			if(!token.getText().equals("(")) {
				throw new sintaxException(" Abra o parêntese '(', Perto do ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			
			token = scanner.nextToken();
			ExpRel();
			
			if(!token.getText().equals(")")) {
				throw new sintaxException(" Feche o parêntese ')' perto do " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			token = scanner.nextToken();
			C();
			
			
			if(token.getText().equals("else")) {
				token = scanner.nextToken();
				C();
			}else {
				
			}
		}else {
			
		}
	}
	
	public void CB() {
		if(token.getType() == Token.TK_IDENTIFIER) {
			At();
		}
		if(token.getText().equals("{")){
			B();
		}
	}
	
	public void It() {
		if(!token.getText().equals("while")) {
			throw new sintaxException(" Necessário um while no começo!  Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals("(")) {
			throw new sintaxException(" Abra o parêntese do while '(', Próximo de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		ExpRel();
		
		if(!token.getText().equals(")")) {
			throw new sintaxException(" Feche o parêntese do while ')', Próximo de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		C();
		
	}
	
	public void At () {
		if(token.getType() != Token.TK_IDENTIFIER) {
			throw new sintaxException(" Necessário uma variável para armazenar o valor, Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		procuraId(token.getText());
		
		token = scanner.nextToken();
		
		if(!token.getText().equals("=")) {
			throw new sintaxException(" Necessário um = para atribuir o resultado da expressão na variável! Foi encontrado -> " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		ExpArit();
		
		if(!token.getText().equals(";")) {
			throw new sintaxException(" Necessário o ; para finalizar a atribuição, próximo de -> " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		
		token = scanner.nextToken();
		}
	
	public void ExpRel() {
		ExpArit();
		
		if(token.getType() != Token.TK_OPERADOR_RELACIONAL && token.getType() != Token.TK_OPERADOR_BOOLEANO) {
			throw new sintaxException(" É necessário um operador relacional! Foi encontrado -> " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		ExpArit();
	}
	
	public void ExpArit() {
		Termo();
		Elinha();	
	}
	
	public void Elinha() {
		if(token.getType() == Token.TK_OPERADOR__ARITMETICO) {
			if(token.getText().equals("+")) {
				token = scanner.nextToken();
			}else if(token.getText().equals("-")) {
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" É necessário um operador aritmético + ou - Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			Termo();
			Elinha();
		}else {
			
		}
	}
	
	public void DecVar() {
		if(token.getText().equals("int") ||token.getText().equals("float") || token.getText().equals("char")){
			if(token.getText().equals("int") ||token.getText().equals("float") || token.getText().equals("char")) {
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" É necessário dizer o tipo da id (int, float, char) Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			
			if(token.getType() == Token.TK_IDENTIFIER) {
				salvarVariavel(token.getText());
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" Necessário uma variável! Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			
			if(token.getText().equals(";")) {
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" Necessário ; para finalizar a declaração, próximo de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			DecVar();
		}else {
			
		}
	}
	
	
	public void Termo() {
		Fator();
		Termol();	
	}

	public void Termol() {
		if(token.getText().equals("*") || token.getText().equals("/")) {
			if(token.getText().equals("*")) {
				token = scanner.nextToken();
			}else if(token.getText().equals("/")) {
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" É necessário um operador aritmético * ou / e foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			Fator();
			Termol();
		}
	}
	
	public void Fator() {
		if(token.getText().equals("(")) {
			token = scanner.nextToken();
			ExpArit();
			if(!token.getText().equals(")")) {
				throw new sintaxException(" é necessário fechar o parentese perto de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			token = scanner.nextToken();
		}
		
		else if ((token.getType() == 1 || token.getType() == 0 || token.getType() == 2 || token.getType() == 5)) {
			token = scanner.nextToken();
		}else {
			throw new sintaxException(" ID, float, int, char ou '(' são esperados, Foi encontrado -> "+Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
	}
	
	public void OP() {
		if (token.getType() == Token.TK_OPERADOR__ARITMETICO) {
			token = scanner.nextToken();
		}else {
			throw new sintaxException(" Operator Expected, found "+Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
 		}
	
	}
	
	public void salvarVariavel(String id) {
		x = id;
		listaId.add(x);
		
	}
	
	public void procuraId(String id) {
		if(listaId.isEmpty()) {
			throw new semanticoException(" É Necessário declarar variável! " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}else {
			for (int a = 0; a < listaId.size(); a++) {
				if(id.equals(listaId.get(a))){
					k = 1; 
				}
			}
			if(k != 1) {
				throw new semanticoException(" É Necessário declarar variável! " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}	
		}
	}

}
