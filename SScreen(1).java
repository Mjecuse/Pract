

import java.util.LinkedList;
import java.util.Queue;
public class SScreen{
Queue<String> gamers= new LinkedList<>();
	//This will enable the players to log in using their Character.
	public void addPlayer(String name) { 
		gamers.add(name);
		System.out.println(name +"Has joined");
	}
	public void whoHere() {
		System.out.println("Players waiting Currently: ");
		//For loop to show who is here
		for (String gamer : gamers) {
			System.out.println(gamer);}
		}
		public void gameStart() {
			if(!gamers.isEmpty()) {
				String nPlayer = gamers.poll();
				System.out.println(nPlayer + " Has joined the game!");
			}else {
				System.out.println("No players are waiting to play.");
				
			
		}
	}
}