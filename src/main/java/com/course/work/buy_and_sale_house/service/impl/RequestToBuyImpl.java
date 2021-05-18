package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;
import com.course.work.buy_and_sale_house.repository.RequestToBuyRepository;
import com.course.work.buy_and_sale_house.service.RequestToBuyService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RequestToBuyImpl implements RequestToBuyService {

    private final RequestToBuyRepository repository;

    public RequestToBuyImpl(RequestToBuyRepository repository) {
        this.repository = repository;
    }

    @Override
    public RequestToBuy findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<RequestToBuy> findAllByUserAndStatusIs(User user, String status) {
        return repository.findAllByUserAndStatusIsOrderByDateAsc(user, status);
    }

    @Override
    public List<RequestToBuy> findAppropriate(PropertyForSale propertyForSale, boolean isOwn) {
        Specification<RequestToBuy> specification = new Specification<RequestToBuy>() {
            @Override
            public Predicate toPredicate(Root<RequestToBuy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    builder) {
                List<Predicate> predicates = new ArrayList<>();

                if (propertyForSale.getArea() != null) {
                    Predicate areaNoMatter = builder.isTrue(root.get("areaNoMatter"));
                    Predicate areaMinIsNull = builder.isNull(root.get("minArea"));
                    Predicate areaMin = builder.lessThanOrEqualTo(root.get("minArea"), propertyForSale.getArea());
                    Predicate areaMinOr = builder.or(areaMinIsNull, areaMin);
                    Predicate areaMaxIsNull = builder.isNull(root.get("maxArea"));
                    Predicate areaMax = builder.greaterThanOrEqualTo(root.get("maxArea"), propertyForSale.getArea());
                    Predicate areaMaxOr = builder.or(areaMaxIsNull, areaMax);
                    Predicate areaChecker = builder.or(areaNoMatter, builder.and(areaMaxOr, areaMinOr));
                    predicates.add(areaChecker);
                }
                if (propertyForSale.getPrice() != null) {
                    Predicate priceNoMatter = builder.isTrue(root.get("priceNoMatter"));
                    Predicate priceMinIsNull = builder.isNull(root.get("minPrice"));
                    Predicate priceMin = builder.lessThanOrEqualTo(root.get("minPrice"), propertyForSale.getPrice());
                    Predicate priceMinOr = builder.or(priceMinIsNull, priceMin);
                    Predicate priceMaxIsNull = builder.isNull(root.get("maxPrice"));
                    Predicate priceMax = builder.greaterThanOrEqualTo(root.get("maxPrice"), propertyForSale.getPrice());
                    Predicate priceMaxOr = builder.or(priceMaxIsNull, priceMax);
                    Predicate priceChecker = builder.or(priceNoMatter, builder.and(priceMaxOr, priceMinOr));
                    predicates.add(priceChecker);
                }
                if (propertyForSale.getDistrict() != null) {
                    Predicate districtNoMatter = builder.isTrue(root.get("districtNoMatter"));
                    Predicate hasDistrict = builder.isMember(propertyForSale.getDistrict(), root.get("district"));
                    Predicate districtChecker = builder.or(districtNoMatter, hasDistrict);
                    predicates.add(districtChecker);
                }
                if (propertyForSale.getNumberOfRooms() != null) {
                    Predicate numberOfRoomsNoMatter = builder.isTrue(root.get("numberOfRoomsNoMatter"));
                    Predicate roomsEquals = builder.equal(root.get("numberOfRooms"), propertyForSale.getNumberOfRooms());
                    Predicate roomsChecker = builder.or(numberOfRoomsNoMatter, roomsEquals);
                    predicates.add(roomsChecker);
                }
                if (propertyForSale.getType() != null && !propertyForSale.getType().isEmpty()) {
                    predicates.add(builder.equal(root.get("type"), propertyForSale.getType()));
                }
                if (propertyForSale.getUser() != null) {
                    if (isOwn) {
                        predicates.add(builder.equal(root.get("user"), propertyForSale.getUser()));
                    } else {
                        predicates.add(builder.notEqual(root.get("user"), propertyForSale.getUser()));
                        predicates.add(builder.equal(root.get("status"), STATUS_OK));
                    }
                }
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return repository.findAll(specification);
    }

    @Override
    public void addPropertyForSale(PropertyForSale propertyForSale, RequestToBuy requestToBuy) {
        if (requestToBuy.getSelectedSale() == null) {
            requestToBuy.setSelectedSale(new ArrayList<>());
        }
        if (!requestToBuy.getSelectedSale().contains(propertyForSale)) {
            requestToBuy.getSelectedSale().add(propertyForSale);
            repository.save(requestToBuy);
        }
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(RequestToBuy requestToBuy) {
        repository.save(requestToBuy);
    }

    @Override
    public List<RequestToBuy> loadTestData(List<District> districts, User user, int count) {
        Double[] prices = new Double[] {1000.0, 2000.0, 1000.0, 4000.0, 5000.0};
        String[] description = new String[] {
                "Куплю будівлю", "Куплю будівлю, роздільний санвузол", "Куплю будівлю, 1 поверх"
        };
        int day = 1000 * 60 * 60 * 24;
        int[] numbersOfRooms = new int[] {1, 2, 3, 1, 4, 5};
        double[] areas = new double[] {10.0, 20, 30, 12, 50, 20, 30};
        List<RequestToBuy> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            RequestToBuy requestToBuy = new RequestToBuy();
            requestToBuy.setUser(user);
            requestToBuy.setCountOfViews(0);
            requestToBuy.setDate(new java.sql.Date(new Date().getTime() - ((i * (i + 1) % 7) * day)));
            requestToBuy.setDescription(description[(i * i * i + 10 + i) % description.length]);
            if (i * i + 1 % 4 != 1) {
                double area = areas[(i + 3 * i) % areas.length];
                requestToBuy.setMaxArea(area + 5);
                requestToBuy.setMinArea(area - 5);
                requestToBuy.setAreaNoMatter(false);
            } else {
                requestToBuy.setAreaNoMatter(true);
            }

            if (i % 4 == 0) {
                requestToBuy.setPriceNoMatter(true);
            } else {
                double price = prices[(i * i + 20 * i) % prices.length];
                requestToBuy.setMaxPrice(price + 500);
                requestToBuy.setMinPrice(price - 500);
                requestToBuy.setPriceNoMatter(false);
            }

            requestToBuy.setNumberOfRooms(numbersOfRooms[i % numbersOfRooms.length]);
            List<District> selectedDistricts = new ArrayList<>();
            if (i % 3 == 0) {
                selectedDistricts.add(districts.get((i + 5) % districts.size()));
                selectedDistricts.add(districts.get((i + 4) % districts.size()));
            } else if (i % 3 == 1){
                selectedDistricts.add(districts.get((i + 5) % districts.size()));
            } else {
                requestToBuy.setDistrictNoMatter(true);
            }
            requestToBuy.setDistrict(selectedDistricts);
            requestToBuy.setStatus(STATUS_OK);
            requestToBuy.setType(i % 3 == 0? TYPE_APARTMENT: TYPE_HOUSE);
            save(requestToBuy);
            result.add(requestToBuy);
        }
        return result;
    }

    @Override
    public void deleteAll(Iterable<? extends RequestToBuy> iterable) {
        repository.deleteAll(iterable);
    }

    @Override
    public List<RequestToBuy> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }
}
