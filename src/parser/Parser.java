package parser;
import java.util.ArrayList;

import exceptions.semanticoException;
import exceptions.sintaxException;
import lexico.Lexico;
import lexico.Token;

public class Parser {
	private Lexico scanner;
	private Token token;
	public String x;
	int k;
	ArrayList<String> listaId = new ArrayList<>();
	
	public Parser(Lexico scanner) {
		this.scanner = scanner;
	}
	
	public void S() {
		token = scanner.nextToken();
		if(!token.getText().equals("int")) {
			throw new sintaxException("Coloque o int no inicio");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals("main")) {
			throw new sintaxException("Coloque main!");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals("(")) {
			throw new sintaxException("Abra o parentese do main");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals(")")) {
			throw new sintaxException("Feche o parentese do main!");
		}
		token = scanner.nextToken();
		
		B();
		
		if(token.getType() == Token.TK_FIM_CODIGO) {
			System.out.println("Ok");
			
		}
		else {
			System.out.println(" Ops, algum erro foi encontrado no final do codigo");
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
				throw new sintaxException(" Abra o parentese '(', Perto do ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			
			token = scanner.nextToken();
			ExpRel();
			
			if(!token.getText().equals(")")) {
				throw new sintaxException(" Feche o parentese ')' perto do " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
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
			throw new sintaxException(" Necessario um while no comeco!  Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		if(!token.getText().equals("(")) {
			throw new sintaxException(" Abra o parentese do while '(', Proximo de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		ExpRel();
		
		if(!token.getText().equals(")")) {
			throw new sintaxException(" Feche o parentese do while ')', Proximo de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		C();
		
	}
	
	public void At () {
		if(token.getType() != Token.TK_IDENTIFIER) {
			throw new sintaxException(" Necessario uma variavel para armazenar o valor, Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		procuraId(token.getText());
		
		token = scanner.nextToken();
		
		if(!token.getText().equals("=")) {
			throw new sintaxException(" Necessario um = para atribuir o resultado da expressao na variavel! Foi encontrado -> " +
			Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		token = scanner.nextToken();
		
		ExpArit();
		
		if(!token.getText().equals(";")) {
			throw new sintaxException(" Necessario o ; para finalizar a atribuicao, Proximo de -> " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}
		
		token = scanner.nextToken();
		}
	
	public void ExpRel() {
		ExpArit();
		
		if(token.getType() != Token.TK_OPERADOR_RELACIONAL && token.getType() != Token.TK_OPERADOR_BOOLEANO) {
			throw new sintaxException(" Necessario um operador relacional! Foi encontrado -> " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
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
				throw new sintaxException(" Necessario um operador aritmetico + ou - Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
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
				throw new sintaxException(" Necessario dizer o tipo da id (int, float, char) Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			
			if(token.getType() == Token.TK_IDENTIFIER) {
				salvarVariavel(token.getText());
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" Necessario uma variavel! Foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			
			if(token.getText().equals(";")) {
				token = scanner.nextToken();
			}else {
				throw new sintaxException(" Necessario ; para finalizar a declaracao, Proximo de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
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
				throw new sintaxException(" Necessario um operador aritmetico * ou / e foi encontrado ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
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
				throw new sintaxException(" Necessario fechar o parentese perto de ->" +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}
			token = scanner.nextToken();
		}
		
		else if ((token.getType() == 1 || token.getType() == 0 || token.getType() == 2 || token.getType() == 5)) {
			token = scanner.nextToken();
		}else {
			throw new sintaxException(" ID, float, int, char ou '(' sao esperados, Foi encontrado -> "+Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
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
			throw new semanticoException(" Necessario declarar variavel! " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
		}else {
			for (int a = 0; a < listaId.size(); a++) {
				if(id.equals(listaId.get(a))){
					k = 1; 
				}
			}
			if(k != 1) {
				throw new semanticoException(" Necessario declarar variavel! " +Token.TK_TEXT[token.getType()]+" ("+token.getText()+")");
			}	
		}
	}

}
