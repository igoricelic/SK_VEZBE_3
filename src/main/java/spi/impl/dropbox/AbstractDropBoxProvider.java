package spi.impl.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.util.Objects;

abstract class AbstractDropBoxProvider {

    /*
     * Pristupni KEY kojim se autorizujemo na DropBox nalog.
     * Kreiramo ga preko AppConsole:
     * https://www.dropbox.com/developers/apps
     * Najpre registrujemo aplikaciju, potom generisemo pristupni token.
     */
    private static final String ACCESS_TOKEN = "<ACCESS_TOKEN>";

    /*
     * Referenca na nalog
     */
    private DbxClientV2 client;

    protected DbxClientV2 getClient() {
        if(Objects.isNull(client)) connect();
        return client;
    }

    private void connect() {
        var config = DbxRequestConfig.newBuilder("SK_Vezbe").build();
        this.client = new DbxClientV2(config, ACCESS_TOKEN);
    }

}
