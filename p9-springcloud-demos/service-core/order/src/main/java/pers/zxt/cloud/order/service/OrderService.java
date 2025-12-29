package pers.zxt.cloud.order.service;

import pers.zxt.cloud.commons.domain.Order;

public interface OrderService {

    Order createOrder(Long userId, Long productId, Integer version);

}
