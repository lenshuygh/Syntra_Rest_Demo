package be.lens.syntra.rest.demo.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message implements Serializable {
    private int id;
    private String author;
    private String text;
}
