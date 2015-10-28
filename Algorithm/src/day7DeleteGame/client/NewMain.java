package day7DeleteGame.client;

import day7DeleteGame.client.ui.NewClientView;

public class NewMain {
	public static void main(String[] args) {
		NewClientView view = new NewClientView(new GameClientMessageHandler());
		view.init();
	}
}
