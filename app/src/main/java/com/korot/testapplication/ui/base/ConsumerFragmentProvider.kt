package com.korot.testapplication.ui.base

interface ConsumerFragmentProvider : LoaderInterface {

    fun startFragment(fragment: BaseFragment)

    fun addFragment(fragment: BaseFragment)

    fun getProvider(): FragmentProvider

}