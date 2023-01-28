package comsmilias.example.movieapp.presentation.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import comsmilias.example.movieapp.R
import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            viewModel.state.collect(){ state ->
                if (state.isLoading){
                    binding.swipeRefresh.isRefreshing = true
                } else if (state.error.isNotEmpty()){
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                } else {
                    binding.swipeRefresh.isRefreshing = false
                    state.movies?.let { movies ->
                        moviesAdapter.setMovieList(movies)
                        moviesAdapter.notifyDataSetChanged()
                    }
                }

            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAdapter.layoutManager = GridLayoutManager(requireContext(), 2)
        moviesAdapter = MoviesAdapter(){ position ->
            viewModel.state.value.movies?.get(position)?.id?.let { movieId ->
                val bundle = Bundle().apply {
                    putInt("id_of_item", movieId)
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }

        }
        binding.rvAdapter.adapter = moviesAdapter

        binding.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.getMovies(true)
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}