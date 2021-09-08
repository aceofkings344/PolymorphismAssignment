import java.util.Date;
import java.util.TimeZone;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ClientLoan extends Application{
	ServerLoan serverLoan;
	
	int y;
	double r;
	double a;
	double mp = 0;
	double tp = 0;
	String result;
	String d1;
	
	Button b = new Button("Submit");
	
	public void start(Stage primaryStage) throws Exception{
		TimeZone.setDefault(TimeZone.getTimeZone("EST"));
		TimeZone.getDefault();
		d1 = "Server Started at " + new Date();
		
		Label l1 = new Label("Annual Interest Rate");
		Label l2 = new Label("Number Of Years:");
		Label l3 = new Label("Loan Amount");
		TextField t1 = new TextField();
		TextField t2 = new TextField();
		TextField t3 = new TextField();
		
		TextArea ta = new TextArea();
		
		GridPane root = new GridPane();
		root.addRow(0, l1, t1);
		root.addRow(1, l2, t2, b);
		root.addRow(5, l3, t3);
		root.addRow(6, ta);
		
		VBox vb = new VBox(root, ta);
		
		Scene scene = new Scene(vb, 400, 250);
		
		b.setOnAction(value -> {
			r = Double.parseDouble(t1.getText());
			y = Integer.parseInt(t2.getText());
			a = Double.parseDouble(t3.getText());
			
			Loan obj = new Loan(r, y, a);
			
			mp = obj.getMonthlyPayment();
			tp = obj.getTotalPayment();
			
			result = "Annual Interest Rate: " + r + "\n" + "Number Of Years: " + y + "\n" + "Loan Amount: " + a + "\n" + "Monthly Payment: " + mp + "\n" + "Total Payment: " + tp;
			
			ta.setText(result);
			
			serverLoan = new ServerLoan(this);
		});
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("ClientLoan");
		primaryStage.show();
	}
	
	public static void main (String args[]) {
		launch(args);
	}
	
	class ServerLoan extends Stage {
		ClientLoan parent;
		Stage subStage;
		TextArea ta = new TextArea();
		
		private ServerLoan (ClientLoan aThis) {
			TimeZone.setDefault(TimeZone.getTimeZone("EST"));
			TimeZone.getDefault();
			
			String d2 = "Connected to client at " + new Date();
			
			parent = aThis;
			
			ta.setText(d1);
			ta.appendText("\n" + d2);
			ta.appendText("\n" + result);
			
			GridPane root = new GridPane();
			
			root.addRow(0, ta);
			
			subStage = new Stage();
			
			Scene scene = new Scene(root, 400, 200);
			
			subStage.setScene(scene);
			subStage.setTitle("ServerLoan");
			subStage.show();
		}
	}

}