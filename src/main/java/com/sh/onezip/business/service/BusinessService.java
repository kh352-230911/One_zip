package com.sh.onezip.business.service;

import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.repository.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;
    // HBK start
    public Page<Business> findAllBizmembers(Pageable pageable) {
        return businessRepository.findAll(pageable);
    }

    public Business findBizmember(Long id) {
        return businessRepository.findBizmember(id);
    }

    public Optional<Business> findById(Long id) {
        return businessRepository.findById(id);
    }

    public Business updateBizAccess(Business newbusiness) {
        return businessRepository.save(newbusiness);
    }
    // HBK end
}
