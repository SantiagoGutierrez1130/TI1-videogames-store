package model;

import java.util.Hashtable;

import collections.Queue;

public class Store {

	private int shelvesQuantity;
	
	private Hashtable[] shelves;
	
	private Queue<Client> clientsQueue;
	
	public Store(int shelvesToCreate) {
	
		shelvesQuantity = shelvesToCreate;
		
		shelves = createHashTables(shelvesToCreate);
		
		clientsQueue = new Queue<Client>();
	}
	
	public void createShelves(String games, int position) {
		
		String[] gamesSplited = games.split(" ");
		
		for(int i=0; i < gamesSplited.length;i++) {
		
		//String [] parts = gamesSplited[0].split(" ");
			
		Game game = new Game(gamesSplited[0],Integer.parseInt(gamesSplited[1]),Integer.parseInt(gamesSplited[2]));
			
		shelves[position].put(gamesSplited[0], game);
		
		}
	}
	
	public Hashtable[] createHashTables(int toCreate) {
		
		Hashtable[] tables = new Hashtable[toCreate];
		
		for(int i=0; i < toCreate;i++) {
			
			Hashtable tmp = new Hashtable<>();
			tables[i] = tmp;
		}
		return tables;
	}
	public void addClienteToQueue(String id,String [] games) {
		
	}
}
