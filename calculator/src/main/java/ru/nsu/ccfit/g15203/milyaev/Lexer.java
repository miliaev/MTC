package ru.nsu.ccfit.g15203.milyaev;

import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private char currentSymbol;
    private Reader reader;
    public Lexer(Reader reader) throws IOException {
        this.reader = reader;
        currentSymbol = (char) reader.read();
    }
    Lexeme getLexeme() throws IOException {
        Lexeme lexeme;
        String number = "";
        while (Character.isWhitespace(currentSymbol)) {
            currentSymbol = (char) reader.read();
        }
        switch (currentSymbol) {
            case '+':
                lexeme = new Lexeme(LexemeType.PLUS, "+");
                break;
            case '-':
                lexeme = new Lexeme(LexemeType.MINUS, "-");
                break;
            case '*':
                lexeme = new Lexeme(LexemeType.MULTIPLY, "*");
                break;
            case '/':
                lexeme = new Lexeme(LexemeType.DIVIDE, "/");
                break;
            case '^':
                lexeme = new Lexeme(LexemeType.ELEVATE, "^");
                break;
            case '(':
                lexeme = new Lexeme(LexemeType.LEFT_BRACKET, "(");
                break;
            case ')':
                lexeme = new Lexeme(LexemeType.RIGHT_BRACKET, ")");
                break;
            case (char) -1:
                lexeme = new Lexeme(LexemeType.EOF, "EOF");
                break;
            default:
                number += currentSymbol;
                currentSymbol = (char) reader.read();
                while (currentSymbol >= '0' && currentSymbol <= '9') {
                    number += currentSymbol;
                    currentSymbol = (char) reader.read();
                }
                lexeme = new Lexeme(LexemeType.NUMBER, number);
                break;
        }
        if (lexeme.getType() != LexemeType.NUMBER) {
            currentSymbol = (char) reader.read();
        }
        return lexeme;
    }
}
