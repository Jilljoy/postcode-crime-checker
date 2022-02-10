package hill.postcodecrimechecker.service.postcode;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class PostcodeResponseData {

    String status;

    PostcodeResultData result;
}
