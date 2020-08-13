package com.maritime.githubsample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.maritime.githubsample.R
import com.maritime.githubsample.viewmodel.ReposViewModel
import com.maritime.githubsample.viewmodel.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_repos.*

class RepositoryListFragment : Fragment() {

    private val viewModel by viewModels<ReposViewModel> { getViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MainActivity.FRAGMENT_TAG = "ReposFragment"
        setupRefreshLayout()
        viewModel.repos.observe(viewLifecycleOwner, Observer { repos ->
            refresh_layout.isRefreshing = false
            (repos_list.adapter as RepositoryListAdapter).replaceData(repos)
        })
        repos_list.adapter = RepositoryListAdapter(
            repositories = emptyList(),
            clickListener = {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, RepositoryDetailsFragment.newInstance(it.id))
                    .commit()
            }
        )
        val retryListener: View.OnClickListener = View.OnClickListener {
            viewModel.fetchRepos(forceRefresh = true)
        }
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Snackbar.make(repos_list, error, Snackbar.LENGTH_SHORT)
                .setAction("RETRY", retryListener).show()
        })
        viewModel.fetchRepos(forceRefresh = false)
    }

    private fun setupRefreshLayout() {
        refresh_layout.setColorSchemeColors(
            ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
            ContextCompat.getColor(requireActivity(), R.color.colorAccent),
            ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark))
        refresh_layout.setOnRefreshListener {
            viewModel.fetchRepos(forceRefresh = true)
        }
    }
}