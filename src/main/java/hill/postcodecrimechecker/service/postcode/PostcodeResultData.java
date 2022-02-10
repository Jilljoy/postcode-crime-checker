package hill.postcodecrimechecker.service.postcode;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class PostcodeResultData {

    String postcode;

    Double latitude;

    Double longitude;
}
