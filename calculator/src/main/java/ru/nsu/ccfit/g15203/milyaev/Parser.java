package ru.nsu.ccfit.g15203.milyaev;

import java.io.IOException;

public class Parser {
    private Lexeme currentLexeme;
    private Lexer lexer;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        currentLexeme = lexer.getLexeme();
    }

    private int parseExpr() throws IOException, ParseException {
        int sign;
        int temp = 0;
        if (currentLexeme.getType() == LexemeType.PLUS || currentLexeme.getType() == LexemeType.MINUS) {
            sign = currentLexeme.getType() == LexemeType.PLUS ? 1 : -1;
            currentLexeme = lexer.getLexeme();
            temp += sign * parseTerm();
        } else {
            temp += parseTerm();
        }
        while (currentLexeme.getType() == LexemeType.PLUS || currentLexeme.getType() == LexemeType.MINUS) {
            sign = currentLexeme.getType() == LexemeType.PLUS ? 1 : -1;
            currentLexeme = lexer.getLexeme();
            temp += sign * parseTerm();
        }
        return temp;
    }

    private int parseTerm() throws IOException, ParseException {
        int temp = parseFactor();
        while (currentLexeme.getType() == LexemeType.MULTIPLY || currentLexeme.getType() == LexemeType.DIVIDE) {
            boolean multiply = currentLexeme.getType() == LexemeType.MULTIPLY;
            currentLexeme = lexer.getLexeme();
            if (multiply) {
                temp *= parseFactor();
            } else {
                temp /= parseFactor();
            }
        }
        return temp;
    }

    private int parseFactor() throws IOException, ParseException {
        int temp = parsePower();
        if (currentLexeme.getType() == LexemeType.ELEVATE) {
            currentLexeme = lexer.getLexeme();
            temp = (int) Math.pow(temp, parseFactor());
        }
        return temp;
    }

    private int parsePower() throws IOException, ParseException {
        int temp = 0;
        if (currentLexeme.getType() == LexemeType.LEFT_BRACKET) {
            currentLexeme = lexer.getLexeme();
            temp = parseExpr();
            if (currentLexeme.getType() != LexemeType.RIGHT_BRACKET) {
                throw new ParseException("Illegal format");
            } else {
                currentLexeme = lexer.getLexeme();
            }
        } else {
            temp = Integer.valueOf(currentLexeme.getLexemeText());
            currentLexeme = lexer.getLexeme();
        }
        return temp;
    }

    public int calculate() throws IOException, ParseException {
        int result = parseExpr();
        if (currentLexeme.getType() != LexemeType.EOF) {
            throw new ParseException("Illegal format");
        }
        return result;
    }
}
