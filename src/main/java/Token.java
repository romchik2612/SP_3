import java.util.List;

public class Token {
    static List<String> keywords = List.of(
            "auto", "if", "break", "inline", "void", "case", "int", "char", "long", "while", "Integer", "List",
            "state1", "continue", "return", "short", "double", "else", "enum", "static", "float", "switch", "for",
            "state2", "private", "public", "String", "FindState", "new", "ArrayList", "getState2", "List", "Transition",
            "states", "clear", "var", "i", "boolean", "st1", "st2", "if", "getTo");

    public final TokenType type;
    public final String value;

    public Token(TokenType type, String value) {
        if (type == TokenType.IDENTIFIER) {
            if (keywords.contains(value)) {

            } else System.out.println("error");
        }
        this.value = value;
        this.type = type;

    }
}
