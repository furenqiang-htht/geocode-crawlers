/**
 * Copyright © 2021. All rights reserved.
 *
 * @描述: biz_statistics_relation
 * @Prject: DataHub
 * @Package: com.domain.module.statistics.relation.service
 * @ClassName: ModelServiceImpl
 * @author: Cui WenKe
 * @date: 2021-07-27
 * @version: V1.0
 */
package com.furenqaing.jsoupcrawlers.service.impl;

import com.furenqaing.jsoupcrawlers.dao.GeoCodeDao;
import com.furenqaing.jsoupcrawlers.entity.GeoCodeEntity;
import com.furenqaing.jsoupcrawlers.service.GeoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ModelServiceImpl
 * @描述: shp存es
 * @author: Eric
 * @date: 2021年10月9日
 */
@Service
public class GeoCodeServiceImpl implements GeoCodeService {

    @Autowired
    GeoCodeDao geoCodeDao;

    @Override
    public void saveAll(List<GeoCodeEntity> geoCodeEntitys) {
        geoCodeDao.saveAll(geoCodeEntitys);
    }
}

