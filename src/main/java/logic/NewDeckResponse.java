package logic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDeckResponse {
    public Boolean success;
    public String deck_id;
    public Boolean shuffled;
    public Integer remaining;
}
