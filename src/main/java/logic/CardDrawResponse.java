package logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardDrawResponse {

    public Boolean success;
    public String deck_id;
    public List<Card> cards;
    public Integer remaining;

}
