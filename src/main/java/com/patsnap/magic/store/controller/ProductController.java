package com.patsnap.magic.store.controller;


import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.Product;
import com.patsnap.magic.store.service.IProductService;
import com.patsnap.magic.store.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(String productId) {
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<Page<Product>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iProductService.searchProductLike(keyword, pageNum, pageSize);
    }
}
