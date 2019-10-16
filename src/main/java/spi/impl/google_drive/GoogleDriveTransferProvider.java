package spi.impl.google_drive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import model.FileMetadata;
import spi.TransferProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoogleDriveTransferProvider extends AbstractGoogleDriveProvider implements TransferProvider {

    @Override
    public boolean upload(String localPath, String remotePath) {
        try {
            File file = new File();
            file.setName(remotePath);
            java.io.File localFile = new java.io.File(localPath);
            FileContent mediaContent = new FileContent("text/plain", localFile);
            getClient().files().create(file, mediaContent).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean download(String remotePath, String localPath) {
        try (OutputStream downloadFile = new FileOutputStream(localPath)) {
            getClient().files().get(remotePath).executeMediaAndDownloadTo(downloadFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FileMetadata> storageContent() {
        List<FileMetadata> results = new ArrayList<>();
        try {
            FileList result = getClient().files().list().execute();
            List<File> files = result.getFiles();
            if(!Objects.isNull(files)) {
                files.forEach(file -> results.add(new FileMetadata(file.getName(), file.getId())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
