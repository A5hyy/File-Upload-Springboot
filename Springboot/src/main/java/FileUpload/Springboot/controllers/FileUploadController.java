package FileUpload.Springboot.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import FileUpload.Springboot.services.AmazonS3Service;

@Controller
@RequestMapping
public class FileUploadController {

    @Autowired
    private AmazonS3Service amazonS3Service;
    
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    //return a string, *myFile from index.html, *name from index.html
    public String postUpload(
        @RequestPart MultipartFile myFile, 
        @RequestPart String name,
        Model model){

            String key = " ";

            //try-catch or throw IOException
            try {
                key = amazonS3Service.upload(myFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            
            // pass in myFile to "file"
            model.addAttribute("file", myFile);
            // pass in name to "name"
            model.addAttribute("name", name);
            // pass in key to "key"
            // convert /myUploads%2F83ad0890 to "key" which will be used as /${key} in html tymeleaf
            model.addAttribute("key", "myUploads/%s".formatted(key));

         // return html page name upload /templates/upload.html
        return "upload"; 
    }
}
