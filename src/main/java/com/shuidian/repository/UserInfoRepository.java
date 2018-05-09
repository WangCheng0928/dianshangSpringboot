package com.shuidian.repository;

import com.shuidian.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @Author: wangcheng
 * @Date: Created in 16:11 2018/2/27
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
    UserInfo findByUsernameAndPassword(String username, String password);

    @Override
    UserInfo findOne(Integer integer);

    public Long countByUsername(String username);
    public Long countByEmail(String email);
    public Long countByPhone(String phone);
    public int countByUsernameAndQuestionAndAnswer(String username, String question, String answer);
    public int countByPasswordAndId(String password, int Id);

    @Query(value = "SELECT userinfo.question FROM UserInfo userinfo WHERE userinfo.username=?1")
    public String selectQuestionByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update UserInfo userinfo set userinfo.password = ?1 where userinfo.username = ?2")
    int updatePasswordByUsername(String passwordNew, String username);

    @Transactional
    @Modifying
    @Query(value = "update UserInfo userinfo set userinfo.password = ?1 where userinfo.id = ?2")
    int updatePasswordById(String passwordNew, Integer id);
}
