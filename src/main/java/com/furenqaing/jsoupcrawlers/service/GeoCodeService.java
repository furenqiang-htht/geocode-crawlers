/**
 * Copyright © 2021. All rights reserved.
 *
 * @描述: biz_statistics_relation
 * @Prject: DataHub
 * @Package: com.domain.module.statistics.relation.service
 * @ClassName: ModelService
 * @author: Cui WenKe
 * @date: 2021-07-27
 * @version: V1.0
 */
package com.furenqaing.jsoupcrawlers.service;

import com.furenqaing.jsoupcrawlers.entity.GeoCodeEntity;

import java.util.List;

/**
 * @ClassName: ModelService
 * @描述: shp存es
 * @author: Eric
 * @date: 2021年10月9日
 */
public interface GeoCodeService {
    void saveAll(List<GeoCodeEntity> geoCodeEntitys);
}

