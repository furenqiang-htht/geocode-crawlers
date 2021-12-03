package com.furenqaing.jsoupcrawlers.task;

import com.furenqaing.jsoupcrawlers.entity.GeoCodeEntity;
import com.furenqaing.jsoupcrawlers.service.GeoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class SpringDataPipeline implements Pipeline {

    @Autowired
    GeoCodeService geoCodeService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<GeoCodeEntity> geoCodeEntitys = resultItems.get("geoCodeEntitys");
        if (geoCodeEntitys == null || geoCodeEntitys.size() == 0) {
            return;
        }
        geoCodeService.saveAll(geoCodeEntitys);
    }
}
