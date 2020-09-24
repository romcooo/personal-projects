package com.romco.bracketeer.config

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class Initializer: ApplicationListener<ContextRefreshedEvent> {

    var alreadySetup = false

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) {
            return
        }



        //TODO - create roles if they don't exist by calling the DAO here

    }

}