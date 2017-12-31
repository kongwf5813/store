package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ResponseCode;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.dao.ICategoryDao;
import com.patsnap.magic.store.dao.IProductDao;
import com.patsnap.magic.store.entity.Category;
import com.patsnap.magic.store.entity.Product;
import com.patsnap.magic.store.vo.ProductDetailVo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            //设置第一张图片为主图片
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (productDao.save(product) != null) {
                return ServerResponse.createBySuccess("更新产品成功");
            }
            return ServerResponse.createBySuccess("更新产品失败");
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse<String> setSaleStatus(String productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productDao.findOne(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        product.setStatus(status);
        if (productDao.save(product) != null) {
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(String productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productDao.findOne(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = this.setProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<Page<ProductDetailVo>> getProductList(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.ASC, "name");
        Page<Product> products = productDao.findAll(pageable);
        List<ProductDetailVo> productDetailVoList = new ArrayList<>();
        products.forEach(product ->
                productDetailVoList.add(this.setProductDetailVo(product)));
        return ServerResponse.createBySuccess(new PageImpl(productDetailVoList));
    }

    @Override
    public ServerResponse<Page<Product>> searchProductLike(String productName, int pageNum, int pageSize) {
        if (StringUtils.isBlank(productName)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.ASC, "name");
        String keyword = "%" + productName + "%";
        Page<Product> products = productDao.findProductsByNameLike(keyword, pageable);
        return ServerResponse.createBySuccess(products);
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(String productId) {
        if (StringUtils.isBlank(productId)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productDao.findOne(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if (product.getStatus() != Constant.ProductStatus.ON_SALE) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        return ServerResponse.createBySuccess(this.setProductDetailVo(product));
    }

    private ProductDetailVo setProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost("");

        Category category = categoryDao.findOne(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParentCategoryId(null);//默认根节点
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(product.getCreateTime());
        productDetailVo.setUpdateTime(product.getUpdateTime());

        return productDetailVo;
    }
}
