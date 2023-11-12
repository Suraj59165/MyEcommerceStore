package com.store.controllers;

import com.store.dtos.ProductDto;
import com.store.payloads.ApiResponseMessage;
import com.store.payloads.ImageResponse;
import com.store.payloads.PageableResponse;
import com.store.services.FileService;
import com.store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    @Value("${product.images.path}")
    private String productImagePath;


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<ProductDto>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable(value = "product-id") String productId) {
        return new ResponseEntity<ProductDto>(productService.getSingleProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        return new ResponseEntity<PageableResponse<ProductDto>>(
                productService.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
            @PathVariable(value = "title") String title,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pagesize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        return new ResponseEntity<PageableResponse<ProductDto>>(
                productService.searchByTitle(title, pageNumber, pagesize, sortBy, sortDirection), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
        productService.delete(productId);
        ApiResponseMessage msg = ApiResponseMessage.builder().message("product deleted successfully")
                .status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<ApiResponseMessage>(msg, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        return new ResponseEntity<ProductDto>(productService.updateProduct(productDto, productId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId, @RequestBody MultipartFile file) throws IOException {
        String uniqueFileName = fileService.uploadImage(file, productImagePath);
        ProductDto productDto = productService.getSingleProduct(productId);
        productDto.setProductImageName(uniqueFileName);
        ProductDto productDto1 = productService.updateProduct(productDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(productDto1.getProductImageName()).msg("product with image name " + productDto1.getProductImageName()).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("image/{productId}")
    public ResponseEntity<?> serveProductimage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getSingleProduct(productId);
        byte[] imageData = fileService.getResource(productImagePath, productDto.getProductImageName());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(imageData);


    }


}
