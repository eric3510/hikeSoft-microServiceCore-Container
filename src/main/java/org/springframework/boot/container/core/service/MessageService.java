package org.springframework.boot.container.core.service;

import java.util.Map;

public interface MessageService{
    void async(String serverName, String url, Map<String, Object> param);

    void async(String url, Map<String, Object> param);
}
