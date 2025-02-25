package com.gi.hrm.query.query;


import lombok.val;

public record Page(int page, int limit) {
    public Page(int page, int limit) {
        val pageCheck = page < 1;
        if (pageCheck) {
            throw new IllegalArgumentException("page must be greater than 0");
        }
        this.page = page - 1;

        val limitCheck = limit < 1;
        if (limitCheck) {
            throw new IllegalArgumentException("limit must be greater than 0");
        }
        this.limit = limit;
    }

    public int offset() {
        return (page - 1) * limit;
    }
}
