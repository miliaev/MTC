package ru.nsu.ccfit.g15203.milyaev;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("2*-2"));
        Parser parser = new Parser(lexer);
        System.out.println(parser.calculate());
    }
}
