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

    public ResponseWrapper<NewDeckResponse> shuffleDeck(String deckId) throws IOException {
        String url = String.format("%s/%s/shuffle/", baseUrl, deckId);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), NewDeckResponse.class),
                rawResponse.getErrorMessage()
        );
    }
    public ResponseWrapper<NewDeckResponse> createNewDeck() throws IOException {
        String url = String.format("%s/new/", baseUrl);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), NewDeckResponse.class),
                rawResponse.getErrorMessage()
        );
    }

    public ResponseWrapper<CardDrawResponse> drawCard(String deckId, int count) throws IOException {
        String url = String.format("%s/%s/draw/?count=%d", baseUrl, deckId, count);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), CardDrawResponse.class),
                rawResponse.getErrorMessage()
        );
    }

    public ResponseWrapper<AddToPileResponse> createPile(String deckId, String pileName) throws IOException {
        String url = String.format("%s/%s/pile/%s/add/?cards=AS,2S", baseUrl, deckId, pileName);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), AddToPileResponse.class),
                rawResponse.getErrorMessage()
        );
    }

    public ResponseWrapper<AddToPileResponse> addToPile(String deckId, String pileName, int count) throws IOException {
        String url = String.format("%s/%s/pile/%s/add/?cards=bottom,%d", baseUrl, deckId, pileName, count);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), AddToPileResponse.class),
                rawResponse.getErrorMessage()
        );
    }

    public ResponseWrapper<AddToPileResponse> shufflePile(String deckId, String pileName) throws IOException {
        String url = String.format("%s/%s/pile/%s/shuffle/", baseUrl, deckId, pileName);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), AddToPileResponse.class),
                rawResponse.getErrorMessage()
        );
    }

    public ResponseWrapper<DrawFromPileResponse> drawFromPile(String deckId, String pileName, int count) throws IOException {
        String url = String.format("%s/%s/pile/%s/draw/?count=%d", baseUrl, deckId, pileName, count);
        ResponseWrapper<String> rawResponse = executeGetRequest(url);
        return new ResponseWrapper<>(
                rawResponse.getStatusCode(),
                mapJsonToType(rawResponse.getData(), DrawFromPileResponse.class),
                rawResponse.getErrorMessage()
        );
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
