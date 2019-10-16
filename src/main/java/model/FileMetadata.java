package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data @AllArgsConstructor @ToString
public class FileMetadata {

    private String name;

    private String path;

}
