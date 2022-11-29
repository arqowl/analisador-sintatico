package lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import exceptions.LexicalException;

public class Lexico {

	private char[] conteudo;
	private int    estado;
	private int    indice;
	
	public Lexico(String filename) {
		try {

			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
			System.out.println("Main -------------");
			System.out.println(txtConteudo);
			System.out.println("Tokens --------------");
			conteudo = txtConteudo.toCharArray();
			indice=0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Token nextToken() {
		char c;
		Token token;
		String termo="";
		
		estado = 0;
		while (this.hasNextChar()) {
			c = nextChar();
			
			switch(estado) {
			case 0:
				if (isChar(c)) {
					termo += c;
					estado = 1;
				}
				else if (isDigit(c)) {
					estado = 3;
					termo += c;
				}
				else if (isSpace(c)) {
					estado = 0;
				}
				else if (isOperadorRelacional(c)) {
					estado = 7;
					termo += c;
				}
				else if (isNot(c)) {
					estado = 9;
					termo += c;
				}
				else if (isEqual(c)) {
					estado = 11;
					termo += c;
				}
				else if (isOperadorAritmetico(c)) {
					estado = 12;
					termo += c;
				}
				else if (isAspas(c)) {
					estado = 13;
					termo += c;
				}
				else if (isCaracterEspecial(c)) {
					estado = 15;
					termo += c;
				}
				else if (isOperadorBooleanoE(c)) {
					estado = 18;
					termo += c;
				}
				else if (isOperadorBooleanoOU(c)) {
					estado = 20;
					termo += c;
				}
				else if (c == '$') {
					estado = 99;
					termo += c;
				}
				else {
					throw new LexicalException("Simbolo invalido");
				}
				break;
			case 1:
				if (isChar(c) || isDigit(c) || isUnderline(c)) { 
					estado = 1;
					termo += c;
				}
				else if (isSpace(c) || isOperadorAritmetico(c) || isOperadorRelacional(c) || isEqual(c) ||
						isCaracterEspecial(c) || isAspas(c) || isNot(c)) {
					estado = 2;
					back();
				}
				else {
					throw new LexicalException("Identificador mal formatado");
				}
				break;
			case 2:
					if (isPalavraReservada(termo)) {
						estado = 17;
						back();
					} 
					else {
						token = new Token();
						token.setType(Token.TK_IDENTIFIER);
						token.setText(termo);
						back();
						return token;
					}
					break;
			case 3:
				if(isDigit(c)){
					estado = 3;
					termo += c;
				}
				else if (isSpace(c) || isOperadorAritmetico(c) || isOperadorRelacional(c) || isEqual(c) ||
						isCaracterEspecial(c) || isAspas(c) || isNot(c)){ 
					estado = 4;
					back();
				}
				else if (isPonto(c)){
					estado = 5;
					termo += c;
				}
				else {
					throw new LexicalException("Numero invalido");
				}
				break;
			case 4:
				token = new Token();
				token.setType(Token.TK_INT);
				token.setText(termo);
				back();
				return token;
			case 5:
				if(isDigit(c)){
					estado = 6;
					termo += c;
				}
				else {
					throw new LexicalException("Numero invalido");
				}
				break;
			case 6:
				if(isDigit(c)){
					estado = 6;
					termo += c;
				}else if (isSpace(c) || isOperadorAritmetico(c) || isOperadorRelacional(c) || isEqual(c) ||
						isCaracterEspecial(c) || isAspas(c) || isNot(c)){
						token = new Token();
						token.setType(Token.TK_FLOAT);
						token.setText(termo);
						back();
						return token;
				} else {
					throw new LexicalException("Numero invalido");
				}
				break;
			case 7:
				if(c == '=') {
					estado = 8;
					termo += c;
				}
				else if(isSpace(c) || isOperadorAritmetico(c) || isOperadorRelacional(c) || isEqual(c) ||
						isCaracterEspecial(c) || isAspas(c) || isNot(c) || isDigit(c) || isChar(c)) {
					estado = 8;
					back();
				}
				else {
					throw new LexicalException("Operador Relacional invalido");
				}
				break;
			case 8:
				token = new Token();
				token.setType(Token.TK_OPERADOR_RELACIONAL);
				token.setText(termo);
				back();
				return token;
			case 9:
				if(c == '=') {
					estado = 10;
					termo += c;
				}
				else {
					throw new LexicalException("Operador Relacional mal formatado");
				}
				break;
			case 10:
				token = new Token();
				token.setType(Token.TK_OPERADOR_RELACIONAL);
				token.setText(termo);
				back();
				return token;
			case 11:
				if(c == '=') {
					estado = 10;
					termo += c;
				}
				else if (isSpace(c) || isOperadorAritmetico(c) || isOperadorRelacional(c) || isEqual(c) ||
						isCaracterEspecial(c) || isAspas(c) || isNot(c) || isDigit(c) || isChar(c)) {
					estado = 12;
					back();
				}
				else {
					throw new LexicalException("Operador Relacional invalido");
				}
				break;
			case 12:
				token = new Token();
				token.setType(Token.TK_OPERADOR__ARITMETICO);
				token.setText(termo);
				back();
				return token;
			case 13:
				if (isChar(c) || isDigit(c)) {  
					estado = 14;
					termo += c;
				}
				else {
					throw new LexicalException("Char mal formatado");
				}
				break;
			case 14:
				if(isAspas(c)) {
					estado = 16;
					termo += c;
				}
				else {
					throw new LexicalException("Char mal formatado");
				}
				break;
			case 15: 
				token = new Token();
				token.setType(Token.TK_CARACTER_ESPECIAL);
				token.setText(termo);
				back();
				return token;
			case 16:
				token = new Token();
				token.setType(Token.TK_CHAR);
				token.setText(termo);
				back();
				return token;
			case 17:
				token = new Token();
				token.setType(Token.TK_PALAVRA_RESERVADA);
				token.setText(termo);
				back();
				return token;
			case 18:
				if(isOperadorBooleanoE(c)) {
					estado = 19;
					termo += c;
				}
				else {
					throw new LexicalException("Operador logico invalido");
				}
				break;
			case 19:
				token = new Token();
				token.setType(Token.TK_OPERADOR_BOOLEANO);
				token.setText(termo);
				back();
				return token;
			case 20:
				if(isOperadorBooleanoOU(c)) {
					estado = 21;
					termo += c;
				}
				else {
					throw new LexicalException("Operador logico invalido");
				}
				break;
			case 21:
				token = new Token();
				token.setType(Token.TK_OPERADOR_BOOLEANO);
				token.setText(termo);
				back();
				return token;
			case 99:
				token = new Token();
				token.setType(Token.TK_FIM_CODIGO);
				token.setText(termo);
				return token;
			}
		}
		return null;
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
	}
	
	private boolean isOperadorAritmetico(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}
	
	private boolean isOperadorRelacional(char c) {
		return c == '>' || c == '<';
	}
	
	private boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}
	
	private boolean isCaracterEspecial(char c) {
		return c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == ';';
	}
	
	private boolean isPonto(char c) {
		return c == '.';
	}
	
	private boolean isAspas(char c) {
		return c == '\'';
	} 
	
	private boolean isEqual(char c) {
		return c == '=';
	}
	
	private boolean isNot(char c) {
		return c == '!';
	}
	
	private boolean isPalavraReservada(String s) {
		return s.equals("else") || s.equals("if") || s.equals("main") || s.equals("while") || s.equals("do") || s.equals("float") || s.equals("char")
				|| s.equals("super") || s.equals("return") || s.equals("switch") || s.equals("try") || s.equals("int");
	}
	
	private boolean isUnderline(char c) {
		return c =='_';
	}
	
	private boolean isOperadorBooleanoE(char c) {
		return c == '&';
	}
	
	private boolean isOperadorBooleanoOU(char c) {
		return c == '|';
	}
	
	private char nextChar() {
		return conteudo[indice++];
	}
	
    private void back() {
    	indice--;
    }
    
    private boolean hasNextChar(){
        return indice < this.conteudo.length;
    }
    
    
}
