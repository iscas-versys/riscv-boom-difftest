package formal

import chisel3._
import chiseltest._
import chiseltest.formal._
import org.scalatest.flatspec.AnyFlatSpec

import boom.v3.exu.{BoomCore, BoomCoreConfig}
import top._

class BOOMFormalSpec extends AnyFlatSpec with Formal with ChiselScalatestTester {
  behavior of "BOOMFormal"
  it should "pass" in {
    // config
    val s = (FormalSettings()) ++ (InOrderSettings())
    s.foreach { Settings.settings += _ }
    Settings.settings.toList.sortBy(_._1)(Ordering.String).foreach {
      case (f, v: Long) =>
        println(f + " = 0x" + v.toHexString)
      case (f, v) =>
        println(f + " = " + v)
    }

    // verify
    verify(new BoomCore()(BoomCoreConfig()), Seq(BoundedCheck(12), BtormcEngineAnnotation))
  }
}