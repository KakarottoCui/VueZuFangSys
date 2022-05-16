package com.tian.myhouse.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * 图像处理类，将上传图片转换为BASE64格式
 */
@RestController
public class PicUpController {

    @RequestMapping(value = "/picup",method = RequestMethod.POST)
     public String picUpload(MultipartFile file) throws IOException {
        //System.out.println(file.getOriginalFilename());
        BASE64Encoder bEncoder=new BASE64Encoder();
        String base64EncoderImg = bEncoder.encode(file.getBytes());
        //返回图片的hase64格式
        //System.out.println(base64EncoderImg);
        return base64EncoderImg;
    }

}
