package org.dfq.webserver.security;

import org.dfq.webserver.models.User;
import java.util.Map;

public class pacUtil {
    public User getUser(Map map) {
        return (User) map.get("user");
    }
}
