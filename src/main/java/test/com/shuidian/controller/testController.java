package test.com.shuidian.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangcheng
 * @Date: Created in 11:54 2018/4/26
 */
@RestController
@RequestMapping("/test")
public class testController {

    private Logger logger = LoggerFactory.getLogger(testController.class);

    @RequestMapping("/str")
    public String test(String str){
        logger.info("testinfo");
        logger.warn("testwarn");
        logger.error("testerror");
        return "testValue: " + str;
    }
}
