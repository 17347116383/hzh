package com.itdy.hqsm.elasticsearch;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.elasticsearch
 * @ClassName: VehicleTemplateService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/21 18:04
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface VehicleTemplateService {

    public void bulkIndex(List<VehicleEntity> personList);

    public List<VehicleEntity> queryForList(double lat, double lon);

    public List<VehicleEntity> queryDto();

}
