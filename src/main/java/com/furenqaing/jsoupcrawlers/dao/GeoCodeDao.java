/**
 * Copyright © 2021. All rights reserved.
 *
 * @描述: biz_statistics_relation
 * @Prject: DataHub
 * @Package: com.domain.module.statistics.relation.dao
 * @ClassName: ModelDao
 * @author: Cui WenKe
 * @date: 2021-07-27
 * @version: V1.0
 */
package com.furenqaing.jsoupcrawlers.dao;

import com.furenqaing.jsoupcrawlers.entity.GeoCodeEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: Eric
 * @date: 2021年11月4日
 */
public interface GeoCodeDao extends ElasticsearchRepository<GeoCodeEntity, String> {
}

