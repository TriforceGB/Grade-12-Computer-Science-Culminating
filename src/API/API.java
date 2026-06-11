package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class API {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("--- ANILIST DISPLAY CONFIGURATION ---");
        System.out.println("1. English Titles");
        System.out.println("2. Japanese Romaji Titles");
        System.out.print("Choose display language (1 or 2): ");
        
        int choice = input.nextInt();
        input.nextLine(); 

        String targetKey = (choice == 1) ? "\"english\":\"" : "\"romaji\":\"";

        System.out.print("\nEnter anime search query (e.g., Attack on Titan): ");
        String searchAnime = input.nextLine();

        // Updated GraphQL query to include type: ANIME filter
        String query = "query ($id: Int, $page: Int, $perPage: Int, $search: String) {\n" +
                       "  Page (page: $page, perPage: $perPage) {\n" +
                       "    pageInfo {\n" +
                       "      currentPage\n" +
                       "      hasNextPage\n" +
                       "      perPage\n" +
                       "    }\n" +
                       "    media (id: $id, search: $search, type: ANIME) {\n" + // <-- ADDED type: ANIME HERE
                       "      id\n" +
                       "      title {\n" +
                       "        english\n" +
                       "        romaji\n" +
                       "      }\n" +
                       "    }\n" +
                       "  }\n" +
                       "}";

        int page = 1;
        int perPage = 10; 

        String variables = "{\n" +
                           "  \"search\": \"" + searchAnime + "\",\n" +
                           "  \"page\": " + page + ",\n" +
                           "  \"perPage\": " + perPage + "\n" +
                           "}";

        String jsonRequestBody = "{\n" +
                                 "  \"query\": " + convertStringToJSONValue(query) + ",\n" +
                                 "  \"variables\": " + variables + "\n" +
                                 "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://graphql.anilist.co"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                System.out.println("\n--- ANIME SEARCH RESULTS ---");
                
                int currentIndex = 0;
                int matchCount = 1;

                while ((currentIndex = responseBody.indexOf(targetKey, currentIndex)) != -1) {
                    int startPos = currentIndex + targetKey.length();
                    int endPos = responseBody.indexOf("\"", startPos);
                    
                    String animeTitle = responseBody.substring(startPos, endPos);
                    
                    if (choice == 1 && animeTitle.equals("null")) {
                        String romajiKey = "\"romaji\":\"";
                        int romStart = responseBody.indexOf(romajiKey, currentIndex - 20); 
                        if (romStart != -1) {
                            int romEnd = responseBody.indexOf("\"", romStart + romajiKey.length());
                            animeTitle = responseBody.substring(romStart + romajiKey.length(), romEnd) + " (No Eng Title)";
                        }
                    }

                    System.out.println(matchCount + ". " + animeTitle);
                    
                    matchCount++;
                    currentIndex = endPos;
                }
                
                if (matchCount == 1) {
                    System.out.println("No matching anime found.");
                }

            } else {
                System.out.println("API Error: Status Code " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            input.close(); 
        }
    }

    private static String convertStringToJSONValue(String input) {
        return "\"" + input.replace("\\", "\\\\")
                           .replace("\"", "\\\"")
                           .replace("\n", "\\n")
                           .replace("\r", "\\r") + "\"";
    }
}