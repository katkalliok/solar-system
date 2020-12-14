package solar

//The initial parameters are individual values. A list of coordinates is also an option.
class Vector3D(val x: Double, val y: Double, val z: Double) {
  
  def +(vector: Vector3D): Vector3D = {
    new Vector3D(this.x + vector.x, this.y + vector.y, this.z + vector.z)
  }
  
  def +(scalar: Double): Vector3D = {
    new Vector3D(this.x + scalar, this.y + scalar, this.z + scalar)
  }
  
  def *(scalar: Double): Vector3D = {
     new Vector3D(this.x*scalar, this.y*scalar, this.z*scalar)
  }
  
  def unit: Vector3D = {
    val length = math.sqrt(x*x + y*y + z*z)
    this*(1/length)
  }
  
  //Kilometers or km/s
  override def toString() = {
    "(" + x + "; " + y + " ; " + z + ")"
  }
}