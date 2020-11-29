package com.zhangjikai.sample.jpa.dto;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jikai Zhang
 * @date 2020-11-28
 */
@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, Long> {
}
