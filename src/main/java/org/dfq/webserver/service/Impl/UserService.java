package org.dfq.webserver.service.Impl;

import org.dfq.webserver.models.ServiceRes;
import org.dfq.webserver.models.User;

public interface UserService {

    // 注册
    ServiceRes register(User user);

    // 登录
    ServiceRes login(User user);

    //改密
    ServiceRes changePassWord(User user);

}

