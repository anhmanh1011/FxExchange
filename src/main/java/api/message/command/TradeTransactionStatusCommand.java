package api.message.command;

import org.json.simple.JSONObject;

import api.message.command.BaseCommand;
import api.message.error.APICommandConstructionException;

public class TradeTransactionStatusCommand extends BaseCommand {

    public TradeTransactionStatusCommand(JSONObject arguments) throws APICommandConstructionException {
        super(arguments);
    }

    @Override
    public String getCommandName() {
        return "tradeTransactionStatus";
    }

    @Override
    public String[] getRequiredArguments() throws APICommandConstructionException {
        return new String[]{"order"};
    }
}
