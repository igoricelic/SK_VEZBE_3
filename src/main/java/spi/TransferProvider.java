package spi;

import model.FileMetadata;

import java.util.List;

public interface TransferProvider {

    boolean upload (String localPath, String remotePath);

    boolean download (String remotePath, String localPath);

    List<FileMetadata> storageContent ();

}
