package com.safety.Controller;




import com.safety.util.FileUploadUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/3/2.
 * 文件上传controller
 */
@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    @RequestMapping(value="/uploadFile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file){

        String path = FileUploadUtils.uploadImg(file);
        //Map<String,String> param = new HashMap<>();
        //param.put("path",path);
        return path;
    }

    @RequestMapping(value="/uploadVoice",method = RequestMethod.POST)
    public String uploadVoice(@RequestParam("file") MultipartFile file, @RequestParam(required = false) String src){

        //String path = FileUploadUtils.uploadImg(file);
        String path = FileUploadUtils.uploadVoice(file,src);
        //Map<String,String> param = new HashMap<>();
        //param.put("path",path);
        return path;
    }

    @RequestMapping(value="/uploadImg",method = RequestMethod.POST)
    public String uploadImg(@RequestParam("files[]") MultipartFile[] file){

        String path = FileUploadUtils.uploadImg(file[0]);
        //Map<String,String> param = new HashMap<>();
        //param.put("path",path);
        return "{\"path\":\""+path+"\"}";
    }


    @RequestMapping(value="/uploadAllImg",method = RequestMethod.POST)
    public String uploadAllImg(@RequestParam("files[]") MultipartFile[] file){
        String path = "";
        for (MultipartFile f:file) {
            path += FileUploadUtils.uploadImg(f)+",";
        }
        path = path.substring(0,path.lastIndexOf(","));
        //Map<String,String> param = new HashMap<>();
        //param.put("path",path);
        return "{\"path\":\""+path+"\"}";
    }
}
