package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    FaceEngineService faceEngineService = new FaceEngineService();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        faceEngineService.startFaceEngine();
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }
}
