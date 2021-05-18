package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.repository.DealRepository;
import org.springframework.stereotype.Component;

@Component
public class StatusChecker {

    private DealRepository dealRepository;

    public StatusChecker(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
        new Thread(()->{
//            while (true) {
//                try {
//                    List<Deal> deals = dealRepository.findAll();
//                    Date today = new Date();
//                    for (Deal deal : deals) {
//                        if (deal.getArrivalDate().before(today)
//                                && deal.getDepartureDate().before(today) && deal.getStatus().equals(Constant.STATUS_ACCEPTED)) {
//                            deal.setStatus(Constant.STATUS_COMPLETED);
//                            dealRepository.save(deal);
//                        }
//                    }
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }).start();
    }
}
