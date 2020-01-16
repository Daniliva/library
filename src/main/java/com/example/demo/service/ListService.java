package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class ListService {
  public static ResponseEntity<?>convertToResponseEntity(List<Object[]>list, int limit)
  {
      if (limit > 0) {
          if (limit < list.size()) {
              return ResponseEntity.ok().body(list.subList(0, limit));
          } else {
              return ResponseEntity.ok().body(list);
          }
      } else {
          return ResponseEntity.ok().body("limit must be greater than zero ");
      }
  }
}
