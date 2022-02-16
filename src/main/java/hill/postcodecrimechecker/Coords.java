package hill.postcodecrimechecker;

import lombok.Value;

import java.io.Serializable;

@Value
public class Coords implements Serializable {

    double latitude;

    double longitude;
}
