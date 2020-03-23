package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class DemoController {


    @Autowired
    FaceEngineService faceEngineService;

    @RequestMapping(value = "/get/feature/url", method = RequestMethod.POST)
    public byte[]  getFaceFeatureByte2(@RequestBody FeatrureFile featrureFile) throws Exception {
        return faceEngineService.getFeatureData(featrureFile.getImage());

    }
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String  getFaceFeatureBytetEST() throws Exception {
        System.out.println("call in ....");
        return "data";
    }



}
