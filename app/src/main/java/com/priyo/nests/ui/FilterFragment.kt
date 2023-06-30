package com.priyo.nests.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyo.nests.core.mvicore.MVIView
import com.priyo.nests.databinding.FragmentFilterBinding
import com.priyo.nests.ui.filterlist.FacilityAdapter
import com.priyo.nests.ui.filterlist.FacilityViewHolder
import com.priyo.nests.ui.helper.FilterEffect
import com.priyo.nests.ui.helper.FilterIntent
import com.priyo.nests.ui.helper.FilterState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment @Inject constructor() :
    Fragment(),
    MVIView<FilterIntent, FilterState, FilterEffect> {

    private var _binding: FragmentFilterBinding? = null

    private val binding get() = _binding!!
    private lateinit var facilityAdapter: FacilityAdapter
    private val viewModel: FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerInView(
            lifecycleOwner = viewLifecycleOwner,
            uiState = viewModel.uiState,
            uiEffect = viewModel.uiEffect,
        )
        setUpAdapter()
        initOnClickListeners()
        triggerEvent(FilterIntent.Init)
    }

    private fun setUpAdapter() {
        facilityAdapter = FacilityAdapter()
        facilityAdapter.onItemClick = { facilityType, position ->
            triggerEvent(FilterIntent.FacilityTypeCta(facilityType, position))
        }
        facilityAdapter.onItemChecked = { facilityOption, parentPosition, childPosition ->
            triggerEvent(
                FilterIntent.FacilityOptionCta(
                    facilityOption = facilityOption,
                    parentPosition = parentPosition,
                    childPosition = childPosition,
                ),
            )
        }
        binding.rvFacilityType.apply {
            adapter = facilityAdapter
            layoutManager =
                LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false,
                )
        }
        facilityAdapter.submitList(emptyList())
    }

    private fun initOnClickListeners() {
        binding.btnFindHouse.setOnClickListener {
            // todo: implement click
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observeUiState(state: FilterState) {
        when (state) {
            is FilterState.Init -> {
                facilityAdapter.submitList(state.filters)
            }
            is FilterState.UpdateFacilityTypeState -> {
                facilityAdapter.submitList(state.filters)
            }
            is FilterState.Idle -> {}
            else -> {}
        }
    }

    override fun observeUiEffect(effect: FilterEffect) {
        when (effect) {
            is FilterEffect.UpdateFacilityOption -> {
                val facilityViewHolder = binding.rvFacilityType.findViewHolderForAdapterPosition(
                    effect.parentPosition,
                ) as FacilityViewHolder
                facilityViewHolder.childAdapter?.submitList(effect.filters.get(effect.parentPosition).facilityOptions)
            }
            else -> {}
        }
    }

    override fun triggerEvent(onEvent: FilterIntent) {
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.intents.send(onEvent)
                }
            }
        }
    }
}
