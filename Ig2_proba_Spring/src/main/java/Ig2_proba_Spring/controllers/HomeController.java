package Ig2_proba_Spring.controllers;

import Ig2_proba_Spring.dto.UploadImagesDto;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import Ig2_proba_Spring.dto.UploadImagesDЕЩ;
import Ig2_proba_Spring.storage.StorageService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@AllArgsConstructor
public class HomeController {
private final StorageService storageService;
    @GetMapping("/")
    public String index() { return "Hello Java Spring Boot"; }


    //Повернення файлів з сервера
    @GetMapping("/files/{filename:.+}")                 //Шлях по якому дістаються файли з сервера
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) throws  Exception {
        Resource file = storageService.loasAsResource(filename);
        String urlFileName= URLEncoder.encode("Сало.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()                      //Передаєм файл з сервера через контролер
                .contentType(MediaType.IMAGE_JPEG)      //тип файла
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\""+urlFileName+"\"") //Кажемо бровзеру що це за контент
                .body(file);                            //сам файл котрий вертаємо
    }
    @PostMapping("/upload") //Загрузка фото
    public String upload(@RequestBody UploadImagesDto dto){
        String fileName=storageService.save(dto.getBase64());
        return fileName; //Повертаємо ім'я файла під яким загрузилось фото
    }
 //  @PostMapping(value = "/form/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 //   public String formUpload(@RequestParam("file") MultipartFile file){
    //      String fileName=storageService.saveMultipartFile(file);
   //     return fileName;
   // }
}
