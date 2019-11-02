package net.schwiz.marvel.util

import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.core.logger.Level.*
import timber.log.Timber

private object KoinTimber : Logger(INFO){
    override fun log(level: Level, msg: MESSAGE) {
        when(level){
            INFO -> Timber.i(msg)
            DEBUG -> Timber.d(msg)
            ERROR -> Timber.e(msg)
        }
    }
}

fun KoinApplication.timber(): KoinApplication {
    logger(KoinTimber)
    return this
}

class Cthaeh : Timber.Tree(){
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //If you get this reference you have read the greatest story ever written.
        //Do nothing, never speak to Cthaeh
    }
}
