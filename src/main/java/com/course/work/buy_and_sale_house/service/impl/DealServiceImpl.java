package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.entity.*;
import com.course.work.buy_and_sale_house.repository.DealRepository;
import com.course.work.buy_and_sale_house.service.CommissionService;
import com.course.work.buy_and_sale_house.service.DealService;
import com.course.work.buy_and_sale_house.service.PropertyForSaleService;
import com.course.work.buy_and_sale_house.service.RequestToBuyService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class DealServiceImpl implements DealService {
    @Override
    public void acceptDeal(Deal deal, User user) {
        Long userId = user.getId();
        if (deal.getBuyer().getId().equals(userId)) {
            deal.setAcceptedByBuyer(true);
        } else if (deal.getSeller().getId().equals(userId)) {
            deal.setAcceptedBySeller(true);
        }
        if (deal.isAcceptedByBuyer() && deal.isAcceptedBySeller() && !ACCEPTED.equals(deal.getStatus())) {
            deal.setStatus(ACCEPTED);
            saveDeal(deal);
        }
    }

    @Override
    public void declineDeal(Deal deal, User user) {
        Long current = user.getId();
        Long seller = deal.getSeller().getId();
        Long buyer = deal.getBuyer().getId();
        if (current.equals(seller) || current.equals(buyer)) {
            deal.setStatus(DECLINED);
            saveDeal(deal);
        }
    }

    private final DealRepository dealRepository;
    private final CommissionService commissionService;
    private RequestToBuyService requestToBuyService;
    private PropertyForSaleService propertyForSaleService;

    public DealServiceImpl(DealRepository dealRepository,
                           CommissionService commissionService,
                           RequestToBuyService requestToBuyService,
                           PropertyForSaleService propertyForSaleService
    ) {
        this.propertyForSaleService = propertyForSaleService;
        this.requestToBuyService = requestToBuyService;
        this.dealRepository = dealRepository;
        this.commissionService = commissionService;
    }

    @Override
    public void initDeal(Deal deal) {
        setActualCommission(deal);
        deal.setStatus(PENDING);
        deal.setDateOfDeal(new Date(new java.util.Date().getTime()));
        deal.setAcceptedBySeller(false);
        deal.setAcceptedByBuyer(false);
        deal.setSeller(deal.getPropertyForSale().getUser());
        deal.setBuyer(deal.getRequestToBuy().getUser());
    }

    private void setActualCommission(Deal d) {
        d.setCommission(commissionService.getActualCommission().getCommission());
    }

    @Override
    public Deal acceptDealByTwoSides(RequestToBuy buy, PropertyForSale sale) {
        Deal deal = new Deal();
        buy.setStatus(RequestToBuyService.STATUS_SOLD);
        sale.setStatus(PropertyForSaleService.STATUS_SOLD);
        deal.setRequestToBuy(buy);
        deal.setPropertyForSale(sale);
        setActualCommission(deal);
        deal.setStatus(ACCEPTED);
        deal.setDateOfDeal(new Date(new java.util.Date().getTime()));
        deal.setAcceptedBySeller(true);
        deal.setAcceptedByBuyer(true);
        deal.setSeller(deal.getPropertyForSale().getUser());
        deal.setBuyer(deal.getRequestToBuy().getUser());
        requestToBuyService.save(buy);
        propertyForSaleService.save(sale);
        dealRepository.save(deal);
        return deal;
    }

    @Override
    public void saveDeal(Deal deal) {
        dealRepository.save(deal);
    }

    @Override
    public List<Deal> findAllByBuyer(User buyer) {
        return dealRepository.findAllByBuyerOrderByDateOfDeal(buyer);
    }

    @Override
    public List<Deal> findAllBySeller(User seller) {
        return dealRepository.findAllBySellerOrderByDateOfDeal(seller);
    }


    @Override
    public Deal findDealById(Long id) {
        return dealRepository.findById(id).get();
    }

    @Override
    public ByteArrayInputStream saveReport(java.util.Date start, java.util.Date finish) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Report");
        int row_num = 0;

        Row row = sheet.createRow(row_num);
        Cell cell = row.createCell(0, CellType.STRING);
        String[] headers = new String[] {"Id", "Покупець", "Продавець", "Комісія", "Ціна" , "Виручка"};
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(header);
        }


        double priceSum = 0;
        double profitSum = 0;

        for (Deal deal : getReport(start, finish)) {
            ++row_num;
            row = sheet.createRow(row_num);
            double price = deal.getPropertyForSale().getPrice();
            double commission =  deal.getCommission();
            double profit = price * commission;
            String[] values = new String[] {
                    deal.getId().toString(),
                    deal.getBuyer().getUsername(),
                    deal.getSeller().getUsername(),
                    Double.toString(commission),
                    Double.toString(price),
                    Double.toString(profit)
            };
            for (int cellIndex = 0; cellIndex < values.length; ++cellIndex) {
                cell = row.createCell(cellIndex, CellType.STRING);
                cell.setCellValue(values[cellIndex]);
            }
            priceSum += price;
            profitSum += profit;
        }
        ++row_num;
        String[] values = new String[] {
                "Всього",
                Double.toString(priceSum),
                Double.toString(profitSum)
        };
        row = sheet.createRow(row_num);
        for (int cellIndex = 0; cellIndex < values.length; ++cellIndex) {
            cell = row.createCell(cellIndex + 3, CellType.STRING);
            cell.setCellValue(values[cellIndex]);
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            System.out.println("Report io error");
        }
        return null;
    }

    @Override
    public List<Deal> getReport(java.util.Date start, java.util.Date finish) {
        Date from = new Date(0);
        Date to = new Date(new java.util.Date().getTime());
        if (start != null) {
            from = new Date(start.getTime());
        }
        if (finish != null) {
            to = new Date(finish.getTime());
        }
        return dealRepository.findAllByDateOfDealBetweenAndStatus(from, to, ACCEPTED);
    }

    private boolean appropriate(RequestToBuy rToBuy, PropertyForSale sale) {
        List<District> districts = rToBuy.getDistrict();

        boolean districtChecker = rToBuy.isDistrictNoMatter() ||
                districts != null && districts.contains(sale.getDistrict());

        boolean areaChecker = rToBuy.isAreaNoMatter() ||
                (rToBuy.getMinArea() == null || rToBuy.getMinArea() <= sale.getArea()) &&
                        (rToBuy.getMaxArea() == null || rToBuy.getMaxArea() >= sale.getArea());

        boolean priceChecker = rToBuy.isPriceNoMatter() ||
                (rToBuy.getMinPrice() == null || rToBuy.getMinPrice() <= sale.getPrice()) &&
                        (rToBuy.getMaxPrice() == null || rToBuy.getMaxPrice() >= sale.getPrice());

        boolean typeChecker = rToBuy.getType().equals(sale.getType());

        boolean roomsChecker = rToBuy.isNumberOfRoomsNoMatter() ||
                rToBuy.getNumberOfRooms() == sale.getNumberOfRooms();

        boolean userChecker = !rToBuy.getUser().getUsername().equals(sale.getUser().getUsername());
        boolean statusChecker = rToBuy.getStatus().equals(RequestToBuyService.STATUS_OK) &&
                sale.getStatus().equals(RequestToBuyService.STATUS_OK);
        return districtChecker && areaChecker && priceChecker && typeChecker && roomsChecker && userChecker
                && statusChecker;
    }

    @Override
    public List<Deal> loadTestData(List<RequestToBuy> buying, List<PropertyForSale> sales, int count) {
        List<Deal> result = new ArrayList<>();
        int left = count;
        for (PropertyForSale sale : sales) {
            if (!sale.getStatus().equals(PropertyForSaleService.STATUS_OK)) {
                continue;
            }
            for (RequestToBuy rToBuy : buying) {
                if (appropriate(rToBuy, sale)) {
                    requestToBuyService.addPropertyForSale(sale, rToBuy);
                    propertyForSaleService.addCandidate(sale, rToBuy);
                    Deal d = new Deal();
                    d.setPropertyForSale(sale);
                    d.setRequestToBuy(rToBuy);
                    initDeal(d);
                    sale.setStatus(PropertyForSaleService.STATUS_SOLD);
                    rToBuy.setStatus(RequestToBuyService.STATUS_SOLD);
                    rToBuy.setSaleChosenByUser(sale);
                    requestToBuyService.save(rToBuy);
                    propertyForSaleService.save(sale);
                    d.setStatus(ACCEPTED);
                    d.setDateOfDeal(new Date(new java.util.Date().getTime() - left * 1000 * 60 * 60 * 24));
                    result.add(d);
                    --left;
                    break;
                }
                if (left <= 0) {
                    break;
                }
            }
            if (left <= 0) {
                break;
            }
        }
        dealRepository.saveAll(result);
        return result;
    }

    @Override
    public void deleteAll(Iterable<? extends Deal> iterable) {
        dealRepository.deleteAll(iterable);
    }
}
