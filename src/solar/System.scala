package solar
import scala.collection.mutable.Buffer
import scala.swing._
import java.awt.{Graphics, Color, Graphics2D}
import java.awt.event._
import javax.swing._

object System {
  //30d is seconds
  var timeStep = 2592000.0
  // Gravitational constant
  val G =  6.67384 * math.pow(10, -11)
  
  
  // All of the planets and satellites shall be stored here
  var objects = Buffer[Planet]()
  
  def addObject(p: Planet) = {
    objects.foreach(_.neighbors += p)
    objects += p
  }
  
  def addObjects(planets: Buffer[Planet]) = planets.foreach(p => this.addObject(p))
  
  //radius in m.
  val sun = new Planet("Sun", 333400, 696000*1000, new Vector3D(0, 0, 0), new Vector3D(0, 0, 0))
  //distance 0,57 909 227 *10^8 km
  val mercury = new Planet("Mercury", 0.055, 2439.7*1000, new Vector3D(0.5791*math.pow(10,8), 0, 0), new Vector3D(0, 1.607, 0))
  //1,08 208 926 * 10^8 km
  val venus = new Planet("Venus", 0.815, 6051.8*1000, new Vector3D(1.0821*math.pow(10,8), 0, 0), new Vector3D(0, 1.174, 0))
  
  //1,4960×108   km
  val earth = new Planet("Earth", 1.00, 6378.14*1000, new Vector3D(1.496*math.pow(10,8), 0, 0), new Vector3D(0, 1.000, 0))
  //2,2792 * 10^8 km
  val mars = new Planet("Mars", 0.108, 3396.2*1000, new Vector3D(2.2792*math.pow(10,8), 0, 0), new Vector3D(0, 0.802, 0))
  //7,78412010 *10^8 km
  val jupiter = new Planet("Jupiter", 317.8, 71450*1000, new Vector3D(7.7841*math.pow(10,8), 0, 0), new Vector3D(0, 0.434, 0))
  
  
  //14,267254 *10^8 km
  val saturn = new Planet("Saturn", 95.162, 60268*1000, new Vector3D(14.26725*math.pow(10,8), 0, 0), new Vector3D(0, 0.323, 0))
  //28,709722 *10^8 km
  val uranus = new Planet("Uranus", 14.536, 25559*1000, new Vector3D(28.7097*math.pow(10,8), 0, 0), new Vector3D(0, 0.228, 0))
  //44,982529 *10^8 km
  val neptune = new Planet("Neptune", 17.147, 24786*1000, new Vector3D(44.9825*math.pow(10,8), 0, 0), new Vector3D(0, 0.182, 0))
  
  val planets = Buffer(sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune)
  this.addObjects(planets)
  
  
  def hasCrashed(thing: Planet): Boolean = !objects.forall(p => thing.coords.x != p.coords.x && thing.coords.y != p.coords.y && thing.coords.z != p.coords.z)
  
  def step = {
    for (p <- objects) {
      p.updatePlanet(objects, timeStep)
    }
  }
  
//  val listener = new ActionListener {
//      def actionPerformed(event: ActionEvent) {
//        for (p <- objects) {
//          p.updatePlanet(objects, timeStep)
//        }
//        //this.actionPerformed.repaint
//      }
//  }
  
  def draw(g: Graphics2D) = {
    objects foreach (_.draw(g))
  }
  
}