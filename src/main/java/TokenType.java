import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TokenType {
    HEADER_NAME(Pretokenizer.HEADER_NAME),
    IDENTIFIER(Pretokenizer.IDENTIFIER),
    PP_NUMBER(Pretokenizer.PP_NUMBER),
    CHAR_CONSTANT(Pretokenizer.CHAR_CONSTANT),
    STRING_LITERAL(Pretokenizer.STRING_LITERAL),
    PUNCTUATOR(Pretokenizer.PUNCTUATORS),
    CPP_COMMENT(Pretokenizer.CPP_COMMENT),
    C_COMMENT(Pretokenizer.C_COMMENT),
    WS("\\b\\B"),
    NEWLINE("\\b\\B"),
    MISC(".");

    private Pattern pattern;

    TokenType(String s) {
        pattern = Pattern.compile(s);
    }

    public static Token matchString(String line, boolean inDirective) {
        List<Token> tokens = new ArrayList<>();
        for (TokenType tokenType : TokenType.values()) {
            if (!inDirective && tokenType == HEADER_NAME)
                continue;
            Matcher matcher = tokenType.pattern.matcher(line);
            if (matcher.lookingAt()) {
                tokens.add(new Token(tokenType, matcher.group(0)));
            }
        }
        return tokens.stream().max(Comparator.comparingInt(x -> x.value.length())).orElse(null);
    }
}
