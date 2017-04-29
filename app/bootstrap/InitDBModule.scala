package bootstrap

import com.google.inject.AbstractModule


class InitDBModule extends AbstractModule {

  override def configure() = {
      bind(classOf[DBInit]).asEagerSingleton()

  }

}
