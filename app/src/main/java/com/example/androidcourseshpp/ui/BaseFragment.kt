package com.example.androidcourseshpp.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {

  fun <T> BaseFragment.collectFlow(flow : Flow<T>, onCollect : (T) -> Unit){
      lifecycleScope.launch {
          repeatOnLifecycle(Lifecycle.State.STARTED){
              flow.collect{
                  onCollect(it)
              }
          }
      }
  }
}