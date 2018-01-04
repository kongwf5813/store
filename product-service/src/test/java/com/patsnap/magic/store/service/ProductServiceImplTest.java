package com.patsnap.magic.store.service;


import com.patsnap.magic.store.BaseTest;
import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.dao.IProductDao;
import com.patsnap.magic.store.entity.Product;

import com.patsnap.magic.store.service.IProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Owen on 2017/12/31.
 */
public class ProductServiceImplTest  extends BaseTest {

    @Autowired
    private IProductService productService;

    @Test
    public void testSaveOrUpdateProduct() throws Exception {
        //测试新增和修改接口
        Product product = new Product();
        product.setStatus(Constant.ProductStatus.OFF_SALE);
        product.setCategoryId("8a80cb8160b5576f0160b55786d30000");
        product.setName("火影忍者佐助");
        product.setDetail("乐高积木 火影忍者佐助");
        product.setSubImages("/image/toy/sasike.jpg,/image/toy/sasike1.jpg,/image/toy/sasike2.jpg,/image/toy/sasike3.jpg");
        product.setPrice(new BigDecimal(16.8));
        product.setSubtitle("小孩积木玩具");
        product.setStock(9999999);
        ServerResponse result = productService.saveOrUpdateProduct(product);
        Assert.assertTrue(result.isSuccess());

        //测试修改销售状态接口
        Product product1 = (Product)result.getData();
        result = productService.setSaleStatus(product1.getId(),Constant.ProductStatus.ON_SALE);
        Assert.assertTrue(result.isSuccess());

        //测试查询产品详情接口
        result = productService.getProductDetail(product1.getId());
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void testSearchProductLike() throws Exception {
        ServerResponse result = productService.searchProductLike("火影", 1,20);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testGetProductList() throws Exception {
        ServerResponse result =  productService.getProductList(1, 20);
        Assert.assertNotNull(result.getData());
    }
}