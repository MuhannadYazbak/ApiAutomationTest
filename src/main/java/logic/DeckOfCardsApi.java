package logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import infra.HttpFacade;
import infra.ResponseWrapper;

import java.io.IOException;

public class DeckOfCardsApi {
    private final String baseUrl = "https://deckofcardsapi.com/api/deck";
    private final HttpFacade httpFacade;
    private final ObjectMapper objectMapper;

    public DeckOfCardsApi(HttpFacade httpFacade, ObjectMapper objectMapper) {
        this.httpFacade = httpFacade;
        this.objectMapper = objectMapper;
    }
    private <T> T mapJsonToType(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }

    public ResponseWrapper<String> shuffleDeck(String deckId) throws IOException {
        String url = String.format("%s/%s/shuffle/", baseUrl, deckId);
        return executeGetRequest(url);
    }

    public ResponseWrapper<String> createNewDeck() throws IOException {
        String url = String.format("%s/new/", baseUrl);
        return executeGetRequest(url);
    }

    public ResponseWrapper<String> drawCard(String deckId, int count) throws IOException {
        String url = String.format("%s/%s/draw/?count=%d", baseUrl, deckId, count);
        return executeGetRequest(url);
    }

    public ResponseWrapper<String> createPile(String deckId, String pileName) throws IOException {
        String url = String.format("%s/%s/pile/%s/add/?cards=AS,2S", baseUrl, deckId, pileName);
        return executeGetRequest(url);
    }

    public ResponseWrapper<String> addToPile(String deckId, String pileName, int count) throws IOException {
        String url = String.format("%s/%s/pile/%s/add/?cards=bottom,%d", baseUrl, deckId, pileName, count);
        return executeGetRequest(url);
    }

    public ResponseWrapper<String> shufflePile(String deckId, String pileName) throws IOException {
        String url = String.format("%s/%s/pile/%s/shuffle/", baseUrl, deckId, pileName);
        return executeGetRequest(url);
    }

    public ResponseWrapper<String> drawFromPile(String deckId, String pileName, int count) throws IOException {
        String url = String.format("%s/%s/pile/%s/draw/?count=%d", baseUrl, deckId, pileName, count);
        return executeGetRequest(url);
    }

    private ResponseWrapper<String> executeGetRequest(String url) throws IOException {
        try {
            JsonNode jsonResponse = executeHttpRequest(url);
            String responseString = objectMapper.writeValueAsString(jsonResponse);
            return new ResponseWrapper<>(200, responseString, null);
        } catch (IOException e) {
            return new ResponseWrapper<>(500, null, e.getMessage());
        }
    }
    private JsonNode executeHttpRequest(String url) {
    try {
        return objectMapper.readTree(httpFacade.executeGet(url).getEntity().getContent());
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
}
