package hill.postcodecrimechecker.service.crimeslocation;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class CrimesLocationLocationData {

    Double latitude;

    Double longitude;
}
