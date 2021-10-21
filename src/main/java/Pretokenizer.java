import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pretokenizer {
    private static String punctSymbols = List.of("[", "]", "(", ")", "{", "}", ".", "->", "++", "--", "&", "*", "+", "-",
                    "...", "=", "*=", "/=", "%=", "+=", "-=", "<<=", ">>=", "&=", "^=", "|=", ",", "#", "##",
                    "~", "!", "/", "%", "<<", ">>", "<", ">", "<=", ">=", "==", "!=", "^", "&&", "||", "?", ":", ";",
                    "<:", "|", ":>", "<%", "%>", "%:", "%:%:")
            .stream()
            .map(Pattern::quote)
            .collect(Collectors.joining("|", "(", ")"));

    private static final String HEXD = "([\\da-fA-F])";
    private static final String UCN = "(\\\\u" + HEXD + "{4}|\\\\U" + HEXD + "{8})";
    private static final String IDND = "([a-zA-Z_]|" + UCN + ")";
    private static final String ESCAPE_SEQUENCE = "(\\\\['\"?\\\\abfnrtv]|\\\\[0-7]{1,3}|\\\\x" + HEXD + "+|" + UCN + ")";

    static final String HEADER_NAME = "(<[^>]+>|\"[^\"]+\")";
    static final String IDENTIFIER = "(" + IDND + "(\\w|" + UCN + ")*)";
    static final String PP_NUMBER = "(\\.?\\d(\\d|" + IDND + "|[eEpP][+-]|\\.)*)";
    static final String CHAR_CONSTANT = "([LuU]?'([^'\\\\]|" + ESCAPE_SEQUENCE + ")')";
    static final String STRING_LITERAL = "(u[8]|U|L)?\"([^\\\\\"]|" + ESCAPE_SEQUENCE + ")*\"";
    static final String PUNCTUATORS = punctSymbols;

    static final String CPP_COMMENT = "(//)";
    static final String C_COMMENT = "(/\\*)";

    private Stream<String> lines;
    private List<Token> tokens = new ArrayList<>();

    private boolean isComment = false;

    public Pretokenizer(Stream<String> lines) {
        this.lines = lines;
    }

    Stream<Token> getTokens() {
        lines.forEach(this::updateLine);
        return tokens.stream();
    }

    private void updateLine(String line) {
        if (isComment) {
            int i = line.indexOf("*/");
            if (i == -1)
                return;
            isComment = false;
            tokens.add(new Token(TokenType.WS, " "));
            line = line.substring(i + 2);

        }
        boolean inDirective = false;
        Token token;
        while (line.length() > 0) {
            int i = 0;
            while (i < line.length() && Character.isWhitespace(line.charAt(i)))
                i++;
            if (i > 0)
                tokens.add(new Token(TokenType.WS, " "));
            line = line.substring(i);
            token = TokenType.matchString(line, inDirective);
            if (token == null)
                return;
            if (token.type == TokenType.CPP_COMMENT) {
                tokens.add(new Token(TokenType.WS, " "));
                return;
            }
            if (token.type == TokenType.C_COMMENT) {
                isComment = true;
                updateLine(line.substring(2));
                return;
            }
            tokens.add(token);
            line = line.substring(token.value.length());
        }
        tokens.add(new Token(TokenType.NEWLINE, "\n"));

    }
}
