package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.vo.OrderVo;
import org.springframework.data.domain.Page;

/**
 * Created by Owen on 2017/12/31.
 */
public interface IOrderService {

    ServerResponse<String> cancel(String userId,Long orderNo);
    ServerResponse getOrderCartProduct(String userId);
    ServerResponse<OrderVo> getOrderDetail(String userId, Long orderNo);
    ServerResponse<Page<OrderVo>> getOrderList(String userId, int pageNum, int pageSize);
}
