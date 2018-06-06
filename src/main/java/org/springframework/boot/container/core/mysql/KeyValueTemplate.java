package org.springframework.boot.container.core.mysql;

import lombok.Data;

@Data
public class KeyValueTemplate{
    private String key;

    private Object value;

    private String operating;
}

