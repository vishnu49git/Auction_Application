package com.myauction.auction.service;
import com.myauction.auction.model.Product;
import com.myauction.auction.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElse(null);
    }
































//    public void deleteProduct(Long productId){
//        productRepository.deleteById(productId);
//    }

//    public String saveImgPath(Long productId ,String imgUrl){
//
//        Optional<Product> product = productRepository.findByProductId(productId);
//        if(product.isPresent()){
//            product.get().setImgUrl(imgUrl);
//            productRepository.save(product.get());
//            return "image path saved succesfully";
//        }
//        else{
//            return "Product not found";
//        }

    //    private final String uploadDir = "uploads";
//    public String createProduct(ProductDto product) throws IOException {
//        Product product1 = new Product();
//        product1.setProductName(product.getProductName());
//        product1.setDescription(product.getDescription());
//        product1.setSku(product.getSku());
//        productRepository.save(product1);
//        return "Product added ";
//    }






}

