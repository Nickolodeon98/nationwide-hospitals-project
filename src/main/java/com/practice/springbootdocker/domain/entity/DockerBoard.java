package com.practice.springbootdocker.domain.entity;

import com.practice.springbootdocker.domain.dto.DockerBoardAddResponse;
import com.practice.springbootdocker.domain.dto.DockerBoardResponse;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Table(name="docker_board")
public class DockerBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;
    private String author;

    public DockerBoard(Long id, String title, String contents, String author) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public DockerBoard() {
    }

    @OneToMany(mappedBy = "postToComment")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public static DockerBoardResponse of(DockerBoard dockerBoard) {
        return new DockerBoardResponse(dockerBoard.getId(), dockerBoard.getTitle(), dockerBoard.getContents(), dockerBoard.getAuthor());
    }

    public static DockerBoardAddResponse addResponseOf(DockerBoard dockerBoard) {
        return DockerBoardAddResponse.builder()
                .id(dockerBoard.getId())
                .title(dockerBoard.getTitle())
                .contents(dockerBoard.getContents())
                .author(dockerBoard.getAuthor())
                .build();
    }
}
