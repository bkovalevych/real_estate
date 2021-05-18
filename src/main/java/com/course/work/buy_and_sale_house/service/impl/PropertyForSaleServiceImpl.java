package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;
import com.course.work.buy_and_sale_house.repository.PropertyForSaleRepository;
import com.course.work.buy_and_sale_house.service.PropertyForSaleService;
import com.course.work.buy_and_sale_house.service.RequestToBuyService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PropertyForSaleServiceImpl implements PropertyForSaleService {
    private final PropertyForSaleRepository repository;
    private final RequestToBuyService requestToBuyService;

    public PropertyForSaleServiceImpl(PropertyForSaleRepository repository, RequestToBuyService requestToBuyService) {
        this.repository = repository;
        this.requestToBuyService = requestToBuyService;
    }

    @Override
    public PropertyForSale findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<PropertyForSale> findAllByUserAndStatusIs(User user, String status) {
        return repository.findAllByUserAndStatusIs(user, status);
    }

    @Override
    public List<PropertyForSale> findAppropriate(RequestToBuy requestToBuy, boolean isOwn) {
        Specification<PropertyForSale> specification = new Specification<PropertyForSale>() {
            @Override
            public Predicate toPredicate(Root<PropertyForSale> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    builder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!requestToBuy.isAreaNoMatter()) {
                    if (requestToBuy.getMaxArea() != null && requestToBuy.getMinArea() != null) {
                        predicates.add(builder.between(root.get("area"), requestToBuy.getMinArea(),
                                requestToBuy.getMaxArea()));
                    }
                    else if (requestToBuy.getMaxArea() != null) {
                        predicates.add(builder.lessThanOrEqualTo(root.get("area"), requestToBuy.getMaxArea()));
                    }
                    else if (requestToBuy.getMinArea() != null) {
                        predicates.add(builder.greaterThanOrEqualTo(root.get("area"), requestToBuy.getMinArea()));
                    }
                }
                if (!requestToBuy.isDistrictNoMatter() && requestToBuy.getDistrict().size() > 0) {
                    CriteriaBuilder.In<District> inClause = builder.in(root.get("district"));
                    for (District d : requestToBuy.getDistrict()) {
                        inClause.value(d);
                    }
                    predicates.add(inClause);
                }
                if (!requestToBuy.isNumberOfRoomsNoMatter()) {
                    predicates.add(builder.equal(root.get("numberOfRooms"), requestToBuy.getNumberOfRooms()));
                }
                if (!requestToBuy.isPriceNoMatter()) {
                    if (requestToBuy.getMaxPrice() != null && requestToBuy.getMinPrice() != null) {
                        predicates.add(builder.between(root.get("price"), requestToBuy.getMinPrice(),
                                requestToBuy.getMaxPrice()));
                    }
                    else if (requestToBuy.getMaxPrice() != null) {
                        predicates.add(builder.lessThanOrEqualTo(root.get("price"), requestToBuy.getMaxPrice()));
                    }
                    else if (requestToBuy.getMinPrice() != null) {
                        predicates.add(builder.greaterThanOrEqualTo(root.get("price"), requestToBuy.getMinPrice()));
                    }
                }

                if (isOwn) {
                    predicates.add(builder.equal(root.get("user"), requestToBuy.getUser()));
                } else {
                    predicates.add(builder.equal(root.get("status"), STATUS_OK));
                    predicates.add(builder.notEqual(root.get("user"), requestToBuy.getUser()));
                }

                if (requestToBuy.getType() != null && !requestToBuy.getType().isEmpty()) {
                    predicates.add(builder.equal(root.get("type"), requestToBuy.getType()));
                }

                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return repository.findAll(specification);
    }
    @Override
    public void addCandidate(PropertyForSale sale, RequestToBuy req) {
        if (sale.getUser().equals(req.getUser())) {
            return;
        }
        if (sale.getCandidates() == null) {
            sale.setCandidates(new ArrayList<>());
        }
        if (sale.getCandidates().contains(req)) {
            return;
        }
        sale.getCandidates().add(req);
        requestToBuyService.addPropertyForSale(sale, req);
        if (sale.getSelectedRequestToBuy() == null) {
            sale.setSelectedRequestToBuy(new ArrayList<>());
        }
        sale.getSelectedRequestToBuy().remove(req);


        repository.save(sale);
    }

    @Override
    public void addRequestToBuy(RequestToBuy requestToBuy, PropertyForSale propertyForSale) {
        if (propertyForSale.getSelectedRequestToBuy() == null) {
            propertyForSale.setSelectedRequestToBuy(new ArrayList<>());
        }
        boolean check = propertyForSale.getSelectedRequestToBuy().contains(requestToBuy);
        if (check) {
            propertyForSale.getSelectedRequestToBuy().add(requestToBuy);
            repository.save(propertyForSale);
        }
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    @Override
    public void save(PropertyForSale propertyForSale) {
        repository.save(propertyForSale);
    }

    @Override
    public List<PropertyForSale> loadTestData(List<District> districts, User user, int count) {
            String[] districtsNames = new String[] {
                    "Аверіна, 10", "Авангардна, 21", "Академіка Павлова, 12/2", "Академічна, 2",
                    "Сумська, 14", "Академіка Волкова", "Академіка Волшина", "Багратіона", "Базарна", "Бакуліна"
            };
            Double[] prices = new Double[] {1000.0, 2000.0, 1000.0, 4000.0};
            String[] description = new String[] {
                    "Продам будівлю", "Продам будівлю, гарний вид на місто", "Продам будинок на 1 поверсі" , "Продам будинок на 2 поверсі", "Продам будинок на 3 поверсі, 3 кімнати"
            };
            int day = 1000 * 60 * 60 * 24;
            int[] numbersOfRooms = new int[] {1, 2, 3, 1, 4, 5};
            double[] areas = new double[] {10.0, 20, 30, 12, 50, 20, 30};
            List<PropertyForSale> result = new ArrayList<>();
            for (int i = 0; i < count; ++i) {
                PropertyForSale propertyForSale = new PropertyForSale();
                propertyForSale.setAddress(districtsNames[(i + 5) % districtsNames.length]);
                propertyForSale.setArea(areas[(i + 3 * i) % areas.length]);
                propertyForSale.setCountOfViews(0);
                propertyForSale.setDate(new java.sql.Date(new Date().getTime() - ((i * (i + 1) % 7) * day)));
                propertyForSale.setDescription(description[(i * i * i + 10 + i) % description.length]);
                propertyForSale.setPrice(prices[(i * i + 20 * i) % prices.length]);
                propertyForSale.setNumberOfRooms(numbersOfRooms[i % numbersOfRooms.length]);
                propertyForSale.setDistrict(districts.get((i + 5) % districts.size()));
                propertyForSale.setUser(user);
                propertyForSale.setStatus(STATUS_OK);
                propertyForSale.setType(i % 3 == 0? TYPE_APARTMENT: TYPE_HOUSE);
                save(propertyForSale);
                result.add(propertyForSale);
            }
            return result;
    }

    @Override
    public void deleteAll(Iterable<? extends PropertyForSale> iterable) {
        repository.deleteAll(iterable);
    }

    @Override
    public List<PropertyForSale> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }
}
