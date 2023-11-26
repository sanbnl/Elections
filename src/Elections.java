import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Elections {


    private static List<String> extractVotePairs(String line) {
        List<String> pairs = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            pairs.add("(" + matcher.group(1) + "," + matcher.group(2) + ")");
        }
        return pairs;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please introduce the elections file path: ");
        String path = in.next();
        File file = new File(path);
        Map<String, String> votes = new HashMap<>();

        if(Files.exists(Paths.get(path))) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String line = bufferedReader.readLine();

                while (line != null) {
                    List<String> votePairs = extractVotePairs(line);
                    for(String vote: votePairs) {
                        String [] votePair =  vote.replaceAll("[()]", "").split(",");
                        if(!votes.containsKey(votePair[0])) {
                            votes.put(votePair[0],votePair[1]);
                        } else {
                            System.out.println("FRAUDE !!!!!!!!!!! with the voter id: " + votePair[0]);
                        }
                     }
                    line = bufferedReader.readLine();
                }
                Map<String, Integer> candidatesResults = new HashMap<>();
                for (String candidate : votes.values()) {
                    candidatesResults.put(candidate, candidatesResults.getOrDefault(candidate, 0) + 1);
                }
                List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(candidatesResults.entrySet());
                sortedList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
                sortedList.stream().limit(3).forEach(m -> System.out.println(m.getKey()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("The path introduced does not exist...");
        }
    }
}