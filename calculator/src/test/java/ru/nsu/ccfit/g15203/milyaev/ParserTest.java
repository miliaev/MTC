package ru.nsu.ccfit.g15203.milyaev;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.StringReader;

public class ParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPlus() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("15+2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(17, parser.calculate());
    }
    @Test
    public void testMinus() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("15-2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(13, parser.calculate());
    }
    @Test
    public void testNegativeNumber() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("-23"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(-23, parser.calculate());
    }
    @Test
    public void testPositiveNumber() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("+159"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(159, parser.calculate());
    }
    @Test
    public void testMultiply() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("5*2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(10, parser.calculate());
    }
    @Test
    public void testDivide() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("10/2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(5, parser.calculate());
    }
    @Test
    public void testPlusAndMultiply() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("10+2*17"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(44, parser.calculate());
    }
    @Test
    public void testPlusAndDivide() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("10+34/17"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(12, parser.calculate());
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("5/0"));
        Parser parser = new Parser(lexer);
        parser.calculate();
    }

    @Test
    public void testElevate() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("5^2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(25, parser.calculate());
    }

    @Test
    public void testElevateAndOthers() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("5^2+15-2^4-3"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(21, parser.calculate());
    }

    @Test
    public void testNegativeElevate() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("-2^2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(-4, parser.calculate());
    }

    @Test
    public void testCorrectBrackets() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("(-2)^2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(4, parser.calculate());
    }

    @Test(expected = ParseException.class)
    public void testWrongBrackets() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("(-2)^2)"));
        Parser parser = new Parser(lexer);
        parser.calculate();
    }

    @Test(expected = ParseException.class)
    public void testIllegalFormat_1() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("(-2F2)^2"));
        Parser parser = new Parser(lexer);
        parser.calculate();
    }

    @Test(expected = ParseException.class)
    public void testIllegalFormat_2() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("(-2 2)^2"));
        Parser parser = new Parser(lexer);
        parser.calculate();
    }
    @Test(expected = NumberFormatException.class)
    public void testNumberFormat() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("2*-2"));
        Parser parser = new Parser(lexer);
        parser.calculate();
    }

    @Test
    public void testWhiteSpaces() throws IOException, ParseException {
        Lexer lexer = new Lexer(new StringReader("     (      -     22)^        2"));
        Parser parser = new Parser(lexer);
        Assert.assertEquals(484, parser.calculate());
    }
}