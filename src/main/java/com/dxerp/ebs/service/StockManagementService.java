package com.dxerp.ebs.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockManagementService {
     @Autowired
    private JdbcTemplate jdbcTemplate;

    public void purchaseProduct(Long productId, BigDecimal quantity, Long locationId, Long createdBy, String remarks) {
        String sql = "BEGIN stock_management_pkg.purchase_product(?, ?, ?, ?, ?); END;";
        jdbcTemplate.update(sql, productId, quantity, locationId, createdBy, remarks);
    }

    public void saleProduct(Long productId, BigDecimal quantity, Long locationId, Long createdBy, String remarks) {
        String sql = "BEGIN stock_management_pkg.sale_product(?, ?, ?, ?, ?); END;";
        jdbcTemplate.update(sql, productId, quantity, locationId, createdBy, remarks);
    }   
}
