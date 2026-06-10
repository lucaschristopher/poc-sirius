package com.example.pocsirius.core.common.security

import com.example.pocsirius.core.common.BuildConfig

object BuildType {

    val current: String get() = BuildConfig.BUILD_TYPE

    val isDebug: Boolean get() = current == DEBUG
    val isRelease: Boolean get() = current == RELEASE
    val isMock: Boolean get() = current == MOCK

    const val DEBUG = "debug"
    const val RELEASE = "release"
    const val MOCK = "mock"
}