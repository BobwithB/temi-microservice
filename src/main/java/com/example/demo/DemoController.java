package com.example.demo;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@RestController
public class DemoController {


    @Autowired
    FaceEngineService faceEngineService;

//    @RequestMapping(value = "/get/feature", method = RequestMethod.POST)
//    public byte[]  getFaceFeatureByte(@RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
//
//
//        //人脸检测
//        ImageInfo imageInfo = getRGBData(uploadFile.getBytes());
//        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
//        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
//        System.out.println(faceInfoList);
//
//        //特征提取
//        FaceFeature faceFeature = new FaceFeature();
//        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
//        System.out.println("feature size：" + faceFeature.getFeatureData().length);
//
//        System.out.println("finish work..");
//        return faceFeature.getFeatureData();
//
//    }
    @RequestMapping(value = "/get/feature/url", method = RequestMethod.POST)
    public byte[]  getFaceFeatureByte2(@RequestBody FeatrureFile featrureFile) throws Exception {
//        System.out.println("call in ....");
//        URL url = new URL(featrureFile.getImage());
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setConnectTimeout(5 * 1000);
//        InputStream inStream = conn.getInputStream();
//        byte[] data = readInputStream(inStream);
//        int errorCode = 0;
//        //人脸检测
//        ImageInfo imageInfo = getRGBData(data);
//        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
//        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
//        //特征提取
//        FaceFeature faceFeature = new FaceFeature();
//        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
//        System.out.println("feature size：" + faceFeature.getFeatureData().length);
        return faceEngineService.getFeatureData(featrureFile.getImage());

    }
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String  getFaceFeatureBytetEST() throws Exception {
        System.out.println("call in ....");
        return "data";

    }



}
