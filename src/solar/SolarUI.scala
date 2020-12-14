package solar
import scala.swing._
import java.awt.{Graphics, Color, Graphics2D, Event}
import javax.swing._
import java.awt.event.ActionListener


object SolarUI extends SimpleSwingApplication {
  
  val solarFrame = new MainFrame {
    title     = "Solar System"
    resizable = true
    //contents = new TextField
    size      = new Dimension(800, 800)
    preferredSize = new Dimension(800, 800)
    background = Color.BLACK
    
    val solarPanel = new Panel {
      override def paintComponent(g: Graphics2D) = {
        background = Color.BLACK
        System.draw(g)
      }
    }
    
    contents = solarPanel
    
//    val listener = new ActionListener() {
//      def actionPerformed(e: java.awt.event.ActionEvent) = {
//        System.step
//        solarPanel.repaint()
//      }
//    }
//        
//    val timer = new Timer(6, listener)
//    timer.start
//    
    
  }
  
 def top = solarFrame
}