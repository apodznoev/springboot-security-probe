package de.avpod.kickout.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by apodznoev
 * date 25.10.2017.
 */
@Service
public class HelloService {
    private final Logger log = LoggerFactory.getLogger(HelloService.class);

    public String getHello(String qrSourceId) {
        return "Hello, " + qrSourceId;
    }
}
