package test;

import com.fasterxml.jackson.databind.JsonNode;
import infra.HttpFacade;
import infra.ResponseWrapper;
import logic.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static infra.ObjectMapperProvider.getObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckOfCardsApiTest {
    private CloseableHttpClient httpClient;
    private DeckOfCardsApi deckOfCardsApi;

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
        String deckId = deckOfCardsApi.createNewDeck().getData().getDeck_id();
        System.out.println("Deck ID: " + deckId);

        // Act
        ResponseWrapper<NewDeckResponse> response = deckOfCardsApi.shuffleDeck(deckId);

        // Log response details
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Content: " + response.getData());
        System.out.println("Error Message: " + response.getErrorMessage());

        // Assert
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getData().getShuffled());
    }


    @Test
    void testCreateNewDeck() throws IOException {
        // Act
        ResponseWrapper<NewDeckResponse> response = deckOfCardsApi.createNewDeck();
        //String deckId = deckOfCardsApi.createNewDeck().getData().getDeckId();
        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testDrawCard() throws IOException {
        // Arrange
        //String deckId = "new";
        String deckId = deckOfCardsApi.createNewDeck().getData().getDeck_id();
        int count = 2;

        // Act
        ResponseWrapper<CardDrawResponse> response = deckOfCardsApi.drawCard(deckId, count);

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals(50,response.getData().remaining);
    }

    @Test
    void testCreatePile() throws IOException {
        // Arrange
        //ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
        //String deckId = extractDeckIdFromResponse(createResponse);
        String deckId = deckOfCardsApi.createNewDeck().getData().getDeck_id();
        String pileName = "test_pile";

        // Act
        ResponseWrapper<AddToPileResponse> response = deckOfCardsApi.createPile(deckId, pileName);

        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }

    @Test
    void testAddToPile() throws IOException {
        // Arrange
//        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
//        String deckId = extractDeckIdFromResponse(createResponse);
        String deckId = deckOfCardsApi.createNewDeck().getData().getDeck_id();
        String pileName = "test_pile";
        int count = 2;

        // Act
        ResponseWrapper<AddToPileResponse> response = deckOfCardsApi.addToPile(deckId, pileName, count);

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals(response.getData().getPiles().getDiscard().getRemaining(), count);
    }

    @Test
    void testShufflePile() throws IOException {
        // Arrange
//        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
//        String deckId = extractDeckIdFromResponse(createResponse);
        String deckId = deckOfCardsApi.createNewDeck().getData().getDeck_id();
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
//        ResponseWrapper<String> createResponse = deckOfCardsApi.createNewDeck();
//        String deckId = extractDeckIdFromResponse(createResponse);
        String deckId = deckOfCardsApi.createNewDeck().getData().getDeck_id();
        String pileName = "test_pile";
        int count = 3;

        // Act
        ResponseWrapper<DrawFromPileResponse> response = deckOfCardsApi.drawFromPile(deckId, pileName, count);


        // Assert
        assertEquals(200, response.getStatusCode());
        // Perform additional data and status validations as needed
    }
}

