package com.dizma.dizmademo.schedule;

import com.dizma.dizmademo.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrdersCheckerScheduler {

    private final OrderService orderService;

    public OrdersCheckerScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void checkOrdersCreation() {
        if (this.orderService.getOrdersCount() > 0)
        {
            this.orderService.deleteOldOrders();
        }
    }
}
