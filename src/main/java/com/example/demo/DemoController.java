package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectModel;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@RestController
public class DemoController {


    String appId = "6xLUZYEZvkMhJaphb5FBGhHNfqZDZUpBd5b7BuhV2pau";
    String sdkKey = "ARe8YTRj66xwMFBjzSbcxYceRavZQYfCya9jDgdjetZX";
    @RequestMapping(value = "/get/feature", method = RequestMethod.POST)
    public byte[]  getFaceFeatureByte(@RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
        System.out.println("call in ....");

        System.out.println("start working");
        System.out.println("start working");
        System.out.println("start working");
        //从官网获取
        String appId = "6xLUZYEZvkMhJaphb5FBGhHNfqZDZUpBd5b7BuhV2pau";
        String sdkKey = "ARe8YTRj66xwMFBjzSbcxYceRavZQYfCya9jDgdjetZX";


        FaceEngine faceEngine = new FaceEngine("/root/cvLib");
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("fail active engine1");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("fail active engine");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("fail init engine");
        }


        //人脸检测
        ImageInfo imageInfo = getRGBData(uploadFile.getBytes());
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("feature size：" + faceFeature.getFeatureData().length);

//        //人脸检测2
//        ImageInfo imageInfo2 = getRGBData(uploadFile.getBytes());
//        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
//        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(),imageInfo.getImageFormat(), faceInfoList2);
//        System.out.println(faceInfoList);
//
//        //特征提取2
//        FaceFeature faceFeature2 = new FaceFeature();
//        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(0), faceFeature2);
//        System.out.println("feature size2：" + faceFeature.getFeatureData().length);
//
//        //特征比对
//        FaceFeature targetFaceFeature = new FaceFeature();
//        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
//        FaceFeature sourceFaceFeature = new FaceFeature();
//        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
//        FaceSimilar faceSimilar = new FaceSimilar();
//
//        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
//
//        System.out.println("common：" + faceSimilar.getScore());
//
//        //设置活体测试
//        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
//        //人脸属性检测
//        FunctionConfiguration configuration = new FunctionConfiguration();
//        configuration.setSupportAge(true);
//        configuration.setSupportFace3dAngle(true);
//        configuration.setSupportGender(true);
//        configuration.setSupportLiveness(true);
//        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
//
//
//        //性别检测
//        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
//        errorCode = faceEngine.getGender(genderInfoList);
//        System.out.println("gender：" + genderInfoList.get(0).getGender());
//
//        //年龄检测
//        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
//        errorCode = faceEngine.getAge(ageInfoList);
//        System.out.println("age：" + ageInfoList.get(0).getAge());
//
//        //3D信息检测
//        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
//        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
//        System.out.println("3d：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());
//
//        //活体检测
//        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
//        errorCode = faceEngine.getLiveness(livenessInfoList);
//        System.out.println("live：" + livenessInfoList.get(0).getLiveness());
//
//
//        //IR属性处理
//        ImageInfo imageInfoGray = getGrayData(uploadFile.getBytes());
//        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
//        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);
//
//        FunctionConfiguration configuration2 = new FunctionConfiguration();
//        configuration2.setSupportIRLiveness(true);
//        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
//        //IR活体检测
//        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
//        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
//        System.out.println("IR live：" + irLivenessInfo.get(0).getLiveness());
//
//        ImageInfoEx imageInfoEx = new ImageInfoEx();
//        imageInfoEx.setHeight(imageInfo.getHeight());
//        imageInfoEx.setWidth(imageInfo.getWidth());
//        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
//        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
//        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
//        List<FaceInfo> faceInfoList1 = new ArrayList<>();
//        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);
//
//        FunctionConfiguration fun = new FunctionConfiguration();
//        fun.setSupportAge(true);
//        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
//        List<AgeInfo> ageInfoList1 = new ArrayList<>();
//        int age = faceEngine.getAge(ageInfoList1);
//        System.out.println("age2：" + ageInfoList1.get(0).getAge());
//
//        FaceFeature feature = new FaceFeature();
//        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);


        //引擎卸载
        errorCode = faceEngine.unInit();

        System.out.println("finish work..");
        System.out.println("");
        System.out.println("finish work = " + faceFeature.toString());
        return faceFeature.getFeatureData();

    }
    @RequestMapping(value = "/get/feature/url", method = RequestMethod.POST)
    public byte[]  getFaceFeatureByte2(@RequestBody FeatrureFile featrureFile) throws Exception {
        System.out.println("call in ....");

        //new一个URL对象
        URL url = new URL(featrureFile.getImage());
//打开链接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//设置请求方式为"GET"
        conn.setRequestMethod("GET");
//超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
//通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
//得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);

////         byte[] aaa  = uploadFile.getBytes();
//
//        File imageFile = new File("/Users/bob/git/aaa/demo/src/main/resources/img/88888.jpg");
//        //创建输出流
//        FileOutputStream outStream = new FileOutputStream(imageFile);
//        //写入数据
//        outStream.write(data);
        //关闭输出流
//        outStream.close();
        System.out.println("call out ....");
        System.out.println("call out ....");
        System.out.println("call out ....");
        System.out.println("call out ....");

        System.out.println("call in ....");

        System.out.println("start working");
        System.out.println("start working");
        System.out.println("start working");
        //从官网获取
        String appId = "6xLUZYEZvkMhJaphb5FBGhHNfqZDZUpBd5b7BuhV2pau";
        String sdkKey = "ARe8YTRj66xwMFBjzSbcxYceRavZQYfCya9jDgdjetZX";


        FaceEngine faceEngine = new FaceEngine("/root/cvLib");
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("fail active engine1");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("fail active engine");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("fail init engine");
        }

        //人脸检测
        ImageInfo imageInfo = getRGBData(data);
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("feature size：" + faceFeature.getFeatureData().length);
        return faceFeature.getFeatureData();

    }
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String  getFaceFeatureBytetEST() throws Exception {
        System.out.println("call in ....");
        System.out.println("call out ....");
        System.out.println("call out ....");
        System.out.println("call out ....");
        System.out.println("call out ....");
        return "data";

    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//创建一个Buffer字符串
        byte[] buffer = new byte[1024];
//每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
//使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
//关闭输入流
        inStream.close();
//把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
