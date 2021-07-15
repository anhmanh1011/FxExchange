package api.sync;

public class RunExample {

    public static void main(String[] args) throws Exception {
        if (args.length != 2 && args.length != 4)
            throw new IllegalArgumentException("required login password [app_id app_name]");
        String login = args[0];
        String password = args[1];
        String appId = args.length == 4 ? args[2] : null;
        String appName = args.length == 4 ? args[3] : null;
        Example ex = new Example();
        Credentials credentials = new Credentials(login, password, appId, appName);
        ex.runExample(ServerData.ServerEnum.DEMO, credentials);
    }
}
