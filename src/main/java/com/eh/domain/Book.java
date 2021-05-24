package com.eh.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Book {

    private Integer bookId;
    private String name;
    private String author;

}
