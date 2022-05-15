package spring.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.Models.Product;
import spring.Repos.ProductRepos;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {
    @Value("${upload.path.product}")
    private String productIconPath;

    private final ProductRepos productRepos;

    public ProductService(ProductRepos productRepos) {
        this.productRepos = productRepos;
    }

    public void edit(Product product, Map<String, String> values, MultipartFile file) throws IOException {
        for (String key : values.keySet()) {
            switch (key){
                case "name":
                    product.setName(values.get(key));
                    break;
                case "priceValue":
                    product.setPriceValue(new BigDecimal(values.get(key)));
                    break;
                case "des":
                    product.setDescription(values.get(key));
                    break;
            }
        }

        File oldImage = new File(productIconPath + "/" + product.getIcoPath());
        if(oldImage.exists()){
            log.info("IN edit PRODUCT FILE {} DELETED", productIconPath + "/" + oldImage.getName());
            oldImage.delete();
        }
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadFile = new File(productIconPath);
            if(!uploadFile.exists()){
                uploadFile.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + '.' + file.getOriginalFilename();
            file.transferTo(new File(productIconPath + "/" + resultFileName));
            product.setIcoPath(resultFileName);
        }

        productRepos.save(product);
        log.info("IN edit PRODUCT {} SUCCESSFUL EDIT", product.getName());
    }
}
