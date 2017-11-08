package de.avpod.kickout.controller;

import de.avpod.kickout.controller.response.HelloResponse;
import de.avpod.kickout.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by apodznoev
 * date 25.10.2017.
 */
@RestController
@RequestMapping("/v1/hello")
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(HelloController.class);
    private final HelloService helloService;

    public HelloController(@Autowired HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HelloResponse testMethod(@RequestParam(value = "qrSourceId", required = false) String qrSourceId) {
        log.info("Requesting hello for qr source id {}", qrSourceId);
        String msg = helloService.getHello(qrSourceId);
        return new HelloResponse(msg);
    }


}
