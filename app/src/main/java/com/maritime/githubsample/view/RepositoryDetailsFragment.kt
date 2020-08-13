package com.maritime.githubsample.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.maritime.githubsample.R
import com.maritime.githubsample.viewmodel.RepoDetailsViewModel
import com.maritime.githubsample.viewmodel.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_repo_details.*

class RepositoryDetailsFragment : Fragment() {

    private var repoUrl = ""
    private var repoShareContent = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MainActivity.FRAGMENT_TAG = "TAG"
        val viewModel by viewModels<RepoDetailsViewModel> { getViewModelFactory() }
        viewModel.repository.observe(viewLifecycleOwner, Observer { repo ->
            toolbar.title = repo.name
            description.text = repo.description
            repoUrl = repo.html_url
            url.text = repo.html_url
            if (repo.owner.avatar_url.isNotEmpty()) {
                Glide.with(requireActivity())
                    .load(repo.owner.avatar_url)
                    .centerCrop()
                    .into(owner_image)
            }
            repoShareContent = "$${repo.name}\n${repo.description}\n\n${repo.html_url}"
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Snackbar.make(details_content, error, Snackbar.LENGTH_SHORT).show()
        })
        arguments?.getLong("DETAILS_ID_BUNDLE_KEY")?.let { id ->
            viewModel.fetchRepo(id)
        }
        url.setOnClickListener {
            context?.let { openChrome(repoUrl, it) }
        }
        toolbar.setNavigationOnClickListener { view ->
            parentFragmentManager.beginTransaction().replace(R.id.content_frame, RepositoryListFragment()).commit()
        }
        setHasOptionsMenu(true)
    }

    private fun openChrome(url: String, context: Context) {
        if (url.isEmpty()) return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            intent.setPackage(null)
            context.startActivity(intent)
        }
    }

    companion object {
        fun newInstance(id: Long): RepositoryDetailsFragment {
            val frag = RepositoryDetailsFragment()
            frag.arguments = Bundle().apply { putLong("DETAILS_ID_BUNDLE_KEY", id) }
            return frag
        }
    }

}