package com.gravitee.lcd;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Digit {
    private boolean top;
    private boolean bottom;
    private boolean topRight;
    private boolean bottomRight;
    private boolean topLeft;
    private boolean bottomLeft;
    private boolean middle;
}