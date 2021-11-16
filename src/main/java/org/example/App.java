package org.example;
/**
 * Hello world!
 *
 */
import it.tdlight.client.APIToken;
import it.tdlight.client.AuthenticationData;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.client.TDLibSettings;
import it.tdlight.common.Init;
import it.tdlight.common.utils.CantLoadLibrary;
import it.tdlight.jni.TdApi;


import java.io.IOException;
import java.nio.file.Paths;

public class App 
{
    public static void main( String[] args ) throws CantLoadLibrary, InterruptedException, IOException, ClassNotFoundException {
        Init.start();
        AppFactory.createProcessor();

        var apiToken = AppFactory.loadAPIToken("apiToken.txt");
        var settings = TDLibSettings.create(apiToken);


        var sessionPath = Paths.get("session-test");
        settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));
        settings.setDownloadedFilesDirectoryPath(sessionPath.resolve("downloads"));

        var client = new SimpleTelegramClient(settings);
        var authenticationData = AuthenticationData.consoleLogin();


        client.addUpdateHandler(TdApi.UpdateNewMessage.class, App::onUpdateNewMessage);
        client.start(authenticationData);

        client.send(new TdApi.GetChat(234), chatIdResult -> {
            System.out.println(chatIdResult);
        });
        client.waitForExit();
    }

    private static void onError(Throwable throwable) {
    }

    private static void onUpdateError(Throwable throwable) {
    }

    private static void onUpdateNewMessage(TdApi.Object object) {
        TdApi.UpdateNewMessage update = (TdApi.UpdateNewMessage) object;
        AppFactory.process(update.message);
    }
}
