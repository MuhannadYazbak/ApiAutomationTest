package logic;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Piles {
    private Map<String, PileDetails> pileDetailsMap = new HashMap<>();

    public Map<String, PileDetails> getPileDetailsMap() {
        return pileDetailsMap;
    }

    @JsonAnySetter
    public void addPileDetail(String name, PileDetails pileDetails) {
        pileDetailsMap.put(name, pileDetails);
    }
}
