package org.jeecg.modules.zzj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zzj.entity.User;
import org.jeecg.modules.zzj.mapper.UserMapper;
import org.jeecg.modules.zzj.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
