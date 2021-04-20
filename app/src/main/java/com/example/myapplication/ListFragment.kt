package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentListBinding
import com.example.parser.com.example.parser.AppDatabase
import com.example.parser3.QueryResult
import com.example.parser3.Requester
import java.lang.Exception

private lateinit var binding: FragmentListBinding
private var adapter: MyListAdapter? = null

private var requester = Requester()
private lateinit var db : AppDatabase


class ListFragment : Fragment() {

    fun updateQuery(q: String){
        requester.updateQ(q)
    }
    fun getData() : QueryResult?{
        var queryResult : QueryResult? = null

        try {
            queryResult = requester.run(requireContext())

            val thread = Thread(Runnable {
                db.myDao().tacticalNukeIncoming()
                for (item in queryResult!!.items) {
                    db.myDao().insert(item)
                }
            })
            thread.start()
            thread.join()

        } catch (e: Exception){
            //no internet connection ->
            val thread = Thread(Runnable{
                queryResult =
                    QueryResult(db.myDao().getAll())
            })
            thread.start()
            thread.join()

            if (queryResult?.items?.isEmpty()!!){
                Toast.makeText(
                    requireContext(), "There is no internet connection, " +
                            "and no data in DB :(", Toast.LENGTH_SHORT
                ).show()
            }
            else {
                Toast.makeText(
                    requireContext(), "There is no internet connection, but " +
                            "db is not empty!", Toast.LENGTH_SHORT
                ).show()
            }
        }
        return queryResult

    }


    fun updateView(){
        getData()?.let { adapter?.setNewItems(it) }
        binding.recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyListAdapter(emptyList(), requireActivity() as? MyListAdapter.OnItemClick)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        binding.btnSearch.setOnClickListener {
            updateQuery(binding.searchStr.text.toString())
            updateView()
        }

        updateView()
    }
}