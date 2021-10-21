import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Preprocessor.start(Files.lines(Path.of("src/main/resources/test.java"))).flatMap(
                formLines -> new Pretokenizer(formLines.lines()).getTokens()).forEach(
                        token -> System.out.println(token.type.name() + " :: \"" + token.value + "\""));
    }
}
