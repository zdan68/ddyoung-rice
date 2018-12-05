package com.self.ddyoung.rice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Msg implements Serializable, Cloneable {
    private String binlogTime;

    private String schema;
}
