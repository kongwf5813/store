package com.patsnap.magic.store.service;


import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.Product;
import com.patsnap.magic.store.vo.ProductDetailVo;
import org.springframework.data.domain.Page;


public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<Page<Product>> getProductList(int pageNum, int pageSize);

    ServerResponse<Page<Product>> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<Page<Product>> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
