import java.util.Map;
import java.util.stream.Stream;

public class Preprocessor {
    private static Map<String, String> changeMap = Map.of(
            "??(", "[",
            "??)", "]",
            "??!", "|",
            "??/", "\\",
            "??=", "#",
            "??-", "~",
            "??'", "^",
            "??<", "{",
            "??>", "}"
    );

    public static Stream<String> start(Stream<String> lines) {
        return replaceElements(lines);
    }

    private static Stream<String> replaceElements(Stream<String> lines) {
        return lines.map(oldLines -> {
                    final String[] refLines = {oldLines};
                    changeMap.forEach((trg, val) -> refLines[0] = refLines[0].replace(trg, val));
                    return refLines[0];
                }
        );
    }

}
