package test;

import com.fasterxml.jackson.databind.JsonNode;
import infra.HttpFacade;
import infra.ResponseWrapper;
import logic.DeckOfCardsApi;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static infra.ObjectMapperProvider.getObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckOfCardsApiTest {
    private CloseableHttpClient httpClient;
    private DeckOfCardsApi deckOfCardsApi;

    //    private String extractDeckIdFromResponse(ResponseWrapper<String> response) throws IOException {
//        JsonNode jsonResponse = getObjectMapper().readTree(response.getData());
//        return jsonResponse.path("deck_id").asText();
//    }

    private String extractDeckIdFromResponse(ResponseWrapper<String> response) throws IOException {
        JsonNode jsonResponse = getObjectMapper().readTree(response.getData());

        if (jsonResponse.has("deck_id")) {
            // Response structure for createNewDeck
            return jsonResponse.path("deck_id").asText();
        } else if (jsonResponse.has("success") && jsonResponse.path("success").asBoolean()) {
            // Response structure for other operations
            return jsonResponse.path("deck_id").asText();
        } else {
            // Handle unexpected response structure
            throw new IllegalArgumentException("Unable to extract deck ID from the response");
        }
    }


    @BeforeEach
    public void setUp() {
        httpClient = HttpClients.createDefault();
        HttpFacade httpFacade = new HttpFacade();
        deckOfCardsApi = new DeckOfCardsApi(httpFacade, getObjectMapper());
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }
@Test
void testShuffleDeck() throws IOException {
    // Arrange
    ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
    String deckId = extractDeckIdFromResponse(createResponse);
    System.out.println("Deck ID: " + deckId);

    // Act
    ResponseWrapper<String> response = deckOfCardsApi.shuffleDeck(deckId);

    // Log response details
    System.out.println("Status Code: " + response.getStatusCode());
    System.out.println("Response Content: " + response.getData());
    System.out.println("Error Message: " + response.getErrorMessage());

    // Assert
    assertEquals(200, response.getStatusCode());
    // Perform additional data and status validations as needed
}



    @Test
    void testCreateNewDeck() throws IOException {
        // Act
        ResponseWrapper<String> response = deckOfCardsApi.createNewDeck();

        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testDrawCard() throws IOException {
        // Arrange
        String deckId = "new";
        int count = 1;

        // Act
        ResponseWrapper<String> response = deckOfCardsApi.drawCard(deckId, count);

        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testCreatePile() throws IOException {
        // Arrange
        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
        String deckId = extractDeckIdFromResponse(createResponse);
        String pileName = "test_pile";

        // Act
        ResponseWrapper<String> response = deckOfCardsApi.createPile(deckId, pileName);

        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testAddToPile() throws IOException {
        // Arrange
        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
        String deckId = extractDeckIdFromResponse(createResponse);
        String pileName = "test_pile";
        int count = 2;

        // Act
        ResponseWrapper<String> response = deckOfCardsApi.addToPile(deckId, pileName, count);

        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testShufflePile() throws IOException {
        // Arrange
        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
        String deckId = extractDeckIdFromResponse(createResponse);
        String pileName = "test_pile";

        // Act
        ResponseWrapper<String> response = deckOfCardsApi.shufflePile(deckId, pileName);

        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testDrawFromPile() throws IOException {
        // Arrange
        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
        String deckId = extractDeckIdFromResponse(createResponse);
        String pileName = "test_pile";
        int count = 3;

        // Act
        ResponseWrapper<String> response = deckOfCardsApi.drawFromPile(deckId, pileName, count);


        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }
}

