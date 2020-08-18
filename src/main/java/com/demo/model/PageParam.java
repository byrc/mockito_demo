package com.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageParam {
    private long pageNo;
    private long pageSize;

    public static PageParam create() {
        return create(1L, 10L);
    }

    public static PageParam create(long pageNo, long pageSize) {
        return new PageParam(pageNo, pageSize);
    }

    public PageParam(long pageNo, long pageSize) {
        if (pageNo < 1L) {
            this.pageNo = 1L;
        }
        this.pageNo = pageNo < 1L ? 1L : pageNo;
        this.pageSize = pageSize <= 0L ? 10L : pageSize;
    }
}
