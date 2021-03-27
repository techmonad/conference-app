package bootstrap

import play.api.{Configuration, Environment}
import play.api.inject.{Binding, Module}


class InitDBModule extends Module {

  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[DBInit].toSelf
    )
  }

}
