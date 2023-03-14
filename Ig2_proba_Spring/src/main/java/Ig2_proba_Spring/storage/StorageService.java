package Ig2_proba_Spring.storage;

import org.springframework.core.io.Resource;

public interface StorageService {
    void init();
    Resource loasAsResource(String filename);
    String save(String base64);
}
