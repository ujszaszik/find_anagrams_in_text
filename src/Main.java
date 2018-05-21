import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		try (Stream<String> words = Files.lines(Paths.get("lib/lastnames.txt"))) {
			Map<String, List<String>> anagrams = words.collect(Collectors.groupingBy(word -> new String(getLexicographicalOrder(word))));
			anagrams = anagrams
					.entrySet()
					.stream()
					.sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> newValue, LinkedHashMap::new));
			anagrams.forEach((k, v) -> System.out.println(v.size() + ": " + v));
		} catch (IOException e) {
			System.out.println("Could not find file!");
		}

	}

	private char[] getLexicographicalOrder(String word) {
		final char[] wordChars = word.toCharArray();
		Arrays.sort(wordChars);
		return wordChars;
	}

}