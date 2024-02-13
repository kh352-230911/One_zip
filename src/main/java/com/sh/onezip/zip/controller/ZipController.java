package com.sh.onezip.zip.controller;

import com.sh.onezip.zip.dto.ZipCreateDto;
import com.sh.onezip.zip.dto.ZipDetailDto;
import com.sh.onezip.zip.dto.ZipUpdateDto;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.service.ZipService;
import com.sh.onezip.attachment.service.AttachmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Zip")
@Slf4j
@Validated
public class ZipController {
    @Autowired
    ZipService zipService;

    @GetMapping("/createZip.do")
    public void createZip(){}

    @PostMapping("/createZip.do")
    public String createZip(
            @Valid ZipCreateDto zipCreateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("zipCreateDto = {}", zipCreateDto);

        Zip zip = zipCreateDto.toZip();
        zip = zipService.createZip(zip);
        redirectAttributes.addFlashAttribute("msg", "집 생성을 축하합니다.");
        return "redirect:/";
    }

    @PostMapping("/updateZip.do")
    public String updateZip(@Valid ZipUpdateDto zipUpdateDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        log.debug("zipUpdateDto = {}", zipUpdateDto);
        if(bindingResult.hasErrors()){
            StringBuilder message = new StringBuilder();
            bindingResult.getAllErrors().forEach((err) -> {
                message.append(err.getDefaultMessage() + " ");
            });
            throw new RuntimeException(message.toString());
        }

        redirectAttributes.addFlashAttribute("msg", "집 정보가 수정되었습니다.");

        return "redirect:/zip/zipDetail.do";
    }
}
