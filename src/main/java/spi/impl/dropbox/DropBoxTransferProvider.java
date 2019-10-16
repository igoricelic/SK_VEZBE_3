package spi.impl.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import model.FileMetadata;
import spi.TransferProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * SDK setup tutorial: https://github.com/dropbox/dropbox-sdk-java
 */
public class DropBoxTransferProvider extends AbstractDropBoxProvider implements TransferProvider {

    private static final String ROOT_DIRECTORY_PATH = "";

    @Override
    public boolean upload(String localPath, String remotePath) {
        try (InputStream in = new FileInputStream(localPath)) {
            getClient().files().uploadBuilder(remotePath).uploadAndFinish(in);
            return true;
        } catch (DbxException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean download(String remotePath, String localPath) {
        try (OutputStream downloadFile = new FileOutputStream(localPath)) {
            getClient().files().downloadBuilder(remotePath).download(downloadFile);
            return true;
        } catch (DbxException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FileMetadata> storageContent() {
        try {
            List<FileMetadata> results = new ArrayList<>();
            // ucitavamo rezultate iz root direktorijuma
            ListFolderResult storageContentList = getClient().files().listFolder(ROOT_DIRECTORY_PATH);
            while (true) {
                // setamo se kroz root i citamo sadrzaj
                for (Metadata metadata : storageContentList.getEntries()) {
                    results.add(new FileMetadata(metadata.getName(), metadata.getPathLower()));
                }

                if (!storageContentList.getHasMore()) {
                    break;
                }

                storageContentList = getClient().files().listFolderContinue(storageContentList.getCursor());
            }
            return results;
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
