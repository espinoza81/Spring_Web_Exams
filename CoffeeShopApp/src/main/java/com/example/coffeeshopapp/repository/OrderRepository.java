package com.example.coffeeshopapp.repository;

import com.example.coffeeshopapp.model.dtos.EmployeeWithOrderCountDTO;
import com.example.coffeeshopapp.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByPriceDesc();

    @Query("SELECT o.employee.username as username, count(o.id) as countOfOrders from Order o GROUP BY username ORDER BY countOfOrders desc")
    List<EmployeeWithOrderCountDTO> getEmployeeWithOrderCountDesc();

    //@Query("SELECT new cardealer.domain.custumer.CustomerTotalSalesDto" +
    //            "(c.name, count(s), sum(p.price*(1.0-(s.discount/100.0)))) " +
    //            "FROM Customer c " +
    //            "JOIN c.sales s " +
    //            "JOIN s.car car " +
    //            "JOIN car.parts p " +
    //            "GROUP BY c " +
    //            "ORDER BY count(s) desc, sum(p.price*(1-s.discount/100)) desc")
}
