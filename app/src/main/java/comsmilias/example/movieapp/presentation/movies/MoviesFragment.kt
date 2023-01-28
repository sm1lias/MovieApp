package comsmilias.example.movieapp.presentation.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import comsmilias.example.movieapp.R
import comsmilias.example.movieapp.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: MoviesFragmentViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewModel.data.observe(viewLifecycleOwner){ data ->
            data?.let { movies ->
                moviesAdapter.setMovieList(movies)
                moviesAdapter.notifyDataSetChanged()
            }

        }
    }

    private fun initRecyclerView() {
        binding.rvAdapter.layoutManager = GridLayoutManager(requireContext(), 2)
        moviesAdapter = MoviesAdapter(){ position ->
            viewModel.data.value?.get(position)?.id?.let { movieId ->
                val bundle = Bundle().apply {
                    putInt("id_of_item", movieId)
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }

        }
        binding.rvAdapter.adapter = moviesAdapter

        binding.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.getRefreshMovies()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}