package solar
import java.io.IOException
import java.io.Reader

object SolarChunkIO {
  
  def loadPlanets(input: Reader) = {
    var hasSun: Boolean = false
    
    // Header consisting of "SOLAR" and the two digits of the simulation version
    var header = new Array[Char](7)
    // Chunk header cosisting of either "PLN", "NAM", "MSS", "RAD", "DIS", "VEL", or "END" and the length of the chunk in two digits.
    var chunkHeader = new Array[Char](5)
    
    
    try {
      Helpers.readFully(header, input)
      
      if (!header.mkString.startsWith("SOLAR")) {
        throw new CorruptedSolarFileException("Unknown file type")
      }
      
      do {
        Helpers.readFully(chunkHeader, input)
        val size = Helpers.extractChunkSize(chunkHeader)
        val data = new Array[Char](size)
        Helpers.readFully(data, input)
        
        // Random chunk debug print?
        println(chunkHeader.mkString + ": " + data.mkString + "\n")
        
        Helpers.extractChunkName(chunkHeader) match {
          case "PLN" => parsePlanet(data.mkString)
          case "END" =>
          case _ =>
        }
      } while (chunkHeader.mkString != "END00" || chunkHeader.mkString == "")
      
      if (!hasSun || System.objects.size < 9) throw new CorruptedSolarFileException("Invalid solar system")
      
      
      // Decrypts the planet info from the chunk, creates a new planet accordingly, and adds it to the system.
      def parsePlanet(data: String): Unit = {
        //Muuttujat: name, mass, radius, distance, velocity
        var parameter = data.take(3)
        data.drop(3)
        var length = data.take(2).toInt
        data.drop(2)
        
        parameter match {
          case "NAM" => {
            val name = data.take(length)
            data.drop(length)
          }
          case "MAS" => {
            val mass = data.take(length).toDouble
            data.drop(length)
          }
          case "RAD" => {
            val radius = data.take(length).toDouble
            data.drop(length)
          }
          case "DIS" => {
            val distance = data.take(length).toDouble
            data.drop(length)
          }
          case "VEL" => {
            val speed = data.take(length).toDouble
            data.drop(length)
          }
          case _ => throw new CorruptedSolarFileException("Corrupted planet data")
        }
        
        ///val planet = new Planet(name, mass, radius, ???, ???)
        
        //system.addObject(planet)
      
        ???
      }
        
      System
      
    } catch { // Basically flawed data 
        case e: IOException =>
          val solExc = new CorruptedSolarFileException("Reading the system data failed.")
          solExc.initCause(e)
          throw solExc
    }
  }
  
  
  /*
   * The helper methods have been mostly loaned from the Chess excercise's ChunkIO object.  
   */
  object Helpers {
    
    def extractChunkSize(chunkHeader: Array[Char]): Int = {
      // Subtracting the ascii value of the character 0 from
      // a character containing a number will return the
      // number itself.
      10 * (chunkHeader(3) - '0') + (chunkHeader(4) - '0')
    }
    
    def extractChunkName(chunkHeader: Array[Char]): String = {
      chunkHeader.take(3).mkString
      
    }
    
    def readFully(result: Array[Char], input: Reader) = {
      var cursor = 0
      while (cursor != result.length) {
        var numCharactersRead = input.read(result, cursor, result.length - cursor)
        // If the file end is reached before the buffer is filled
        // an exception is thrown.
        if (numCharactersRead == -1) {
          throw new CorruptedSolarFileException("Unexpected end of file")
        }
        cursor += numCharactersRead
      }
    }
    
    
  }
  
}