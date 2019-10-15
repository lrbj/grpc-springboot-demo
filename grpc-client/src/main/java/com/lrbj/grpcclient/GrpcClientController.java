package com.lrbj.grpcclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService;

    @RequestMapping("/")
    public String printMessage(@RequestParam(defaultValue = "Susan") String name) {
        return grpcClientService.sendMessage(name);
    }

    @RequestMapping("/test")
    public Object printMessage(@RequestParam(defaultValue = "龙华园区") String region,
                               @RequestParam(defaultValue = "A区") String park,
                               @RequestParam(defaultValue = "A01") String building) {
        return grpcClientService.getResult(null, null, null);
    }
}
