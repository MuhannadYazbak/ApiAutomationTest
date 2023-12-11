package logic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToPileResponse {

    public Boolean success;
    public String deck_id;
    public Integer remaining;
    public Piles piles;

}