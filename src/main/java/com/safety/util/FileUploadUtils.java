package com.safety.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/3.
 * 文件处理
 */
public class FileUploadUtils {

    //临时存放路径
    static final public String tempPath = "/home/safety/temp/";

    //正式存放路径
    static final public String realPath = "/home/safety/images/";

    //正式存放路径
    static final public String videoRealPath = "/home/safety/video/";

    //正式存放路径
    static final public String voiceRealPath = "/home/safety/voice/";

    /**
     * 上传文件到临时目录
     * @param mFile
     * @return
     */
    static public String uploadImg(MultipartFile mFile){
       // MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 得到上传的文件
        //MultipartFile mFile = multipartRequest.getFile("file");
        // 得到上传的文件的文件名
        String filename = mFile.getOriginalFilename();
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        //获取文件后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));
        //文件上传后的名字
        String uploadFileName = UUID.randomUUID()+suffixName;
        // 得到上传服务器的路径
        String path = tempPath;
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        path = path + uploadFileName;
        try {
            inputStream = mFile.getInputStream();
            outputStream = new FileOutputStream(path);
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            while ((byteCount = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, byteCount);
            }
            // 文件流写到服务器端

            if(inputStream != null)
                inputStream.close();
            if(outputStream != null)
                outputStream.close();
            return uploadFileName;
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 上传音频
     * @param mFile
     * @param src
     * @return
     */
    static public String uploadVoice(MultipartFile mFile, String src){
        // 得到上传的文件的文件名
        String filename = mFile.getOriginalFilename();
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        //获取文件后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));
        //文件上传后的名字
        String uploadFileName = UUID.randomUUID()+suffixName;
        // 得到上传服务器的路径
        String path = tempPath;
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        path = path + uploadFileName;
        try {
            inputStream = mFile.getInputStream();
            outputStream = new FileOutputStream(path);
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            while ((byteCount = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, byteCount);
            }
            // 文件流写到服务器端

            if(inputStream != null)
                inputStream.close();
            if(outputStream != null)
                outputStream.close();

            String command = "/opt/silk_convert/silk-v3-decoder-master/converter.sh "+path+" mp3";

            int status=Runtime.getRuntime().exec(command).waitFor();

            String uploadMp3Name = uploadFileName.substring(0,uploadFileName.lastIndexOf("."))+".mp3";

            File fd = new File(path);
            if(fd.exists())fd.delete();

            if(!StringUtils.isBlank(src)) {
                    String uploadedMp3Name = uploadMp3Name;
                    uploadMp3Name = UUID.randomUUID()+".mp3";


                    BufferedInputStream input1 = new BufferedInputStream(new FileInputStream(new File(tempPath + src)));  //路径格式：D://java//1.MP3
                    BufferedInputStream input2 = new BufferedInputStream(new FileInputStream(new File(tempPath + uploadedMp3Name)));
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(tempPath + uploadMp3Name)));
                    byte[] byt = new byte[1024];
                    int len;
                    while ((len=input1.read(byt)) != -1)
                    {
                        out.write(byt,0,byt.length);
                        out.flush();

                    }
                    while ((len=input2.read(byt)) != -1)
                    {
                        out.write(byt,0,byt.length);
                        out.flush();
                    }
                    input1.close();
                    input2.close();
                    out.close();
                File fs = new File(tempPath + src);
                if(fs.exists())fs.delete();
                File fsd = new File(tempPath + uploadedMp3Name);
                if(fsd.exists())fsd.delete();

            }
            return uploadMp3Name;
        } catch (IOException e){
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }



    /**
     * 移动文件到正式目录
     * @param tempName
     * @return
     */
    static public String moveFileToDir(String tempName){
        String path = realPath;
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        String tempFilePath = tempPath + tempName;
        String realFilePath = path + tempName;
        try {
            FileCopyUtils.copy(new File(tempFilePath), new File(realFilePath));
            File tempFile = new File(tempFilePath);
            tempFile.delete();
            return tempName;
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 移动视频到正式目录
     * @param tempName
     * @return
     */
    static public String moveVideoToDir(String tempName){
        String path = videoRealPath;
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        String tempFilePath = tempPath + tempName;
        String realPath = tempName.substring(0,tempName.lastIndexOf("."));
        File rf = new File(path + realPath);
        if(!rf.exists())
            rf.mkdirs();
        String realFilePath = path+ realPath+ File.separator + tempName;
        try {
            FileCopyUtils.copy(new File(tempFilePath), new File(realFilePath));
            File tempFile = new File(tempFilePath);
            tempFile.delete();

            //切割一个1分钟的视频
            //String cutName = "";
            //获取文件后缀名
            String suffixName = tempName.substring(tempName.lastIndexOf("."));
            //文件上传后的名字
            String uid = UUID.randomUUID().toString();
            File cf = new File(path + uid);
            if(!cf.exists())
                cf.mkdirs();
            String uploadFileName = uid+File.separator+uid+suffixName;
            String command = "ffmpeg -ss 00:00:00 -t 00:01:00 -i "+realFilePath+" -vcodec copy -acodec copy "+path + uploadFileName;

            int status=Runtime.getRuntime().exec(command).waitFor();
            return uploadFileName;



        } catch (IOException e){
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 移动音频到正式目录
     * @param tempName
     * @return
     */
    static public String moveVoiceToDir(String tempName){
        String path = voiceRealPath;
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();
        String tempFilePath = tempPath + tempName;
        String realFilePath = path + tempName;
        try {
            FileCopyUtils.copy(new File(tempFilePath), new File(realFilePath));
            File tempFile = new File(tempFilePath);
            tempFile.delete();
            return tempName;
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }

}
