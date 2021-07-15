package api.message.command;

//import org.json.simple.JSONObject;
import api.message.command.BaseCommand;
import api.message.error.APICommandConstructionException;
import org.json.simple.JSONObject;

public class LoginCommand extends BaseCommand {

    public LoginCommand(JSONObject arguments) throws APICommandConstructionException {
        super(arguments);
    }

    @Override
    public String getCommandName() {
        return "login";
    }

    @Override
    public String[] getRequiredArguments() throws APICommandConstructionException {
        return new String[] {"userId", "password"};
    }
}
