package com.itdy.hqsm.security.myshiro.service.Impl;

import com.itdy.hqsm.security.myshiro.service.FunctionService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service.Impl
 * @ClassName: FunctionServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:01
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FunctionServiceImpl  implements FunctionService {
}
