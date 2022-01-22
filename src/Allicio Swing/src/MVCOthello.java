/**
 * @author Allicio Mikael
 */
public class MVCOthello {

    public static void main(String[] args) {
       
        OthelloView theView = new OthelloView();
        OthelloModel theModel = new OthelloModel();
        
        OthelloController controller = new OthelloController(theModel, theView);
    }
    
}
