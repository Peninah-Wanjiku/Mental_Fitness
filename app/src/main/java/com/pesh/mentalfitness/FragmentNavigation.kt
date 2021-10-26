package com.pesh.mentalfitness

import androidx.fragment.app.Fragment

interface FragmentNavigation {

    fun navigateFrag(fragment: Fragment, addToStack: Boolean)
}