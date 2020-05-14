package it.uniba.example;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.uniba.main.AlgebraicNotation;

class AlgebraicNotationTest {
	AlgebraicNotation interpreter;
	String command;

	@Test
	void testValidAlgebraicNotation() {
		// pedone
		command = "d5";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "d5");
		assertTrue(interpreter.getSymbol().isEmpty());
		assertTrue(interpreter.isGoodMove());
		// pedone con cattura
		command = "cxd5";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "cd5");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isGoodMove());
		// pedone con en passant (scritta 'ep')
		command = "cxd5 ep";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "cd5");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().get(1), "ep");
		assertEquals(interpreter.getSymbol().size(), 2);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isEnPassant());
		assertTrue(interpreter.isGoodMove());
		// pedone con en passant (scritta 'e.p.')
		command = "cxd5 e.p.";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "cd5");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().get(1), "ep");
		assertEquals(interpreter.getSymbol().size(), 2);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isEnPassant());
		assertTrue(interpreter.isGoodMove());
		// cavallo con cattura
		command = "Cxa3";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "a3");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isGoodMove());
		// torre con ambiguità di colonna
		command = "Tba3";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "T");
		assertEquals(interpreter.getEndSquareId(), "ba3");
		assertTrue(interpreter.getSymbol().isEmpty());
		assertTrue(interpreter.isGoodMove());
		// cavallo con ambiguità di riga con cattura
		command = "C2xa3";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "2a3");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isGoodMove());
		// comando con scacco
		command = "Cd6+";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "d6");
		assertEquals(interpreter.getSymbol().get(0), "+");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCheck());
		assertTrue(interpreter.isGoodMove());
		// comando con scacco doppio
		command = "Cd6++";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "d6");
		assertEquals(interpreter.getSymbol().get(0), "++");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isDoubleCheck());
		assertTrue(interpreter.isGoodMove());
		// arrocco lungo (notazione con gli zeri)
		command = "0-0-0";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "");
		assertEquals(interpreter.getSymbol().get(0), "0-0-0");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isGoodMove());
		// arrocco corto (notazione con le 'O')
		command = "O-O";
		interpreter = new AlgebraicNotation(command);
		assertEquals(interpreter.getSymbol().get(0), "0-0");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isGoodMove());
	}

	@Test
	void testInvalidAlgebraicNotation() {
		// comando vuoto
		command = "";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// movimento in casa inesistente (colonna non valida)
		command = "Cj5";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// movimento in casa inesistente (riga non valida)
		command = "Dxh9";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// simbolo non valido ('X' maiuscola)
		command = "DXg6";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// simbolo non posizionato correttamente (caso 1)
		command = "Dgx6";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// simbolo non posizionato correttamente (caso 2)
		command = "Dg6x";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// pedone con cattura senza ambiguità
		command = "xg6";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// lettera non valida
		command = "Yh1";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// comando non valido (sbagliato)
		command = "xfe67";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// comando non valido (casa di arrivo inesistente)
		command = "Taxb6c7";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// comando non valido (impossibile en passant)
		command = "Txd5++ e.p.";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// comando non valido (troppi simboli)
		command = "exd5#++";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// arrocco corto non valido
		command = "0-0o";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// arrocco lungo non valido
		command = "0-0-0-0";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// ambiguita' con cattura non valida (per numero)
		command = "T9xc8";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// ambiguita' con cattura non valida (per lettera)
		command = "Tlxc8";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// ambiguita' con cattura non valida (pedone)
		command = "lxk8";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
		// pedone con doppio en passant
		command = "exf4 ep ep";
		interpreter = new AlgebraicNotation(command);
		assertFalse(interpreter.isGoodMove());
	}
}
