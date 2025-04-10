// import Mill dependency
import mill._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib.TestModule.ScalaTest
import $ivy.`com.lihaoyi::mill-contrib-bloop:`

import scalalib._
// support BSP
import mill.bsp._

object boom extends ScalaModule { m =>
  override def millSourcePath = os.pwd
  override def scalaVersion = "2.13.14"
  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    "-P:chiselplugin:genBundleElements"
  )
  val chiselVersion = "6.5.0"
  val rocketVersion = "1.6-snapshot"
  override def moduleDeps = super.moduleDeps ++ Seq(rocketModule) ++ Seq(difftest) ++ Seq(riscvspeccore)

  override def ivyDeps = Agg(
    ivy"org.chipsalliance::chisel:$chiselVersion",
    ivy"org.chipsalliance::cde:$rocketVersion",
    ivy"org.chipsalliance::macros:$rocketVersion",
    ivy"org.chipsalliance::diplomacy-$chiselVersion:$rocketVersion",
    ivy"org.chipsalliance::hardfloat-$chiselVersion:$rocketVersion",
    ivy"org.chipsalliance::rocketchip-$chiselVersion:$rocketVersion",
	ivy"ch.epfl.scala::bloop-config:2.0.3"
  )
  override def scalacPluginIvyDeps = Agg(
    ivy"org.chipsalliance:::chisel-plugin:$chiselVersion",
  )
}
trait riscvSpecCore extends ScalaModule with HasThisChisel {
  def scalaVersion = defaultScalaVersion
  def millSourcePath = pwd / os.up/"riscv-spec-core"
  def chiselModule: Option[ScalaModule] = None
  def chiselPluginJar: T[Option[PathRef]] = None
  def chiselIvy: Option[Dep] = v.chiselIvy
  def chiselPluginIvy: Option[Dep] = v.chiselPluginIvy
}
object riscvspeccore extends riscvSpecCore

trait Difftest extends ScalaModule with HasThisChisel{
  def scalaVersion = defaultScalaVersion
  def millSourcePath = pwd /os.up/ "difftest"
  def chiselModule: Option[ScalaModule] = None
  def chiselPluginJar: T[Option[PathRef]] = None
  def chiselIvy: Option[Dep] = v.chiselIvy
  def chiselPluginIvy: Option[Dep] = v.chiselPluginIvy
}

object difftest extends Difftest