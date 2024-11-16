package org.dfq.webserver.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dfq.webserver.models.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

