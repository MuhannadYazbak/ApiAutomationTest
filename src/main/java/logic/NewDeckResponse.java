package logic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDeckResponse {
    public Boolean success;
    public String deckId;
    public Boolean shuffled;
    public Integer remaining;
}
