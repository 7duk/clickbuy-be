package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.service.ItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("item")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController extends Controller {
    ItemService itemService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<?> getItems(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size,
                                      @RequestParam(name = "order_by", required = false) String orderBy,
                                      @RequestParam(name = "direction", required = false) String direction,
                                      @RequestParam(name = "category_ids", required = false) String categoryIds) {
        log.info("category_ids : {}", categoryIds);
        return response(HttpStatus.OK, "get items successfully.", itemService.getItems(page, size, orderBy, direction, categoryIds));

    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable("id") Integer id) {
        return response(HttpStatus.OK, "get item successfully.", itemService.getItem(id));
    }
}

