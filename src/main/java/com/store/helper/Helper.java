package com.store.helper;

import com.store.payloads.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(Object -> new ModelMapper().map(Object, type))
                .collect(Collectors.toList());
        PageableResponse<V> res = new PageableResponse<V>();
        res.setContent(dtoList);
        res.setPageNumber(page.getNumber());
        res.setPageSize(page.getSize());
        res.setTotalElements(page.getTotalElements());
        res.setTotalPages(page.getTotalPages());
        res.setLastPage(page.isLast());
        return res;
    }

}
