package hill.postcodecrimechecker.service.crimeslocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class CrimesLocationResponse {

    String category;

    String id;

    @JsonProperty("persistent_id")
    String persistentId;

    CrimesLocationLocationData location;

}
