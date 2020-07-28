package com.korot.testapplication.ui.base

interface BaseActivity : LoaderInterface {

    fun startFragment(fragment: BaseFragment)

    fun addFragment(fragment: BaseFragment)

    fun getProvider(): FragmentProvider

    fun back()

    fun logout()

}