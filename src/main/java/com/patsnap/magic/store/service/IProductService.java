package com.patsnap.magic.store.service;


import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.Product;
import com.patsnap.magic.store.vo.ProductDetailVo;
import org.springframework.data.domain.Page;

public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(String productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(String productId);

    ServerResponse<Page<ProductDetailVo>> getProductList(int pageNum, int pageSize);

    ServerResponse<Page<Product>> searchProductLike(String productName, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(String productId);
}
