package com.eh.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Event {

    private Integer eventId;
    private Book book;

}
