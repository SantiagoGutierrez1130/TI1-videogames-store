package ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Game;
import model.Store;

public class StoreUI {

	//*********** FirstPane ***********
	
	@FXML
    private TextField shelvesAmount;

    @FXML
    private TextField clientsQuantity;

    @FXML
    private TextField cashiersQuantity;

    @FXML
    private BorderPane mainPane;
    
	private Store gameStore;
	
	//************** GameCreationPane **********
	
	@FXML
    private BorderPane mainPaneGameCreation;

    @FXML
    private Label numberIndicator;

    @FXML
    private TextField gameCodeTxT;

    @FXML
    private TextField gamePriceTxT;

    @FXML
    private TextField gameQuantityTxT;
    
    @FXML
    private TableView<Game> AddGameTableView;

    @FXML
    private TableColumn<Game, String> codeColumn;

    @FXML
    private TableColumn<Game, Integer> priceColumn;

    @FXML
    private TableColumn<Game, Integer> quantityColumn;
    
    //**************** AddClient ********************
	
    @FXML
    private TextField clientIDTxT;

    @FXML
    private TableView<Game> ClientGamesTableView;

    @FXML
    private TableColumn<Game, String> clientGamesColumn;

    @FXML
    private ComboBox<String> clientGamesComboBox;

    
	//public StoreUI() {
		
	//}
	public StoreUI(Store gameStore){
		
		this.gameStore = gameStore;	
	}
	public void setStore(Store gameStore) {
	this.gameStore = gameStore;
	}
	public Store getGameStore() {
		return gameStore;
	}
	 @FXML
	 public void loadAddGame(ActionEvent event) throws IOException {

		 int cashiers = Integer.parseInt(cashiersQuantity.getText());
		 
		 int clients = Integer.parseInt(clientsQuantity.getText());
		 
		 int shelves = Integer.parseInt(shelvesAmount.getText());
		 
		 Store gameStoreNew = new Store(shelves, cashiers, clients);
		 
		 setStore(gameStoreNew);
		 
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addGame.fxml"));
		 fxmlLoader.setController(this);
		 Parent addGamePane = fxmlLoader.load();
		 mainPane.getChildren().setAll(addGamePane);
		 int indicator = gameStore.getCurrentShelves()+1;
		 numberIndicator.setText(""+indicator);
		 gameStore.setCurrentShelves(indicator);
		 
	    }
	 @FXML
	  public  void addGameToShelf(ActionEvent event) {
		 	
		 String code = gameCodeTxT.getText();
		 
		 int quantity = Integer.parseInt(gameQuantityTxT.getText());
		 
		 int price = Integer.parseInt(gamePriceTxT.getText());
		 
		 	if(gameCodeTxT.getText()!="" || gameQuantityTxT.getText()!=""||gamePriceTxT.getText()!=""         ) {
		 	
		 		gameStore.addGameToList(code, price, quantity);
		 		
		 		initializeAddGamesTableView();
		 		AddGameTableView.refresh();	
		 		
		 		}
		 	
	    }
	 public void initializeAddGamesTableView() {
		 
		 
		 	ObservableList<Game> observableList;
	    	observableList = FXCollections.observableArrayList(gameStore.getGames());
	    	AddGameTableView.setItems(observableList);
	    	
	    	codeColumn.setCellValueFactory(new PropertyValueFactory<Game,String>("code"));
	    	priceColumn.setCellValueFactory(new PropertyValueFactory<Game,Integer>("price"));
	    	quantityColumn.setCellValueFactory(new PropertyValueFactory<Game,Integer>("quantity"));
	 }
	  @FXML
	   public void nextStep(ActionEvent event) throws IOException {

		  		gameStore.createHashTable();

		  		if(gameStore.getCurrentShelves()<gameStore.getShelvesQuantity()) {
		  		
		  			gameStore.setCurrentShelves(gameStore.getCurrentShelves()+1);
		  			gameCodeTxT.setText("");
		  			gamePriceTxT.setText("");
		  			gameQuantityTxT.setText("");
		  			
		  			numberIndicator.setText(""+gameStore.getCurrentShelves());
		  			
		  			AddGameTableView.refresh();
		  		
		  		}else {
		  			
		  			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addClient.fxml"));
		  			fxmlLoader.setController(this);
		  	    	Parent menuPane = fxmlLoader.load();
		  	    	mainPane.getChildren().setAll(menuPane);
		  			gameStore.setGamesEmpty();
		  			initializeClientsTableview();
		  			initializeGamesCombobox();
		  			gameStore.setClientsCounted(gameStore.getClientsAmount()+1);
		  		}	
	    }
	  @FXML
	   public void addGameToClient(ActionEvent event) {

		String game = clientGamesComboBox.getAccessibleText();
		  
			gameStore.addGame(game);
			initializeClientsTableview();
			ClientGamesTableView.refresh();
		
		  	
	    }
	  @FXML
	  public void thirdStep(ActionEvent event) throws IOException {

		  String clientCode = clientIDTxT.getText();
		  gameStore.addClienteToQueue(clientCode);
		  
		  if(gameStore.getClientsCounted()<gameStore.getClientsAmount()) {
			  
			  clientIDTxT.setText("");
			  clientGamesComboBox.setAccessibleText("");
			  ClientGamesTableView.refresh();
			  gameStore.setClientsAmount(gameStore.getClientsAmount()+1);
			  
		  }else {
			 
			  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameOrderPane.fxml"));
	  			fxmlLoader.setController(this);
	  	    	Parent menuPane = fxmlLoader.load();
	  	    	mainPane.getChildren().setAll(menuPane);
	  			gameStore.setGamesEmpty(); 
			  
		  }
		  
	    }
	  
	  
	  
	  public void initializeGamesCombobox() {
		  
		  for(int i=0; i < gameStore.getAllGames().size();i++) {
			   String name = gameStore.getAllGames().get(i).getCode();
			   clientGamesComboBox.getItems().add(name);
		   }
	  }
	  public void initializeClientsTableview() {
		  
		  ObservableList<Game> observableList;
		  observableList = FXCollections.observableArrayList(gameStore.getGames());
		  ClientGamesTableView.setItems(observableList);
	  
		  clientGamesColumn.setCellValueFactory(new PropertyValueFactory<Game,String>("code"));
	 }
}