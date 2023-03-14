package Ig2_proba_Spring.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation; // Вказуємо шлях до нашої папки з фото
    public FileSystemStorageService(StorageProperties properties){
        this.rootLocation=Paths.get(properties.getLocation());
    }
    @Override
    public void init() {
        //Перевіряємо чи пака для наших фото існує. Якщо ні - то створюємо
        try {
            if(!Files.exists(rootLocation))
                Files.createDirectories(rootLocation);
        }catch (IOException ex){
            throw new StorageException("Помилка створення папки");
        }
    }

    @Override
    public Resource loasAsResource(String fileName) {
        try {
            Path file=rootLocation.resolve(fileName);
            Resource resource =new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            throw new StorageException("Проблема при роботі з файлом"+fileName);
        }catch (MalformedURLException e){
            throw new StorageException("Файл не знайдено"+fileName);
        }
    }

    @Override
    public String save(String base64) {
        try {
            if (base64.isEmpty()) {
                throw new StorageException("Пустий base64");
            }
            UUID uuid = UUID.randomUUID();
            String randomFileName = uuid.toString() + ".jpg";
            String[] charArray = base64.split(","); //Відсчення по комі (",") службової інформації від байтів при кодуванні base64 і поміщаємо в масив
            Base64.Decoder decoder = Base64.getDecoder(); //отримуємо декодера яким будемо декодувати
            byte[] bytes = new byte[0]; // Масив в який будемо поміщати отримані з потока байти
            bytes = decoder.decode(charArray[1]);
            String folder = rootLocation.toString() + "/" + randomFileName;
            new FileOutputStream(folder).write(bytes);
            return randomFileName;
        } catch (IOException e) {
            throw new StorageException("Проблема при збережні та перетворені base64", e);
        }

    }

    /**  @Override
      public String save(String base64) {
          try {
              if(base64.isEmpty()) {
                  throw new StorageException("Пустий base64");
              }
              UUID uuid = UUID.randomUUID();
              String [] charArray = base64.split(","); //Відсчення по комі (",") службової інформації від байтів при кодуванні base64 і поміщаємо в масив
              String extension;
              switch(charArray[0]) {
                  case "data:image/png;base64":
                      extension="png";
                      break;
                  default:
                      extension="jpg";
                      break;
              }
              String randomFileName = uuid.toString()+"."+extension; //Рандомне ім'я під яким буде зберігатись фото
              Base64.Decoder decoder = Base64.getDecoder(); //отримуємо декодера яким будемо декодувати
              byte [] bytes = new byte[0]; // Масив в який будемо поміщати отримані з потока байти
              bytes = decoder.decode(charArray[1]);
              int [] imageSize = {32,150, 300, 600, 1200};
              try(var byteStream = new ByteArrayInputStream(bytes)) {
                  var image = ImageIO.read(byteStream);
                  // цикл запису та перетворення файла в різних розмірах
                  for(int size : imageSize) {
                      String fileSaveItem = rootLocation.toString()+"/"+size+"_"+randomFileName; // Створюємо  унікальне ім'я файлу
                      BufferedImage newImg = ImageUtils.resizeImage(image,
                              extension=="jpg" ? ImageUtils.IMAGE_JPEG: ImageUtils.IMAGE_PNG, size, size); // перетворюємо фото в інші розміри
                      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                      ImageIO.write(newImg, extension, byteArrayOutputStream);
                      byte [] newBytes = byteArrayOutputStream.toByteArray();
                      FileOutputStream out = new FileOutputStream(fileSaveItem);
                      out.write(newBytes);
                      out.close();
                  }
              } catch(IOException e) {
                  throw new StorageException("Проблема перетворення фото", e);
              }
              return randomFileName;

          } catch(StorageException e) {
              throw new StorageException("Проблема при збережні та перетворені base64",e);
          }
      } **/


}
