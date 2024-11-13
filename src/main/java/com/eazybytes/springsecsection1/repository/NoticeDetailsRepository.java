package com.eazybytes.springsecsection1.repository;

import com.eazybytes.springsecsection1.entity.NoticeDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeDetailsRepository extends CrudRepository<NoticeDetailEntity, Integer> {

    @Query("select n from NoticeDetailEntity n where  now() between n.noticBegDt and n.noticEndDt ")
    List<NoticeDetailEntity> findAllActiveNoticeDetails();
}
