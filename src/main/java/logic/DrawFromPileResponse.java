package logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DrawFromPileResponse {

    public Boolean success;
    public String deck_id;
    public Integer remaining;
    public Piles piles;
    public List<Card> cards;
}