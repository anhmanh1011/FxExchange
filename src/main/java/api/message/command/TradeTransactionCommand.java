package api.message.command;

import org.json.simple.JSONObject;

import api.message.command.BaseCommand;
import api.message.error.APICommandConstructionException;

public class TradeTransactionCommand extends BaseCommand {

    public TradeTransactionCommand(JSONObject arguments) throws APICommandConstructionException {
        super(arguments);
    }

    @Override
    public String getCommandName() {
        return "tradeTransaction";
    }

    @Override
    public String[] getRequiredArguments() throws APICommandConstructionException {
        return new String[]{"tradeTransInfo"};
    }
}
