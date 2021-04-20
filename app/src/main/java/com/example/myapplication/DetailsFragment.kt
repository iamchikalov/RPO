package com.example.myapplication

import android.os.Bundle
import android.telecom.Call
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.parser3.Item
import com.squareup.picasso.Picasso


private const val IMAGE = "image"
private const val FULLNAME = "fullName"
private const val LANGUAGE = "language"
private const val URL = "url"
private const val DESCRIPTION = "description"


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.description.text = arguments?.getString(DESCRIPTION)
        binding.url.text = arguments?.getString(URL)
        binding.language.text = arguments?.getString(LANGUAGE)
        binding.fullName.text = arguments?.getString(FULLNAME)
        Picasso.get().load(arguments?.getString(IMAGE)).into(binding.imageView2)
    }

    companion object {
        @JvmStatic
        fun newInstance(item: Item) : DetailsFragment {
            val fragment = DetailsFragment()
            val arguments = Bundle()
            arguments.putString(FULLNAME, item.full_name)
            arguments.putString(IMAGE, item.owner?.avatar_url)
            arguments.putString(LANGUAGE, item.language)
            arguments.putString(DESCRIPTION, item.description)
            arguments.putString(URL, item.url)
            fragment.arguments = arguments
            return fragment
        }
    }
}