
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Pokemon{

        private String nome;
        private Image image;
        //String type; aplicacao futura!!

        public Pokemon(String nome, Image image){
            this.nome = nome;
            this.image = image;
        }

        
        public String getNome(){
            return nome;
        }

        public Image getImage(){
            return image;
        }

        public void setNome(String nome){
            this.nome = nome;
        }

        public void setImage(Image image){
                this.image = image;
        }


    
}