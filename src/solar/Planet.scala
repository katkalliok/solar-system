package solar

import java.awt.{Graphics, Color, Graphics2D}
import java.util.Random;
import scala.collection.mutable.Buffer

class Planet(val name: String, mass: Double, val radius: Double, inLocation: Vector3D, inSpeed: Vector3D) {

  // Earth's mass in kg. Used as a multiplier for the scaled masses of other planets
  val earthMass = 5.9737 * math.pow(10, 24)
  // Earth's average distance from the Sun. Used as a multiplier like the mass. 
  val earthDist = 1.4960 * math.pow(10, 8)
  //Earth's average orbital speed in m/s.
  val earthSpeed = 29800
  
  val G =  6.67384 * math.pow(10, -11)
  def getMass = mass * earthMass
  var coords = inLocation * 1000
  var speed = inSpeed * earthSpeed
  
  
  val neighbors = scala.collection.mutable.Buffer[Planet]()
  
  def addNeighbor(n: Planet) =  neighbors += n
  
  //Calculates the next coordinates, which depend on the planet and its neighbors' current attributes.
  def updatePlanet(neighbors: Buffer[Planet], step: Double): Unit = {
    val acc = this.acceleration(neighbors)
    coords = coords+(this.speed*step)+acc*(math.pow(step, 2))*0.5
    speed = speed+(acc*(step))
  }
  
  def acceleration(neighbours: Buffer[Planet]): Vector3D = {
    neighbors.map(p => this.acceleration(p)).fold(new Vector3D(0.0, 0.0, 0.0))((z, a) => z+a)
  }
  
  def acceleration(planet: Planet): Vector3D = {
    //a = F/m
    val force = this.force(planet)
    new Vector3D(force.x / this.getMass, force.y / this.getMass, force.z / this.getMass)
  }
  
  def force(planet: Planet): Vector3D = {
    //F=Gm1m2/r^2
    val M = this.getMass
    val m = planet.getMass
    
    val xDiff = planet.coords.x - this.coords.x
    var xForce = 0.0
    if (xDiff != 0) xForce = G*M*m / math.pow(xDiff, 2)
    
    val yDiff = planet.coords.y - this.coords.y
    var yForce = 0.0
    if (yDiff != 0) yForce = G*M*m / math.pow(yDiff, 2)
    
//    val zDiff = planet.coords.z - this.coords.z
//    val zForce = G*M*m / math.pow(zDiff, 2)
    
    new Vector3D(xForce, yForce, 0)
  }
  
  val random = new Random();
  val randColor = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat())
  
  def draw(g: Graphics2D) = {
    var color: Color = null 
    name match {
      case "Sun" => color = Color.yellow
      case "Mercury" => color = Color.lightGray
      case "Venus" => color = Color.white
      case "Earth" => color = Color.green
      case "Mars" => color = Color.red
      case "Jupiter" => color = Color.orange
      case "Saturn" => color = Color.yellow
      case "Uranus" => color = Color.cyan
      case "Neptune" => color = Color.blue
      case _ => color = randColor
    }
    g.setColor(color)
    g.fillOval((coords.x - radius).toInt, (coords.y - radius).toInt, (2*radius).toInt, (2*radius).toInt)
  }
}