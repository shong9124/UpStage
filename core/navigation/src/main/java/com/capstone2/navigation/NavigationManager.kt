package com.capstone2.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class NavigationManager @Inject constructor() {
    private val _commands = MutableSharedFlow<NavigationCommand>()
    val command = _commands.asSharedFlow()

    suspend fun navigate(command: NavigationCommand) {
        _commands.emit(command)
    }
}