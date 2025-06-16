package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.response.ItemResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {
    Page getItems(int page, int size, String direction, String orderBy, String categoryIds, Long price, String priceComparision);

    ItemResponse getItem(Integer id);

    List<ItemResponse> search(String keyword);
}
