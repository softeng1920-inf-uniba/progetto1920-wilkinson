package it.uniba.example;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.uniba.main.AlgebraicNotation;

class AlgebraicNotationTest {
	AlgebraicNotation interpreter;

	@Test
	void testValidAlgebraicNotation() {
		// pedone
		interpreter = new AlgebraicNotation("d5");
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "d5");
		assertTrue(interpreter.getSymbol().isEmpty());
		assertTrue(interpreter.isGoodMove());
		// pedone con cattura
		interpreter = new AlgebraicNotation("cxd5");
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "cd5");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isGoodMove());
		// pedone con en passant (scritta 'ep')
		interpreter = new AlgebraicNotation("cxd5 ep");
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "cd5");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().get(1), "ep");
		assertEquals(interpreter.getSymbol().size(), 2);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isEnPassant());
		assertTrue(interpreter.isGoodMove());
		// pedone con en passant (scritta 'e.p.')
		interpreter = new AlgebraicNotation("cxd5 e.p.");
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "cd5");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().get(1), "ep");
		assertEquals(interpreter.getSymbol().size(), 2);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isEnPassant());
		assertTrue(interpreter.isGoodMove());
		// cavallo con cattura
		interpreter = new AlgebraicNotation("Cxa3");
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "a3");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isGoodMove());
		// torre con ambiguita' di colonna
		interpreter = new AlgebraicNotation("Tba3");
		assertEquals(interpreter.getPieceLetter(), "T");
		assertEquals(interpreter.getEndSquareId(), "ba3");
		assertTrue(interpreter.getSymbol().isEmpty());
		assertTrue(interpreter.isGoodMove());
		// cavallo con ambiguita' di riga con cattura
		interpreter = new AlgebraicNotation("C2xa3");
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "2a3");
		assertEquals(interpreter.getSymbol().get(0), "x");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCapture());
		assertTrue(interpreter.isGoodMove());
		// comando con scacco
		interpreter = new AlgebraicNotation("Cd6+");
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "d6");
		assertEquals(interpreter.getSymbol().get(0), "+");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isCheck());
		assertTrue(interpreter.isGoodMove());
		// comando con scacco doppio
		interpreter = new AlgebraicNotation("Cd6++");
		assertEquals(interpreter.getPieceLetter(), "C");
		assertEquals(interpreter.getEndSquareId(), "d6");
		assertEquals(interpreter.getSymbol().get(0), "++");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isDoubleCheck());
		assertTrue(interpreter.isGoodMove());
		// arrocco lungo (notazione con gli zeri)
		interpreter = new AlgebraicNotation("0-0-0");
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "");
		assertEquals(interpreter.getSymbol().get(0), "0-0-0");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isGoodMove());
		// arrocco corto (notazione con le 'O')
		interpreter = new AlgebraicNotation("O-O");
		assertEquals(interpreter.getPieceLetter(), "");
		assertEquals(interpreter.getEndSquareId(), "");
		assertEquals(interpreter.getSymbol().get(0), "0-0");
		assertEquals(interpreter.getSymbol().size(), 1);
		assertTrue(interpreter.isGoodMove());
	}

	@Test
	void testInvalidAlgebraicNotation() {
		// comando vuoto
		interpreter = new AlgebraicNotation("");
		assertFalse(interpreter.isGoodMove());
		// movimento in casa inesistente (colonna non valida)
		interpreter = new AlgebraicNotation("Cj5");
		assertFalse(interpreter.isGoodMove());
		// movimento in casa inesistente (riga non valida)
		interpreter = new AlgebraicNotation("Dxh9");
		assertFalse(interpreter.isGoodMove());
		// simbolo non valido ('X' maiuscola)
		interpreter = new AlgebraicNotation("DXg6");
		assertFalse(interpreter.isGoodMove());
		// simbolo non posizionato correttamente (caso 1)
		interpreter = new AlgebraicNotation("Dgx6");
		assertFalse(interpreter.isGoodMove());
		// simbolo non posizionato correttamente (caso 2)
		interpreter = new AlgebraicNotation("Dg6x");
		assertFalse(interpreter.isGoodMove());
		// pedone con cattura senza ambiguita'
		interpreter = new AlgebraicNotation("xg6");
		assertFalse(interpreter.isGoodMove());
		// lettera non valida
		interpreter = new AlgebraicNotation("Yh1");
		assertFalse(interpreter.isGoodMove());
		// comando non valido (sbagliato)
		interpreter = new AlgebraicNotation("xfe67");
		assertFalse(interpreter.isGoodMove());
		// comando non valido (casa di arrivo ripetuta)
		interpreter = new AlgebraicNotation("Taxb6c7");
		assertFalse(interpreter.isGoodMove());
		// comando non valido (impossibile en passant)
		interpreter = new AlgebraicNotation("Txd5++ e.p.");
		assertFalse(interpreter.isGoodMove());
		// comando non valido (troppi simboli)
		interpreter = new AlgebraicNotation("exd5#++");
		assertFalse(interpreter.isGoodMove());
		// comando non valido (maiuscolo)
		interpreter = new AlgebraicNotation("TxD5");
		assertFalse(interpreter.isGoodMove());
		// arrocco corto non valido
		interpreter = new AlgebraicNotation("0-0o");
		assertFalse(interpreter.isGoodMove());
		// arrocco lungo non valido
		interpreter = new AlgebraicNotation("0-0-0-0");
		assertFalse(interpreter.isGoodMove());
		// ambiguita' con cattura non valida (per numero)
		interpreter = new AlgebraicNotation("T9xc8");
		assertFalse(interpreter.isGoodMove());
		// ambiguita' con cattura non valida (per lettera)
		interpreter = new AlgebraicNotation("Tlxc8");
		assertFalse(interpreter.isGoodMove());
		// ambiguita' con cattura non valida (pedone)
		interpreter = new AlgebraicNotation("lxk8");
		assertFalse(interpreter.isGoodMove());
		// pedone con doppio en passant
		interpreter = new AlgebraicNotation("exf4 ep ep");
		assertFalse(interpreter.isGoodMove());
		// comando en passant non valido
		interpreter = new AlgebraicNotation("exf4 eep");
		assertFalse(interpreter.isGoodMove());
		// comando en passant inizio stringa
		interpreter = new AlgebraicNotation("ep exf4");
		assertFalse(interpreter.isGoodMove());
		// comando en passant meta' stringa
		interpreter = new AlgebraicNotation("exe.p. f4");
		assertFalse(interpreter.isGoodMove());
		// comando en passant senza cattura
		interpreter = new AlgebraicNotation("f4 ep");
		assertFalse(interpreter.isGoodMove());
		// solo comando en passant
		interpreter = new AlgebraicNotation("e.p.");
		assertFalse(interpreter.isGoodMove());
	}
}