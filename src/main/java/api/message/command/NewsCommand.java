package api.message.command;

import org.json.simple.JSONObject;

import api.message.command.BaseCommand;
import api.message.error.APICommandConstructionException;

public class NewsCommand extends BaseCommand {

    public NewsCommand(JSONObject arguments) throws APICommandConstructionException {
        super(arguments);
    }

    @Override
    public String getCommandName() {
        return "getNews";
    }

    @Override
    public String[] getRequiredArguments() throws APICommandConstructionException {
        return new String[]{"start", "end"};
    }
}
