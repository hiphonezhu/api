package com.xxx.sample.api.service;

import com.java.api.jdbc.ServiceException;
import com.java.api.jdbc.dao.DataAccessor;
import com.java.api.jdbc.id.IdGenerator;
import com.java.api.jdbc.paging.Paging;
import com.java.api.util.DateUtil;
import com.xxx.sample.api.constant.Message;
import com.xxx.sample.api.result.AdvertiserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 广告主服务
 *
 * @author huangyong
 * @since 1.0.0
 */
@Service
public class AdvertiserService {

    @Autowired
    private DataAccessor dataAccessor;

    @Autowired
    private IdGenerator idGenerator;

    public Paging<AdvertiserResult> getAdvertiserPaging(int pageNumber, int pageSize, String whereCondition, String orderBy) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("whereCondition", whereCondition);
        paramMap.put("orderBy", orderBy);
        return dataAccessor.selectPaging("AdvertiserMapper.selectAdvertiserPaging", paramMap, pageNumber, pageSize);
    }

    public AdvertiserResult getAdvertiser(String advertiserId) {
        AdvertiserResult advertiserResult = dataAccessor.selectOne("AdvertiserMapper.selectAdvertiserById", advertiserId);
        if (advertiserResult == null) {
            throw new ServiceException(Message.QUERY_FAILURE);
        }
        return advertiserResult;
    }

    /**
     * @Transactional: Spring 事务注解
     * 1、只能用在 public 方法上且不会被继承；
     * 2、默认只会在遇到运行时异常才回滚，比如 NullPointException，遇到 IOException 之类必须捕获的异常不会回滚事务。
     * @param advertiserId
     * @param advertiserName
     * @param description
     */
    @Transactional
    public void updateAdvertiser(String advertiserId, String advertiserName, String description) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", advertiserId);
        paramMap.put("advertiserName", advertiserName);
        paramMap.put("description", description);
        dataAccessor.update("AdvertiserMapper.updateAdvertiser", paramMap);
    }

    @Transactional
    public void deleteAdvertiser(String advertiserId) {
        dataAccessor.update("AdvertiserMapper.deleteAdvertiser", advertiserId);
    }

    @Transactional
    public void createAdvertiser(String advertiserName, String description) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", idGenerator.generateId());
        paramMap.put("advertiserName", advertiserName);
        paramMap.put("description", description);
        paramMap.put("createdTime", DateUtil.getCurrentDateTime());
        dataAccessor.insert("AdvertiserMapper.insertAdvertiser", paramMap);
    }
}
