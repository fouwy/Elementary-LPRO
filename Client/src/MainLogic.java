import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLogic implements ActionListener {

    private final MainPage main_page;

    public MainLogic(MainPage main_page) {
        this.main_page = main_page;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(main_page.getLeaveButton())){
            enterLoginPage();
        }
    }

    private void enterLoginPage(){
        new LoginPage();
        main_page.disposeMain();
    }
}
