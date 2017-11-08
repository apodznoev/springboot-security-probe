package de.avpod.kickout.controller.response;

/**
 * Created by apodznoev
 * date 25.10.2017.
 */
public class HelloResponse {
    private final String helloMsg;

    public HelloResponse(String helloMsg) {
        this.helloMsg = helloMsg;
    }

    public String getHelloMsg() {
        return helloMsg;
    }
}
