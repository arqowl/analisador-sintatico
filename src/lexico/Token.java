package lexico;

public class Token {

	public static final int TK_IDENTIFIER  				= 0;
	public static final int TK_INT     			        = 1;
	public static final int TK_FLOAT    			    = 2;
	public static final int TK_OPERADOR_RELACIONAL 	    = 3;
	public static final int TK_OPERADOR__ARITMETICO     = 4;
	public static final int TK_CHAR                     = 5;
	public static final int TK_CARACTER_ESPECIAL	    = 6;
	public static final int TK_PALAVRA_RESERVADA        = 7;
	public static final int TK_OPERADOR_BOOLEANO        = 8;
	public static final int TK_FIM_CODIGO               = 9;
	
	public static final String TK_TEXT[] = {
			"IDENTIFIER", "INT", "FLOAT", "OPERADOR_RELACIONAL", "OPERADOR__ARITMETICO",
			"CHAR", "CARACTER_ESPECIAL" , "PALAVRA_RESERVADA","OPERADOR_BOOLEANO" 	
	};
	
	private int    type;
	private String text;
	private int    linha;
	private int    coluna;
	
	
	public Token(int type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	public Token() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	

	@Override
	public String toString() {
		switch(this.type) {
		case 0:
			return this.text + " - Identificador";
		case 1:
			return this.text + " - Inteiro";
		case 2:
			return this.text + " - Real";
		case 3:
			return this.text + " - Operador Relacional";
		case 4:
			return this.text + " - Operador Aritmético";
		case 5:
			return this.text + " - Char";
		case 6:
			return this.text + " - Caracter Especial";
		case 7:
			return this.text + " - Palavra Reservada";
		case 8:
			return this.text + " - Operador Booleano";
		case 9:
			return this.text + " - Fim do Código";
		}
		return "";
	}
	
}
