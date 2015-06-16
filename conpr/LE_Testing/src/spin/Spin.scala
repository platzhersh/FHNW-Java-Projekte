package spin

import scala.sys.process._
import java.io.File
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import java.nio.file.StandardOpenOption

class SpinAPI(executable: String) {
  val workingDir = new File("./promela")
  val modelFile = "model.pml"
  val trailFile = Paths.get("./promela/model.pml.trail")
  
  def generate(pml: String): Unit = {
    Files.write(Paths.get(s"./promela/$modelFile"), pml.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    Process(s"../spin/$executable -a $modelFile", workingDir).!!
  }
  
  def compile(): Unit = {
    Process("cc -o pan pan.c", workingDir).!!
  }
  
  def verify(): Unit  = {
    def noError(res: String): Boolean =
      res.contains("errors: 0")
    
     val result = Process("./pan -a -n", workingDir).!!
     if(noError(result) && Files.exists(trailFile)) {
       Files.delete(trailFile)
     }
     
     println("Verification result:\n" + result)
  }
  
  def trace(): Unit = {
    if(Files.exists(Paths.get("./promela/model.pml.trail"))) {
      val result = Process(s"../spin/$executable -t -p -g -l  $modelFile", workingDir).!!
      println("Error trace:\n" + result)
    } else {
      println("No trace file found")
    }
  }
}

object OSX extends SpinAPI("spin_osx")
object Win extends SpinAPI("spin643_windows64.exe") 

object Spin extends App {
  import Models._
 
  val spin = OSX // Win
  spin.generate(counter)
  spin.compile()
  spin.verify()
  spin.trace()
}