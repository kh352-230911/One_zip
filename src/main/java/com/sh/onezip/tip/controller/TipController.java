package com.sh.onezip.tip.controller;

import com.sh.onezip.tip.service.TipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class TipController {
    @Autowired
    private TipService tipService;

}
