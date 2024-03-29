package api.message.command;

import org.json.simple.JSONObject;

import api.message.command.BaseCommand;
import api.message.error.APICommandConstructionException;

public class ChartRangeCommand extends BaseCommand {

    public ChartRangeCommand(JSONObject arguments) throws APICommandConstructionException {
        super(arguments);
    }

    @Override
    public String getCommandName() {
        return "getChartRangeRequest";
    }

    @Override
    public String[] getRequiredArguments() throws APICommandConstructionException {
        return new String[]{"info"};
    }
}
