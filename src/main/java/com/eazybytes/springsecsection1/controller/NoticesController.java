package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.entity.NoticeDetailEntity;
import com.eazybytes.springsecsection1.repository.NoticeDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class NoticesController {

    private final NoticeDetailsRepository noticeDetailsRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDetailEntity>> getNotices() {
//        this.noticeDetailsRepository.findAll();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(120, TimeUnit.SECONDS))
                .body(noticeDetailsRepository.findAllActiveNoticeDetails());
    }

}
