package solar

object TextUI extends App {
  
  
   //Do the whole recalculation with the time step thing
//  val updateListener = new java.awt.event.ActionListener {
//      def actionPerformed(e : java.awt.event.ActionEvent) = {
//        System.step
//      }
//   }


  //val updateTimer = new javax.swing.Timer(6, updateListener)
  
  
  //val uiTimer = new javax.swing.Timer(5000, printListener)
  
  println("Welcome to Solar System Simulator.")
  println("The simulator presents our solar system from \"above\" and tracks the planets and your satellite's movements")
  println("Please enter a name for your satellite:")
  val name = scala.io.StdIn.readLine()
  println("Your satellite is called " + name)
  println("Now enter its initial speed (meters per second) in direction x:")
  var xSpeed = scala.io.StdIn.readLine()
  while (xSpeed.isEmpty()) {
    println("Invalid input. Please enter an integer or a decimal number:")
    xSpeed = scala.io.StdIn.readLine()
  }
  var xStr = xSpeed.replace(".", "")
  while (!xStr.forall(x => x.isDigit)) {
    println("Invalid input. Please enter an integer or a decimal number:")
    xSpeed = scala.io.StdIn.readLine()
    xStr = xSpeed.replace(".", "")
  }
  
  println(name + " travels " + xSpeed + " meters per second in the direction x. How about the direction y?")
  var ySpeed = scala.io.StdIn.readLine()
  while (ySpeed.isEmpty()) {
    println("Invalid input. Please enter an integer or a decimal number:")
    ySpeed = scala.io.StdIn.readLine()
  }
  var yStr = ySpeed.replace(".", "")
  while (!yStr.forall(y => y.isDigit)) {
    println("Invalid input. Please enter an integer or a decimal number:")
    ySpeed = scala.io.StdIn.readLine()
    yStr = ySpeed.replace(".", "")
  }
  //The speed will be multiplied by Earth's orbital speed 29800 m/s
  val speed = new Vector3D(xSpeed.toDouble/29800, ySpeed.toDouble/29800, 0)
  
  val inLocation = {
    val unitV = speed.unit
    System.earth.coords + unitV * System.earth.radius
  }
  
  //The inspected satellite 
  //1500 kg, 2 meters' radius
  val satellite = new Planet(name, 1500, 2, inLocation, speed)
  System.addObject(satellite)

  println(name + " travels " + ySpeed + " meters per second in the direction y.")
  
  println("How frequent location updates do you want for " + name + " and the planets?")
  println("Please enter your time step for checking in on the planets as a number of days (24 h).")
  var step = scala.io.StdIn.readLine()
  while (step.isEmpty()) {
    println("Invalid input. Please enter an integer or a decimal number:")
    step = scala.io.StdIn.readLine()
  }
  var stepStr = step.replace(".", "")
  while (!stepStr.forall(x => x.isDigit)) {
    println("Invalid input. Please enter an integer or a decimal number:")
    step = scala.io.StdIn.readLine()
    stepStr = step.replace(".", "")
  }
  
  println("For every update, " + step + " days will have passed in the simulation of the Solar System.")
  //In seconds
  System.timeStep = step.toDouble*24*60*60
  
  println("The simulator will now start presenting the status of each planet and " + name + ".")
  println("For the new data appearing every ten seconds, " + step + " days of the system will have surpassed since the previous update.")
  //updateTimer.start
  //uiTimer.start
  
  //THE PAUSE STUFF DOES NOT WORK WHEN PRINTING
  //println("Whenever you wish to pause the simulation, enter \"p\".")
  //println("If you wish to change the time step, enter \"t\".")
  
  var days = 0.0
  
  //Prints out the status for each planet. Location vectors are in km, speed vectors in m/s.
  while(true) {//(!System.hasCrashed(satellite)) {
    Thread.sleep(5000)
    println("*********\n")
    println("After " + days + " days")
    System.objects.foreach {
        p => {
          println()
          println(p.name.capitalize + " status: \nLocation: " + (p.coords*(1.0/1000)) +"\nSpeed: " + p.speed)
          println("*********\n")
        }
      }
    days += step.toDouble
    System.step
    Thread.sleep(5000)
  }
  
  var input = scala.io.StdIn.readLine()
  
  
  input match {
    case "p" => { //Pause
      this.pause
      println("The simulation is paused.")
      println("If you want to continue, enter \"c\".")
      println("If you want to quit, enter \"q\".")
      var ip = scala.io.StdIn.readLine()
      do {
        ip match {
          case "c" => this.unPause //Continue
          case "q" => {
            ??? //Rerun
          }
          case _ => println("Invalid input. Please enter either \"c\" to continue the simulation or \"q\" to stop and create a new satellite.")
        }
      } while (ip != "c" && ip != "q")
    }
    case "t" => {
      //pause first
      this.pause
      //then ask
      println("Enter the new time step in days:")
      step = scala.io.StdIn.readLine
      println("The new time step is " + step + " days.")
      System.timeStep = step.toDouble*24*60*60
      this.unPause
    }
    case _ => println("Invalid input. Enter \"p\" to pause the simulation or \"t\" to change the time step.")
  }
  
 
  var stopped = false
  
  def pause = {
    stopped = true
    //updateTimer.stop()
    //uiTimer.stop()
  }
  
  def unPause = {
    stopped = false
    //updateTimer.start()
    //uiTimer.start()
  }
  
  
  
//  val crashListener = new java.awt.event.ActionListener {
//    def actionPerformed(e: java.awt.event.ActionEvent) = {
//      if (System.hasCrashed(satellite)) TextUI.pause
//    }
//  }
  
  
}