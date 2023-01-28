package comsmilias.example.movieapp.presentation.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import comsmilias.example.movieapp.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel: MovieFragmentViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("id_of_item")?.let { id ->
            viewModel.getMovie(id)
        }

        lifecycleScope.launch {
            viewModel.state.collect() { state ->
                if (state.isLoading) {
                } else if (state.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                } else {
                    state.movie?.let {
                        binding.imageView.load(it.imageUrl)
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}