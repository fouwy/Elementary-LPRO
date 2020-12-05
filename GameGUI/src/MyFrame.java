import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {
	
	//Labels lhospital, lmorgue, lbaker, lcircus, lpalace, lpool, lprison, llabs, lmagnussen, lbackground;
	Panel /*hospitalp, morguep, bakerp, circusp, palacep, poolp, prisonp, labsp, magnussenp, */backgroundp;
	
	MyFrame(){
		
		//set position, sizes of panels, colors and Set of Starting Panel
		/*hospitalp = new Panels(90, 90, 190, 350, 200, 200, 200, 0);
		morguep = new Panels(280, 90, 150, 100, 63, 63, 63, 0);
		circusp = new Panels(500, 600, 200, 210, 213, 125, 49, 0);
		palacep = new Panels(500, 90, 500, 150, 165, 8, 8, 0);
		poolp = new Panels(1150, 90, 200, 250, 12, 136, 252, 0);
		prisonp = new Panels(90, 600, 250, 210, 150, 150, 150, 0);
		labsp = new Panels(870, 400, 200, 310, 133, 246, 151, 0);
		magnussenp = new Panels(1150, 600, 200, 210, 216, 245, 234, 0);*/
		backgroundp = new Panel(90, 90, 900, 720, 38, 102, 0);	//1260, 720
		//bakerp = new Panels(600, 300, 200, 200, 50, 0, 0, 1);
		
		//panels retornam valor que diz para dar set do panel com a character 
		//panel é repintado varias vezes 
		
		
		this.setTitle("ELEMENTARY");	//sets Title of frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
		this.setLayout(null); //set manually the components
		this.setSize(750,750);//sets the x-dimension and y-dimension of frame
		this.getContentPane().setBackground(new Color(0,19,51)); //change color background on a range to 0-255 RGB color
		
		//labels
		/*lhospital = new Labels(50, 50, 300, 150);
		lhospital.setText("Hospital");
		lmorgue = new Labels(0, 0, 300, 150);
		lmorgue.setText("Morgue");
		lbaker = new Labels(50, 50, 300, 150);
		lbaker.setText("221B Baker Street");
		lcircus = new Labels(50, 50, 300, 150);
		lcircus.setText("Circus");
		lpalace = new Labels(50, 50, 300, 150);
		lpalace.setText("Palace");
		lpool = new Labels(50, 50, 300, 150);
		lpool.setText("Pool");
		lprison = new Labels(50, 50, 300, 150);
		lprison.setText("Prison");
		llabs = new Labels(50, 50, 300, 150);
		llabs.setText("Labs");
		lmagnussen = new Labels(50, 50, 300, 150);
		lmagnussen.setText("Magnussen");
		
		//add labels to panels
		hospitalp.add(lhospital);
		morguep.add(lmorgue); 
		bakerp.add(lbaker);
		circusp.add(lcircus); 
		palacep.add(lpalace);
		poolp.add(lpool);
		prisonp.add(lprison);
		labsp.add(llabs);
		magnussenp.add(lmagnussen);
		
		
		//add panels to frame
		this.add(hospitalp); //first add all components 
		this.add(morguep);
		this.add(bakerp);
		this.add(palacep);
		this.add(poolp);
		this.add(prisonp);
		this.add(magnussenp);
		this.add(labsp);
		this.add(circusp);
		this.add(labsp);*/
		this.add(backgroundp);
		this.setVisible(true);
	
	}
	
	
}
