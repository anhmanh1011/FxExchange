package api.message.command;

import org.json.simple.JSONObject;

import api.message.command.BaseCommand;
import api.message.error.APICommandConstructionException;

public class ProfitCalculationCommand extends BaseCommand {

    public ProfitCalculationCommand(JSONObject arguments) throws APICommandConstructionException {
        super(arguments);
    }

    @Override
    public String getCommandName() {
        return "getProfitCalculation";
    }

    @Override
    public String[] getRequiredArguments() throws APICommandConstructionException {
        return new String[]{"cmd", "symbol", "volume", "openPrice", "closePrice"}; 
    }
}
