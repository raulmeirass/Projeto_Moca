package sptech.moca.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sptech.moca.databinding.ActivityPorquinhoBinding

class ExtratoPorquinhoFragment : Fragment() {

    private var _binding: ActivityPorquinhoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityPorquinhoBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
}