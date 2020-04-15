package it.uniba.main;

import java.util.ArrayList;


public class AlgebraicNotation {
	String pieceLetter = "";	//lettera corrispondente al pezzo 
	ArrayList<String> symbol = new ArrayList<String>();	//simbolo speciale nel comando
	String endSquareId;	//casa di arrivo del pezzo
	ArrayList<String> symbolList = new ArrayList<String>();	//simboli possibili
	boolean isCastle = false;
	boolean isEnPassant = false;
	boolean isDoubleCheck = false;
	
	private static final int STARTINDEX = 0;
	private static final int PIECELETTERINDEX = 1;
	private static final int DOUBLECHECKSPOTS = 2;
	private static final int DOUBLECHECKINDEX = 3;
	private static final int ENPASSANTINDEX = 4;
	private static final int STARTINDEXCASTLING = 5;

	AlgebraicNotation(String command){
		initializeSymbolList();
		divideCommand(command);
	}

	void initializeSymbolList() {	//elenco simboli possibili
		symbolList.add("+");
		symbolList.add("x");
		symbolList.add("#");
		symbolList.add("++");
		symbolList.add("e.p.");
		symbolList.add("0-0-0");
		symbolList.add("0-0");
	}

	void divideCommand(String command){
		if(!isPawn(command)) {	//controllo lettera se non pedone
			this.pieceLetter = command.substring(STARTINDEX,PIECELETTERINDEX);
			command = reduceString(command, getPieceLetter());
			}
		
		extractSymbol(command);	//estrazione simbolo speciale
		
		if(isDoubleCheck) {	//caso scacco doppio
			command = command.substring(STARTINDEX, command.length()-DOUBLECHECKSPOTS);
		}
		
		for(String currentSymbol: getSymbol()) {	//elimino eventuali altri simboli
			command = reduceString(command, currentSymbol);
		}
		
		if(isEnPassant) {	//caso en passant
			setEndSquareId(command.substring(STARTINDEX, command.length()-ENPASSANTINDEX)); //elimino la dicitura e.p. 
		} else if(isCastle) {	//caso arrocco
			setEndSquareId("");
		} else {
			setEndSquareId(command);	//il resto della stringa è la casella di partenza/arrivo
		}

	}

	boolean isPawn(String command) {	//controllo se la mossa è di un pedone (nessuna lettera iniziale)

		char firstLetter = command.charAt(STARTINDEX);
		if(Character.isUpperCase(firstLetter)) {
			return false;
		}

		return true;
	}


	void extractSymbol(String command) {	//estrae l'eventuale simbolo speciale 
		int pos = 0;

		for(String currentSymbol: symbolList) {
			if(command.contains(currentSymbol)) {
	
				if(pos >= STARTINDEXCASTLING) {	//check di simboli che innescano eventi
					this.isCastle = true;
				} else if(pos == ENPASSANTINDEX) {
					this.isEnPassant = true;
				} else if(pos == DOUBLECHECKINDEX) {
					this.isDoubleCheck = true;
					getSymbol().remove(symbolList.get(STARTINDEX));
				}
				
				getSymbol().add(currentSymbol);
				
				if(isCastle) {	//interrompo se arrocco
					break;
				}
			}
			pos++;
		}
	}

	String reduceString(String command, String extracted) {	//riduce la stringa di comando eliminando i caratteri già estratti

		/*controllo se la stringa da sottrarre è vuota o se contiene un simbolo da trattare diversamente*/
		if(!extracted.isEmpty() && !(extracted == symbolList.get(ENPASSANTINDEX)) && !(extracted == symbolList.get(DOUBLECHECKINDEX))) {
			String newCommand = "";

			for(int i=0; i<command.length(); i++) {
				char commandToken = command.charAt(i);
				
				for(int j=0; j<extracted.length(); j++) {
					char extractedToken = extracted.charAt(j);
					
					if(commandToken != extractedToken) {
						newCommand += commandToken;
					}
				}
			}
			return newCommand;
		}
		return command;
	}
	
	boolean checkMove() {
		switch(getPieceLetter()) {
		case "T": case "D": case "A":
			break;
		}
		
		if(getEndSquareId().length() >3) {
			return false;
		} else {
			String letter = getEndSquareId().substring(0, 1);
			String number = getEndSquareId().substring(1, 2);
			
		}
		
		return false;
	}


	public String toString(){	//stampa a video del comando scomposto
		String output = "";
		output += "Nome Pezzo: "+getPieceLetter()+"\nCasa di arrivo: "+getEndSquareId()+"\nSimbolo speciale: "+getSymbol();
		return output;
	}

	
	/*Getters & Setters*/
	public String getPieceLetter() {
		return pieceLetter;
	}

	public void setPieceLetter(String pieceLetter) {
		this.pieceLetter = pieceLetter;
	}

	public ArrayList<String> getSymbol() {
		return symbol;
	}

	public void setSymbol(ArrayList<String> symbol) {
		this.symbol = symbol;
	}

	public String getEndSquareId() {
		return endSquareId;
	}

	public void setEndSquareId(String endSquareId) {
		this.endSquareId = endSquareId;
	}
}
