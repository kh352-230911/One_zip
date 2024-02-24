package com.sh.onezip.productLog.service;

import com.sh.onezip.productLog.entity.ProductLog;
import com.sh.onezip.productLog.repository.ProductLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductLogService {

    @Autowired
    ProductLogRepository productLogRepository;
    public ProductLog createProductLog(ProductLog productLog) {
        return productLogRepository.save(productLog);
    }
}
