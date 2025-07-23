package dev.sideproject.ndx.service.impl;

import dev.sideproject.ndx.dto.response.ItemResponse;
import dev.sideproject.ndx.mapper.ItemMapper;
import dev.sideproject.ndx.service.ItemService;
import dev.sideproject.ndx.specifications.ItemSpecs;
import dev.sideproject.ndx.entity.Item;
import dev.sideproject.ndx.exception.AppException;
import dev.sideproject.ndx.exception.ErrorCode;
import dev.sideproject.ndx.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;

    @Override
    public Page<?> getItems(int page, int size, String direction, String orderBy, String categoryIds, Long price, String priceComparison) {
        Sort sort;
        if (Objects.nonNull(orderBy) && Objects.nonNull(direction)) {
            Sort.Direction direct = Sort.Direction.valueOf(direction.toUpperCase());
            sort = Sort.by(direct, orderBy);
        } else {
            sort = Sort.unsorted();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Map<String, Object> filters = new HashMap<>();
        if (Objects.nonNull(categoryIds)) {
            filters.put("category", categoryIds);
        }
        filters.put("isDeleted", 0);

        Specification<Item> specification = ItemSpecs.getItemSpecification(filters, priceComparison, price);

        return itemRepository.findAll(specification, pageable).map(ItemMapper::mapToItemResponse);
    }

    @Override
    public ItemResponse getItem(Integer id) {
        return itemRepository.findById(id).map(ItemMapper::mapToItemResponse).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_FOUND;
            log.error("item {}", errorCode.getMessage());
            return new AppException(errorCode);
        });
    }

    @Override
    public List<ItemResponse> search(String keyword) {
        return itemRepository.findByNameContaining(keyword)
                .stream().map(ItemMapper::mapToItemResponse).toList();
    }
}
