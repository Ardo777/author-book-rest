package com.example.authorbookrest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponseDto {

   private Object data;
   private int size;
   private int page;
   private long totalElements;

}
