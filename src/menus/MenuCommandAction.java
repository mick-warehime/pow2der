package menus;

import org.newdawn.slick.command.Command;

import commands.InputListenerAggregator;

public class MenuCommandAction implements MenuSelectionAction {

	
	private InputListenerAggregator aggregator;
	private Command command;

	public MenuCommandAction(InputListenerAggregator handlerListenerAggregator,Command command){
		this.aggregator = handlerListenerAggregator;
		this.command = command;
	}
	
	@Override
	public void Activate() {
		aggregator.receiveSingleCommand(command);
	}

}
