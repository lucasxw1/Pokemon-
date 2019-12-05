
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.util.Duration; 
import java.util.*;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class App extends Application {
  
  
    RedBlackBST<Integer, Pokemon> bst = new RedBlackBST<Integer, Pokemon>();
    
    
    public void carrega(){
    Path path1 = Paths.get("names.txt");
    String where = path1.toString();
    try (BufferedReader br = new BufferedReader(new FileReader(where))) {
      String linha = br.readLine();
      linha = br.readLine();
      
      String nome;
      
      int ajuda = 0;
      while (linha != null) {
        ajuda++;
        nome = linha;
        bst.add(ajuda, new Pokemon(nome, new Image("file:DataBase\\" + ajuda + ".png")));
        linha = br.readLine();
        
      }
    } catch (IOException e) {
      System.out.println(e.getClass());
    }
  }

    public static App app = null;

    public static App getInstance(){
      return app;
    }

      public App(){
      app = this;
      }

    
      public static void main(String[] args) {
      launch(args);
    }



    

  
  
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    carrega();


    GridPane tab = new GridPane();
    primaryStage.setTitle("Pokedex");
  
    tab.setAlignment(Pos.CENTER);
    tab.setHgap(10);
    tab.setVgap(10);
    tab.setPadding(new Insets(10, 10, 10, 10));

    
  
    //primeira pag;
    Button pesquisar = new Button("Pesquisar");
    pesquisar.setMaxSize(100,100);
    TextField textPesquisa = new TextField();
    textPesquisa.setMaxSize(200, 200);

    VBox um = new VBox(10);
    um.setAlignment(Pos.CENTER);
    um.setPadding(new Insets(25, 25, 25, 25));
    um.getChildren().add(textPesquisa);
    um.getChildren().add(new Text("Digite o numero de um pokemon"));
    um.getChildren().add(pesquisar);
    um.getChildren().add(tab);
// fim primeira pag;

    Scene scene1 = new Scene(um);
    pesquisar.setOnAction(
      e -> {
         //segunda pag;
         ImageView imageView2 = new ImageView(bst.get(Integer.parseInt(textPesquisa.getText())).getImage());
         Button back = new Button("Voltar");
         back.setMaxSize(125,125);




      VBox dois = new VBox(imageView2);
      dois.setAlignment(Pos.CENTER);
      dois.setPadding(new Insets(25, 25, 25, 25));
      dois.setSpacing(50);
      Text pokemonName= new Text(bst.get(Integer.parseInt(textPesquisa.getText())).getNome());
      pokemonName.getStyleClass().add("my-text");
      dois.getChildren().add(pokemonName);
      dois.getChildren().add(back);
      dois.getChildren().add(tab);
    
      
      
      ScaleTransition scaleTransition = new ScaleTransition(); 
      scaleTransition.setDuration(Duration.millis(1500)); 
      scaleTransition.setNode(imageView2); 
      scaleTransition.setByY(1.0); 
      scaleTransition.setByX(1.0); 
      scaleTransition.play(); 

      TranslateTransition translateTransition = new TranslateTransition(); 
      
      translateTransition.setDuration(Duration.millis(1500)); 
      translateTransition.setNode(pokemonName); 
      translateTransition.setByX(500); 
      translateTransition.setCycleCount(1); 
      translateTransition.setAutoReverse(false); 
      translateTransition.play(); 

      back.setOnAction(
        f -> {
          primaryStage.setMaximized(false);
          primaryStage.setScene(scene1);
          primaryStage.setMaximized(true);

        });



      Scene scene2 = new Scene(dois);
      scene2.getStylesheets().add("styles1.css");
      primaryStage.setMaximized(false);
      primaryStage.setScene(scene2);
      primaryStage.setMaximized(true);



      
      }
  );
    
  

    
    scene1.getStylesheets().add("styles.css");
    primaryStage.setScene(scene1);
    primaryStage.show();
    primaryStage.setMaximized(true);

  }

  
}