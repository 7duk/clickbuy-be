package dev.sideproject.ndx.service;

import dev.sideproject.ndx.dto.response.ItemResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {
    Page<?> getItems(int page, int size, String direction, String orderBy, String categoryIds, Long price, String priceComparison);

    ItemResponse getItem(Integer id);

    List<ItemResponse> search(String keyword);
}
