package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.ItemResponse;
import org.springframework.data.domain.Page;

public interface ItemService {
    Page getItems(int page, int size, String direction, String orderBy, String categoryIds);

    ItemResponse getItem(Integer id);
}
