package windows;

public class ViewController {
	private Window window;

public ViewController(Window window){
	this.window = window;
}

public void bouttonBarman(){
	ViewBarmanHome vbh = new ViewBarmanHome();
	 window.setContentPane(vbh);
	 window.repaint();
}

public void bouttonGestionnaire(){
	ViewBossHome vbh = new ViewBossHome();
	 window.setContentPane(vbh);
	 window.repaint();
}
}
